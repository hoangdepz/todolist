package com.demo.mynote.AdapterClasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.mynote.Models.ImageModels;
import com.demo.mynote.R;
import java.util.List;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    private Context context;
    private List<ImageModels> imageIds;

    public ViewPagerAdapter(List<ImageModels> list, Context context) {
        this.imageIds = list;
        this.context = context;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_pager_items, viewGroup, false));
    }

    private Bitmap convertBase64ToBitmap(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        byte[] decode = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    @Override 
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Glide.with(this.context).load(convertBase64ToBitmap(this.imageIds.get(i).getImage())).placeholder(R.drawable.placeholder).format(DecodeFormat.PREFER_RGB_565).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imageView);
    }

    @Override 
    public int getItemCount() {
        return this.imageIds.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}
