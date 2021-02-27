package com.galleryx.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.galleryx.fragments.FolderImage;
import com.galleryx.fragments.ImageViewer;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull Fragment fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ImageViewer.newInstance(FolderImage.imagePaths.get(position));
    }


    @Override
    public int getItemCount() {
        return FolderImage.imagePaths.size();
    }
}
