package com.example.carlos.sp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class PhotoStoreActivity extends ActionBarActivity {

    private Intent mShareIntent;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_store);
        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("image/png");
        Uri uri = Uri.parse("@drawable/" + PhotoStoreActivity.getImage(getIntent().getStringExtra(Intent.EXTRA_TEXT)));
        mShareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        setContentView(R.layout.activity_store_detail);


        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.container1, new PlaceholderFragment());
        transaction.add(R.id.container2, new CommentsFragment());

        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_store, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);


        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(mShareIntent);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_photo_store, container, false);

            Intent intent =getActivity().getIntent();
            String store = intent.getStringExtra(Intent.EXTRA_TEXT);

            int index;
            int image = 0;
            switch(store) {
                case "Tienda de Lego":
                    image = R.drawable.lego;
                    break;
                case "Tienda de Libros":
                    image = R.drawable.libros;
                    break;
                case "Tienda de Zapatos":
                    image = R.drawable.zapatos;
                    break;
                case "Tienda de Ropa":
                    image = R.drawable.ropa;
                    break;
                default:
                    image = R.drawable.vinos;
                    break;
            }



            ((TextView) rootView.findViewById(R.id.store_photo_name)).setText(store);
            ((ImageView) rootView.findViewById(R.id.image_store)).setImageResource(image);


            return rootView;
        }
    }

    static String getImage(String store) {
        String image;
        switch(store) {
            case "Tienda de Lego":
                image = "lego.jpg";
                break;
            case "Tienda de Libros":
                image = "libros.png";
                break;
            case "Tienda de Zapatos":
                image = "zapatos.jpg";
                break;
            case "Tienda de Ropa":
                image = "ropa.png";
                break;
            default:
                image = "vinos.jpg";
                break;
        }
        return image;
    }
}
