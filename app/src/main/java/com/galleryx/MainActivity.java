package com.galleryx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.galleryx.adapter.AlbumAdapter;
import com.galleryx.model.Album;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Album> albums;
    ArrayList<String> PicAlbums = new ArrayList<>();
    AlbumAdapter albumAdapter;
    private final int storageRequestCode = 69;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, storageRequestCode);
        }
        setContentView(R.layout.activity_main);
        albums = new ArrayList<>();
        recyclerView = findViewById(R.id.albumRecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        getAlbums();
        albumAdapter = new AlbumAdapter(this, albums);
        recyclerView.setAdapter(albumAdapter);
        recyclerView.setScrollBarSize(20);
        recyclerView.setVerticalScrollBarEnabled(true);

    }

    public void getAlbums(){
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor albumCursor = getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_ID},  null, null,MediaStore.Images.Media.DATE_TAKEN + " DESC");
        if (albumCursor!=null && albumCursor.moveToFirst())
            do {
                String albumName = albumCursor.getString(0);
                String firstImage = albumCursor.getString(1);
                String  bucketID = albumCursor.getString(2);
                if(!PicAlbums.contains(albumName)){
                    PicAlbums.add(albumName);
                    Album album = new Album(albumName);
                    album.setFirstImage(firstImage);
                    album.setAlbumID(bucketID);
                    albums.add(album);
                }
            }while(albumCursor.moveToNext());
        albumCursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(storageRequestCode==requestCode){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getAlbums();
                albumAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(this, "You Sure about that?? :(", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}