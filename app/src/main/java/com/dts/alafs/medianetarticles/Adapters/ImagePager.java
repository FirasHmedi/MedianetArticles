package com.dts.alafs.medianetarticles.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dts.alafs.medianetarticles.R;
import com.squareup.picasso.Picasso;

public class ImagePager extends PagerAdapter {
    private Context context;
    private String[] images;

    public ImagePager(Context context,String[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }
    /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_image, null);
        ImageView imageView = view.findViewById(R.id.item_image);
        String URL=context.getResources().getString(R.string.URL_RETRIEVE_IMAGES,context.getString(R.string.ROOT_URL));
        Picasso.with(context).load(URL+ images[position]+".jpg").fit().centerCrop().into(imageView);
        container.addView(view);
        return view;
    }
    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
    /*

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

}