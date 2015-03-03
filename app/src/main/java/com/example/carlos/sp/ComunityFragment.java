package com.example.carlos.sp;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.carlos.sp.data.DbHelper;
import com.example.carlos.sp.data.Photo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.util.ArrayList;


public class ComunityFragment extends Fragment {

    private DbHelper db;
    private SQLiteDatabase SQLite;
    private Cursor cursor;
    private Photo photo;
    private ArrayList<String> photo_description_list;
    private ArrayList<String> photo_url_list;
    private ListView list;

    public ComunityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comunity, container, false);
        SQLite = db.getWritableDatabase();
        cursor = SQLite.rawQuery("SELECT * FROM " + Photo.PhotoEntry.TABLE_NAME, null);
        photo_description_list = new ArrayList<String>();
        photo_url_list = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                photo_description_list.add(cursor.getString(cursor.getColumnIndex(Photo.PhotoEntry.COLUMN_DESCRIPTION)));
                photo_url_list.add(cursor.getString(cursor.getColumnIndex(Photo.PhotoEntry.COLUMN_URL)));
            }while(cursor.moveToNext());
        }else{
            try {
                readJSONFile();
            } catch (Exception e) {
                Log.e("ERROR", "DON'T READ JSON FILE" + e.getMessage() + e.getCause());
            }

        }

        CustomList adapter = new CustomList(getActivity(), photo_description_list, photo_url_list);
        list=(ListView) rootView.findViewById(R.id.community_list);
        list.setAdapter(adapter);

        return rootView;
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
            if (type.equals("photo")) {
                String url = (String) json_object.get("url");
                String description = (String) json_object.get("description");
                photo = new Photo(
                        url,
                        description,
                        Integer.valueOf(json_object.get("favorites").toString())
                );
                SQLite.insert(Photo.PhotoEntry.TABLE_NAME, null,PhotoContentValues(photo));
                photo_description_list.add(description);
                photo_url_list.add(url);
            }
        }

    }

    public ContentValues PhotoContentValues(Photo photo) {
        ContentValues content = new ContentValues();
        content.put(Photo.PhotoEntry.COLUMN_URL, photo.url);
        content.put(Photo.PhotoEntry.COLUMN_DESCRIPTION, photo.description);
        content.put(Photo.PhotoEntry.COLUMN_FAVORITES_COUNTER, photo.favorites);
        return content;
    }

}
