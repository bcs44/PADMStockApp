package com.example.bruna.stockapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class ProductsListAdapter extends ArrayAdapter<Product> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;


    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView nome;
        TextView preco;
        //     TextView img;
        ImageView image;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ProductsListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the persons information
        String nome = getItem(position).getNome();
        String preco = getItem(position).getPreco();
        //    int img = getItem(position).getImg();
        String imgUrl = getItem(position).getImgURL();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.nome = (TextView) convertView.findViewById(R.id.textView1);
            holder.preco = (TextView) convertView.findViewById(R.id.textView2);
            //   holder.img = (TextView) convertView.findViewById(R.id.textView3);
            holder.image = (ImageView) convertView.findViewById(R.id.image);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.nome.setText(nome);
        holder.preco.setText(preco);
        //  holder.img.setText(img);

        //create the imageloader object
        ImageLoader imageLoader = ImageLoader.getInstance();
        ;
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //  String url = "https://firebasestorage.googleapis.com/v0/b/ortoconforto14.appspot.com/o/65971_1104628946218823_5320259956633339258_n.png?alt=media&token=6b2ca6a1-415e-4ade-a9f5-4dbf3ec6adde";

        //Glide.with(context).load(imgUrl).into(imageView);


        //download and display image from url
        imageLoader.displayImage(imgUrl, holder.image, options);

        return convertView;

    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
