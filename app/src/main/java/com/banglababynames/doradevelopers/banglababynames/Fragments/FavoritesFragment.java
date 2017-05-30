package com.banglababynames.doradevelopers.banglababynames.Fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banglababynames.doradevelopers.banglababynames.FirebaseConnection.Utils;
import com.banglababynames.doradevelopers.banglababynames.FirebaseNamesListAdapter;
import com.banglababynames.doradevelopers.banglababynames.Pojo.NamesPojo;
import com.banglababynames.doradevelopers.banglababynames.R;
import com.banglababynames.doradevelopers.banglababynames.WordAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {


    DatabaseReference ref;
    ListView favoriteList;
    private ActionBar mActionBar;
    ValueEventListener mListListner;
    private static final String TAG = "FavoritesFragment";
    ArrayList<NamesPojo> likedNamesList=new ArrayList<NamesPojo>();

    public FavoritesFragment() {
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
        likedNamesList.clear();
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_favorites, container, false);
        String UniqueID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        favoriteList = (ListView) view.findViewById(R.id.favorites_list);

        // changes actionbar title
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Favorites");

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


        final DatabaseReference keysData = FirebaseDatabase.getInstance().getReference("favorites").child(UniqueID);
        keysData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()){

                    ref=Utils.getDatabase().getReference().child("names").child(child.getKey().toString());
                    Log.e("Key",child.getKey().toString());
                    mListListner=ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            NamesPojo name = dataSnapshot.getValue(NamesPojo.class);
                            likedNamesList.add(name);

                            try {
                                WordAdapter namesAdapter = new WordAdapter(getContext(), likedNamesList);
                                favoriteList.setAdapter(namesAdapter);
                            }catch (NullPointerException e) {
                                Log.e(TAG,"null exception");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        //goes back to previous fragment

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    //goes back to previous fragment

                    // handle back button
                    MainActivityFragment fragment = new MainActivityFragment();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

                    return true;

                }

                return false;
            }
        });
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
        final MenuItem itemSearch= menu.findItem(R.id.action_search);
        itemSearch.setVisible(false);


        super.onCreateOptionsMenu(menu, inflater);
    }

}
