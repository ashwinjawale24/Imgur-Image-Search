package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.prajwal.prajwalwaingankar_cavista.R;

/**
 * Created by Prajwal Waingankar
 * on 22-Aug-20.
 * Github: prajwalmw
 */


public class ImageAdapter extends BaseAdapter {
    private Context context;


    public ImageAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(imageIds[position]);
        return imageView;
    }

    public Integer[] imageIds = {
                    R.drawable.test_image,
                    R.drawable.test_image_2,
                    R.drawable.test_image,
                    R.drawable.test_image_2,
                    R.drawable.test_image};


}
