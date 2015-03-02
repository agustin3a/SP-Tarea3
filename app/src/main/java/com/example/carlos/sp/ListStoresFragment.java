package com.example.carlos.sp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.carlos.sp.data.Comment;
import com.example.carlos.sp.data.Comment.CommentEntry;
import com.example.carlos.sp.data.DbHelper;
import com.example.carlos.sp.data.Store;
import com.example.carlos.sp.data.Store.StoreEntry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListStoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListStoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListStoresFragment extends Fragment {

    private ArrayAdapter<String> mStoresAdapter;
    private OnFragmentInteractionListener mListener;
    private DbHelper db;
    private SQLiteDatabase SQLite;
    private Cursor cursor;
    private ArrayList<String> stores = new ArrayList<String>();
    private ListView listview;
    private Store store;
    private Comment commentX;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListStoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListStoresFragment newInstance(String param1, String param2) {
        ListStoresFragment fragment = new ListStoresFragment();
        return fragment;
    }

    public ListStoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(getActivity());

    }

    //@Override
    //public void onActivityCreated(Bundle savedInstanceState) {
     //

    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_stores, container, false);

        super.onCreate(savedInstanceState);
        SQLite = db.getWritableDatabase();
        cursor = SQLite.rawQuery("SELECT * FROM stores" , null);
        stores = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                stores.add(cursor.getString(cursor.getColumnIndex(StoreEntry.COLUMN_NAME)));

            }while(cursor.moveToNext());
        }else{
            try {
                readJSONFile();
            } catch (Exception e) {
                Log.e("ERROR","DON'T READ JSON FILE" + e.getMessage() + e.getCause());
            }

        }
        mStoresAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_stores,
                R.id.list_item_store_textview,
                stores
        );



        listview = (ListView) rootView.findViewById(R.id.listview_store);
        listview.setAdapter(mStoresAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String aux = mStoresAdapter.getItem(position);
                // Toast.makeText(getActivity(), aux ,Toast.LENGTH_SHORT).show()

                Intent intent = new Intent(getActivity(),StoreDetailActivity.class).putExtra(Intent.EXTRA_TEXT, aux);
                startActivity(intent);
            }
        });

        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }


    public void readJSONFile() throws Exception{

            JSONParser parser = new JSONParser();
            JSONArray comments;
            AssetManager assetManager = getActivity().getAssets();
            InputStream input;
            input = assetManager.open("stores.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer, "UTF-8");
            JSONArray a = (JSONArray) parser.parse(text);
            for (Object o : a) {
                JSONObject json_object = (JSONObject) o;
                String type = (String) json_object.get("type");
                if (type.equals("store")) {
                    String name = (String) json_object.get("name");
                    int id = Integer.valueOf(json_object.get("id").toString());
                    Log.e("PRUEBA", name);
                    store = new Store(
                            id,
                            name,
                            (String) json_object.get("address"),
                            (String) json_object.get("phone"),
                            (String) json_object.get("hoursOfOperaion"),
                            (String) json_object.get("url"),
                            (String) json_object.get("email"),
                            Integer.valueOf(json_object.get("favorites").toString()),
                            (String) json_object.get("location")
                    );
                    SQLite.insert(StoreEntry.TABLE_NAME, null,StoreContentValues(store));
                    comments = (JSONArray) json_object.get("comments");
                    for(Object c : comments) {
                        JSONObject comment = (JSONObject) c;
                        commentX = new Comment(
                                (String) comment.get("comment"),
                                id
                        );
                        SQLite.insert(CommentEntry.TABLE_NAME, null, CommentContentValues(commentX));
                    }
                    stores.add(name);
                }
            }

    }

    public ContentValues StoreContentValues(Store store){
        ContentValues content = new ContentValues();
        content.put(StoreEntry._ID, store.id);
        content.put(StoreEntry.COLUMN_NAME,  store.name );
        content.put(StoreEntry.COLUMN_ADDRESS,  store.address );
        content.put(StoreEntry.COLUMN_PHONE,  store.phone );
        content.put(StoreEntry.COLUMN_SCHEDULE,  store.schedule );
        content.put(StoreEntry.COLUMN_WEBSITE,  store.website );
        content.put(StoreEntry.COLUMN_EMAIL,   store.email  );
        content.put(StoreEntry.COLUMN_LOCATION,  store.location );
        content.put(StoreEntry.COLUMN_FAVORITES_COUNTER, store.favorites);
        return content;
    }

    public ContentValues CommentContentValues(Comment comment) {
        ContentValues content = new ContentValues();
        content.put(CommentEntry.COLUMN_TEXT, comment.text);
        content.put(CommentEntry.COLUMN_STORE_KEY, comment.store_id);
        return content;
    }

}
