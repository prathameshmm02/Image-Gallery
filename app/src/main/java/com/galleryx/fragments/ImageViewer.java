package com.galleryx.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.galleryx.R;

public class ImageViewer extends Fragment {
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    private ImageView image;
    private static final String ARG_URI = "Image Path";
    private String mURI;
    private float mScaleFactor = 1.0f;
    private boolean isZoomed = false;
    public ImageViewer() {
    }

    public static ImageViewer newInstance(String uri) {
        ImageViewer fragment = new ImageViewer();
        Bundle args = new Bundle();
        args.putString(ARG_URI, uri);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mURI = getArguments().getString(ARG_URI);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_viewer, container, false);
        image = view.findViewById(R.id.myImage);
        image.setTransitionName(mURI);
        Glide.with(this)
                .load(mURI)
                .into(image);
        getParentFragment().startPostponedEnterTransition();
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mGestureDetector = new GestureDetector(getContext(), new GestureListener());
        view.setOnTouchListener((v, event) -> {
            mScaleGestureDetector.onTouchEvent(event);
            mGestureDetector.onTouchEvent(event);
            ViewPagerFragment.viewPager.setUserInputEnabled(!isZoomed);

//            if (!(0.9<mScaleFactor && mScaleFactor<1.1)){
//                  mGestureDetector.onTouchEvent(event);
//            }
            return true;
        });
        return view;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            isZoomed = true;
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 10.0f));
            if (mScaleFactor==1.0f){
                isZoomed = false;
            }
            image.setScaleX(mScaleFactor);
            image.setScaleY(mScaleFactor);
            return true;
        }
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isZoomed){
                mScaleFactor = 1.0f;
                isZoomed = false;
            }
            else{
                mScaleFactor = 2.0f;
                isZoomed = true;
            }
            image.setScaleX(mScaleFactor);
            image.setScaleY(mScaleFactor);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mScaleFactor>1){
                image.scrollBy((int) distanceX, (int) distanceY);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!isZoomed){
                if (e2.getY() > e1.getY()) {
                    swipeDown();
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void swipeDown() {
        this.getActivity().onBackPressed();
    }
}