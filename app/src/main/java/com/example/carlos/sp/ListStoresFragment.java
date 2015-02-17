package com.example.carlos.sp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListStoresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListStoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListStoresFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayAdapter<String> mStoresAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ListStoresFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_stores, container, false);

        String[] storesArray = {
                "Tienda de Lego",
                "Tienda de Libros",
                "Tienda de Zapatos",
                "Tienda de Ropa",
                "Tienda de Vinos"
        };
        ArrayList<String> stores = new ArrayList<String>(
                Arrays.asList(storesArray)
        );

        mStoresAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_stores,
                R.id.list_item_store_textview,
                stores
        );

        ListView listview = (ListView) rootView.findViewById(R.id.listview_store);
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
