package com.example.carlos.sp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.carlos.sp.data.DbHelper;
import com.example.carlos.sp.data.Store.StoreEntry;


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

        public String store;

        private DbHelper db;
        private SQLiteDatabase SQLite;
        private Cursor cursor;

        private String store_address = "";
        private String store_phone = "";
        private String store_hoursOfOperation = "";
        private String store_website = "";
        private String store_email = "";

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            db = new DbHelper(getActivity());
            View rootView = inflater.inflate(R.layout.fragment_store_detail, container, false);

            Intent intent = getActivity().getIntent();

            store = intent.getStringExtra(Intent.EXTRA_TEXT);

            SQLite = db.getWritableDatabase();
            cursor = SQLite.rawQuery("SELECT * FROM stores WHERE " + StoreEntry.COLUMN_NAME + "='" + store + "'", null);

            if(cursor.moveToFirst()){
                store_address = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_ADDRESS));
                store_phone = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_PHONE));
                store_hoursOfOperation = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_SCHEDULE));
                store_website = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_WEBSITE));
                store_email = cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_EMAIL));
            }else{
                Log.e("ERROR", "DON'T EXIST THE STORE");
            }


            ((TextView) rootView.findViewById(R.id.store_name)).setText(store);



            ((TextView) rootView.findViewById(R.id.store_address)).setText(store_address);
            ((TextView) rootView.findViewById(R.id.store_number)).setText(store_phone);
            ((TextView) rootView.findViewById(R.id.store_schedule)).setText(store_hoursOfOperation);
            ((TextView) rootView.findViewById(R.id.store_website)).setText(store_website);
            ((TextView) rootView.findViewById(R.id.store_email)).setText(store_email);


            ((Button) rootView.findViewById(R.id.call)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + store_phone));
                    startActivity(callIntent);
                }
            });

            ((TextView) rootView.findViewById(R.id.store_website)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("https://" + store_email));
                    startActivity(callIntent);
                }
            });

            ((TextView) rootView.findViewById(R.id.store_address)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_VIEW);
                    callIntent.setData(Uri.parse("https://www.google.com.gt/maps/place/" + store_address));
                    startActivity(callIntent);
                }
            });

            ((TextView) rootView.findViewById(R.id.store_email)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{store_email});
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


