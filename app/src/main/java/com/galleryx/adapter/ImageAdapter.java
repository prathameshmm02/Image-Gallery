package com.galleryx.adapter;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.galleryx.ImageFolder;
import com.galleryx.R;
import com.galleryx.fragments.ImageViewer;
import com.galleryx.fragments.ViewPagerFragment;
import com.galleryx.model.Image;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.galleryx.ImageFolder.noOfCols;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Fragment fragment;
    ArrayList<Image> images;

    public ImageAdapter(Fragment fragment, ArrayList<Image> images) {
        this.fragment = fragment;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        DisplayMetrics displaymetrics = fragment.requireContext().getResources().getDisplayMetrics();
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.widthPixels / noOfCols;
        //if you need 4-5-6 anything fix imageview in height
        holder.cardView.getLayoutParams().width = devicewidth-4;
        //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
        holder.cardView.getLayoutParams().height = devicewidth-4;
        holder.imageView.setClipToOutline(true);
        holder.imageView.setTransitionName(images.get(position).getImagePath());
        DrawableCrossFadeFactory factory = new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();
        Glide.with(fragment)
                //.applyDefaultRequestOptions(new RequestOptions().dontTransform())
                .load(images.get(position).getImagePath())
                .transition(withCrossFade(factory))
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardLayout);
            imageView = itemView.findViewById(R.id.imageItem);

            itemView.setOnClickListener(v ->{
                Log.i("Clicked", "Cik");
                ImageFolder.currentPosition = getAdapterPosition();
                fragment.getParentFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true) // Optimize for shared element transition
                        .replace(R.id.frameLayout, new ViewPagerFragment(), ViewPagerFragment.class
                                .getSimpleName())
                        .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
                        .addToBackStack(ViewPagerFragment.class
                                .getSimpleName())
                        .commit();
            });


        }


    }

}
