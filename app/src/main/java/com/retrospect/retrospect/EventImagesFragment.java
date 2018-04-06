package com.retrospect.retrospect;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Shivam on 3/26/2018.
 */


public class EventImagesFragment extends Fragment {

    private static final String TAG = EventImagesFragment.class.getSimpleName();
    private View view;
    private Button selectImagesButton;
    private ArrayList<Uri> mImageUris = new ArrayList<>();

    int PICK_IMAGES = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.event_images_fragment, container, false);
        Log.d(TAG, String.valueOf(view == null));
        selectImagesButton = view.findViewById(R.id.selectImagesButton);
        selectImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });
        return view;
    }

    private void getImages(){
        //Get images from gallery and add them to images arraylist
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGES);
        initEventImagesRecyclerView();
    }

    private void initEventImagesRecyclerView(){
        Log.d(TAG, "initEventImagesRecyclerView: init recyclerview");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.eventImagesRecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        EventImagesRecyclerViewAdapter adapter = new EventImagesRecyclerViewAdapter(this.getActivity(), mImageUris);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if(requestCode==PICK_IMAGES){
            if(resultCode==RESULT_OK){
                if(data.getData()!=null){
                    Uri mImageUri=data.getData();
                }
                else{
                    if(data.getClipData()!=null){
                        ClipData mClipData=data.getClipData();
                        ArrayList<Uri> mArrayUri=new ArrayList<Uri>();

                        for(int i=0;i<mClipData.getItemCount();i++){
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mImageUris.add(uri);
                        }
                        Log.v("LOG_TAG", "Selected Images"+ mArrayUri.size());
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public ArrayList<Uri> getmImageUris(){
        return mImageUris;
    }


}
