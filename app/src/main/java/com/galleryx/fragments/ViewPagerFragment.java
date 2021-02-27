package com.galleryx.fragments;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.galleryx.ImageFolder;
import com.galleryx.R;
import com.galleryx.adapter.ViewPagerAdapter;

import java.util.List;
import java.util.Map;

import static com.galleryx.ImageFolder.currentPosition;


public class ViewPagerFragment extends Fragment {

    public static ViewPager2 viewPager;
    public ViewPagerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewPager = (ViewPager2) inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager.setAdapter(new ViewPagerAdapter(this));
        viewPager.setCurrentItem(currentPosition, false);
        LinearLayout toolbar = requireActivity().findViewById(R.id.bottomToolbar);
        toolbar.setVisibility(View.VISIBLE);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();
        return viewPager;
    }
}
