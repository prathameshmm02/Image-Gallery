package com.galleryx.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.galleryx.ImageFolder;
import com.galleryx.R;
import com.galleryx.adapter.ImageAdapter;
import com.galleryx.model.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.galleryx.ImageFolder.noOfCols;

public class FolderImage extends Fragment {
    ArrayList<Image> images;
    RecyclerView recyclerView;
    public static ArrayList<String> imagePaths;
    static GridLayoutManager gridLayout;
    ImageAdapter myAdapter;
    public FolderImage() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_folder_image, container, false);
        LinearLayout toolbar = getActivity().findViewById(R.id.bottomToolbar);
        toolbar.setVisibility(View.GONE);
        Intent intent = getActivity().getIntent();
        String albumID = intent.getStringExtra("Folder ID");
        TextView textView = view.findViewById(R.id.albumNameTitle);
        textView.setText(getActivity().getIntent().getStringExtra("Folder Name"));
        recyclerView = view.findViewById(R.id.imageRecyclerView);
        //DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        gridLayout = new GridLayoutManager(getContext(), noOfCols);
        recyclerView.setLayoutManager(gridLayout);
        recyclerView.setHasFixedSize(false);
        images = new ArrayList<>();
        imagePaths = new ArrayList<>();
        images = getImages(albumID);
        myAdapter = new ImageAdapter(this, images);
        recyclerView.setAdapter(myAdapter);
        // Inflate the layout for this fragment
        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the ViewHolder for the clicked position.
                        RecyclerView.ViewHolder selectedViewHolder = recyclerView
                                .findViewHolderForAdapterPosition(ImageFolder.currentPosition);
                        if (selectedViewHolder == null) {
                            return;
                        }

                        // Map the first shared element name to the child ImageView.
                        sharedElements
                                .put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.myImage));
                    }
                });
        return view;
    }

    public ArrayList<Image> getImages(String albumID) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getActivity().getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN}, MediaStore.Images.Media.BUCKET_ID + " = " + albumID, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
        cursor.moveToFirst();
        do {
            String imgPath = cursor.getString(0);
            String imgAlbum = cursor.getString(2);
            String dateTaken = cursor.getString(3);
            Image image = new Image(imgPath, imgAlbum, dateTaken);
            images.add(image);
            imagePaths.add(imgPath);
        }while (cursor.moveToNext());
        cursor.close();
        return images;
    }
}
