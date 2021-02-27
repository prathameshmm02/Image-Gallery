package com.galleryx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.galleryx.fragments.FolderImage;
import com.galleryx.fragments.ViewPagerFragment;

public class ImageFolder extends AppCompatActivity {
    public static int noOfCols = 3;
    public static int currentPosition;
    ImageButton share, edit, delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_folder);
        share = findViewById(R.id.share);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(v ->{
            String currentUri = FolderImage.imagePaths.get(currentPosition);
            new AlertDialog.Builder(this).setTitle("Delete")
                    .setMessage("Do You want to delete the file?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        try {
                            ViewPagerFragment.viewPager.setCurrentItem(currentPosition+1);
                        }
                        catch (Exception e){
                            ViewPagerFragment.viewPager.setCurrentItem(currentPosition-1);
                        }
                        getContentResolver().delete(Uri.parse(currentUri), null, null);
                    })
                    .setNegativeButton("No", null)
                    .show();

        });
        share.setOnClickListener(v ->{
            String currentUri = FolderImage.imagePaths.get(currentPosition);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("image/*");
            i.putExtra(Intent.EXTRA_STREAM, Uri.parse(currentUri));
            startActivity(Intent.createChooser(i, "Share Using"));
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.frameLayout, new FolderImage(), FolderImage.class.getSimpleName())
                .commit();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.folder_menu, menu);
        return true;
    }

/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.action_grid_size_2:
                FolderImage.gridLayout.setSpanCount(2);
                noOfCols = 2;
                break;
            case R.id.action_grid_size_3:
                FolderImage.gridLayout.setSpanCount(3);
                noOfCols = 3;
                break;
            case R.id.action_grid_size_4:
                FolderImage.gridLayout.setSpanCount(4);
                noOfCols = 4;
                break;
            case R.id.action_grid_size_5:
                FolderImage.gridLayout.setSpanCount(5);
                noOfCols = 5;
                break;
            case R.id.deleteButton:
                for (Integer image: ImageAdapter.selectedItems) {
                    new File(images.get(image).getImagePath()).delete();
                }

        }
        return true;
    }
    */
}