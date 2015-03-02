package com.example.carlos.sp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carlos.sp.data.Comment.CommentEntry;
import com.example.carlos.sp.data.DbHelper;
import com.example.carlos.sp.data.Store;

import java.util.ArrayList;


public class CommentsFragment extends Fragment {


    private DbHelper db;
    private SQLiteDatabase SQLite;
    private Cursor cursor;

    private String store;
    private int storeId;
    private ArrayList<String> comments;
    private OnFragmentInteractionListener mListener;
    private ListView listview;
    private ArrayAdapter<String> mCommentsAdapter;



    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(getActivity());
        Intent intent = getActivity().getIntent();

        store = intent.getStringExtra(Intent.EXTRA_TEXT);

        SQLite = db.getWritableDatabase();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_comments, container, false);

        LinearLayout list = (LinearLayout) rootView.findViewById(R.id.list_comment);

        cursor = SQLite.rawQuery("SELECT * FROM stores WHERE " + Store.StoreEntry.COLUMN_NAME + "='" + store + "'", null);

        if(cursor.moveToFirst()){
            storeId = cursor.getInt(cursor.getColumnIndex(Store.StoreEntry._ID));
        }else{
            Log.e("ERROR", "DON'T EXIST THE STORE");
        }

        cursor = SQLite.rawQuery("SELECT * FROM " + CommentEntry.TABLE_NAME + " WHERE "
                + CommentEntry.COLUMN_STORE_KEY + "=" + storeId, null);
        comments = new ArrayList<String>();
        if(cursor.moveToFirst()) {
            do {
                comments.add(cursor.getString(cursor.getColumnIndex(CommentEntry.COLUMN_TEXT)));
                TextView vi = (TextView) inflater.inflate(R.layout.list_comments, null);
                vi.setText(cursor.getString(cursor.getColumnIndex(CommentEntry.COLUMN_TEXT)));
                list.addView(vi);
            } while (cursor.moveToNext());
        }


        /*mCommentsAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_comments,
                R.id.list_item_comment_textview,
                comments
        );

        listview = (ListView) rootView.findViewById(R.id.listview_comments);
        listview.setAdapter(mCommentsAdapter);
        */

        ((Button) rootView.findViewById(R.id.bComment)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) rootView.findViewById(R.id.newComment)).setText(((EditText) rootView.findViewById(R.id.textComment)).getText());

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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
