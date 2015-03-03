package com.example.carlos.sp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Carlos on 2/03/15.
 */
public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> description_list;
    private ArrayList<String> url_list;

    public CustomList(Activity context,ArrayList<String> web, ArrayList<String> imageId) {
        super(context, R.layout.list_comunity, web);
        this.context = context;
        this.description_list = web;
        this.url_list = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_comunity, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.photo_description);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.photo);
        txtTitle.setText(description_list.get(position));
        //imageView.setImageBitmap();
        new ImageLoadTask(url_list.get(position), imageView).execute();
        return rowView;
    }



}

 class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private ImageView imageView;

    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}
