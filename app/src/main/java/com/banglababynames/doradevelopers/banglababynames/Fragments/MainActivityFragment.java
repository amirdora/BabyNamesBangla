package com.banglababynames.doradevelopers.banglababynames.Fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.banglababynames.doradevelopers.banglababynames.FirebaseNamesListAdapter;
import com.banglababynames.doradevelopers.banglababynames.Pojo.NamesPojo;
import com.banglababynames.doradevelopers.banglababynames.R;
import com.banglababynames.doradevelopers.banglababynames.FirebaseConnection.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private DatabaseReference Database;
    private Query DatabaseQuery;
    private ActionBar mActionBar;


    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // line of code to access actionbar items when on fragment
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_activity, container, false);

        Utils.getDatabase();
        DatabaseQuery = Utils.getDatabase().getReference("names");

        // changes actionbar title
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Bangla Names");

        ListView listView = (android.widget.ListView) view.findViewById(R.id.listview_names);
        final FirebaseNamesListAdapter NamesAdapter = new FirebaseNamesListAdapter(getActivity(), NamesPojo.class, R.layout.list_item_name, DatabaseQuery);

        listView.setAdapter(NamesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {

                String key = NamesAdapter.getRef(position).getKey();
                Log.e("key", key);
                Bundle bundle = new Bundle();
                bundle.putString("string_passed", key);
                /*

                opens new fragment

                 */

                NameDetailFragment fragment = new NameDetailFragment();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });


         /*
            Changes actionbar color
         */
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00BCD4")));
        //changes status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = (getActivity()).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#00ACC1"));
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        // Save the menu reference
        //hides actionbar icons
        final MenuItem itemFavorite = menu.findItem(R.id.action_favorite);
        itemFavorite.setVisible(false);
        final MenuItem itemSignout= menu.findItem(R.id.action_sign_out);
        itemSignout.setVisible(false);
        final MenuItem itemShare= menu.findItem(R.id.action_share_w);
        itemShare.setVisible(false);


        super.onCreateOptionsMenu(menu, inflater);
    }


}
