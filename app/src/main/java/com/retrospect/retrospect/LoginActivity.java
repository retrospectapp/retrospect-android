package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResponse;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private final static int RC_SIGN_IN =2;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        SignInButton button = findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();
        CredentialsClient mCredentialsClient = Credentials.getClient(this);
        CredentialRequest mCredentialRequest = new CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .setAccountTypes(IdentityProviders.GOOGLE)
                .build();

        mCredentialsClient.request(mCredentialRequest).addOnCompleteListener(new OnCompleteListener<CredentialRequestResponse>() {
            @Override
            public void onComplete(@NonNull Task<CredentialRequestResponse> task) {
                if(task.isSuccessful()){
                    Log.d("GOOG_SIGN_IN","Credential retrieval successful");
                    onCredentialReceived(task.getResult().getCredential());
                } else{
                    Log.d("GOOG_SIGN_IN", "Credential retrieval failed");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d("GOOG_SIGN_IN", "Sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // Sign in success, update UI with the signed-in user's information
                            Log.d("GOOG_SIGN_IN", "signInWithCredential:success");
                            CredentialsClient mCredentialsClient = Credentials.getClient(getParent());
                            Credential cred = new Credential.Builder(account.getEmail())
                                    .setAccountType(IdentityProviders.GOOGLE)
                                    .setName(account.getDisplayName())
                                    .setProfilePictureUri(account.getPhotoUrl())
                                    .build();
                            mCredentialsClient.save(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("CRED", "SAVE: OK");
                                        Toast.makeText(getParent(), "Credentials saved", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            findAccountType(account);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.d("GOOG_SIGN_IN", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void onCredentialReceived(Credential credential) throws NullPointerException{
        String accountType = credential.getAccountType();
        if(accountType == null) {
          Log.d("CRED","Account type is null");
        } else if (accountType.equals(IdentityProviders.GOOGLE)) {
            // The user has previously signed in with Google Sign-In. Silently
            // sign in the user with the same ID.
            // See https://developers.google.com/identity/sign-in/android/
            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();

            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
            Task<GoogleSignInAccount> task = signInClient.silentSignIn();
            task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                    Log.d("SILENT_SIGN_IN", "Silent sign in successful");
                }
            });
        }
    }

    private void findAccountType(GoogleSignInAccount account){
        String uid = account.getId();
        // Query the Users collection for this ID and return user type
        CollectionReference mUserRef = FirebaseFirestore.getInstance().collection("sampleData/Users/Accounts");
    }

}
