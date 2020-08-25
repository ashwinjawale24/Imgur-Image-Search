package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import android.content.Context;
import android.graphics.Matrix;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prajwal.prajwalwaingankar_cavista.R;

import java.util.List;

/**
 * Created by Prajwal Waingankar
 * on 22-Aug-20.
 * Github: prajwalmw
 */


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<String> stringList;


    /**
     * ImageAdapter constructor with two arguments
     * @param context the context of the MainActivity
     * @param imageUrlsList An arraylist of type String which contains all the images urls.
     */
    public ImageAdapter(Context context, List<String> imageUrlsList) {
        this.context = context;
        this.stringList = imageUrlsList;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     *
     * @param position The current position of the item which is clicked
     * @param convertView If the initialization of imageview is not done
     * @param parent The viewgroup parent reference.
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, 400));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        Uri uri = Uri.parse(stringList.get(position));
        Glide.with(context).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        return imageView;
    }

}
