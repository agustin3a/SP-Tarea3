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
import android.widget.Button;
import android.widget.TextView;


public class StoreDetailActivity extends ActionBarActivity {

    Intent mShareIntent;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String store = intent.getStringExtra(Intent.EXTRA_TEXT);

        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, store);
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
        getMenuInflater().inflate(R.menu.menu_store_detail, menu);

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

        private String[] addressArray = {"14 calle 4-5 Zona 18 Guatemala",
                "12 calle 4-3 Zona 1 Guatemala",
                "15 calle 6-5 Zona 16 Guatemala",
                "9 calle 9-12 Zona 14 Guatemala",
                "10 calle 34-6 Zona 2 Guatemala"
        };

        private String[] numberArray = {"59895895", "58392030", "94950334", "56473829", "69605040"};


        private String[] scheduleArray = {"Lunes - Viernes : 9:00 - 12:00",
                "Lunes - Viernes : 8:00 - 10:00",
                "Lunes - Viernes : 7:00 - 17:00",
                "Lunes - Viernes : 6:00 - 16:00",
                "Lunes - Viernes : 5:00 - 18:00",
        };

        private String[] websiteArray = {"lego.com", "libros.com", "zapatos.com", "ropa.com", "vinos.com"};

        private String[] emailArray = {"lego@gmail.com", "libros@gmail.com", "zapatos@gmail.com", "ropa@gmail.com", "vinos@gmail.com"};

        public int index;

        public String store;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_store_detail, container, false);

            Intent intent = getActivity().getIntent();
            store = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) rootView.findViewById(R.id.store_name)).setText(store);

            switch (store) {
                case "Tienda de Lego":
                    index = 0;
                    break;
                case "Tienda de Libros":
                    index = 1;
                    break;
                case "Tienda de Zapatos":
                    index = 2;
                    break;
                case "Tienda de Ropa":
                    index = 3;
                    break;
                default:
                    index = 4;
                    break;
            }


            ((TextView) rootView.findViewById(R.id.store_address)).setText(addressArray[index]);
            ((TextView) rootView.findViewById(R.id.store_number)).setText(numberArray[index]);
            ((TextView) rootView.findViewById(R.id.store_schedule)).setText(scheduleArray[index]);
            ((TextView) rootView.findViewById(R.id.store_website)).setText(websiteArray[index]);
            ((TextView) rootView.findViewById(R.id.store_email)).setText(emailArray[index]);


            ((Button) rootView.findViewById(R.id.call)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + numberArray[index]));
                    startActivity(callIntent);
                }
            });

            ((TextView) rootView.findViewById(R.id.store_website)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("https://" + websiteArray[index]));
                    startActivity(callIntent);
                }
            });

            ((TextView) rootView.findViewById(R.id.store_address)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("https://www.google.com.gt/maps/place/" + addressArray[index]));
                    startActivity(callIntent);
                }
            });

            ((TextView) rootView.findViewById(R.id.store_email)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailArray[index]});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                    startActivity(Intent.createChooser(intent, ""));
                }
            });

            ((Button) rootView.findViewById(R.id.button_photo)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(getActivity(), PhotoStoreActivity.class);
                    callIntent.putExtra(Intent.EXTRA_TEXT, store);
                    startActivity(callIntent);

                }
            });


            return rootView;
        }
    }
}


