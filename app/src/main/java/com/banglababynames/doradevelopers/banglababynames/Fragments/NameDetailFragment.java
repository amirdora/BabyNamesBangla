package com.banglababynames.doradevelopers.banglababynames.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.banglababynames.doradevelopers.banglababynames.Pojo.NamesPojo;
import com.banglababynames.doradevelopers.banglababynames.R;
import com.banglababynames.doradevelopers.banglababynames.FirebaseConnection.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameDetailFragment extends Fragment{

    private DatabaseReference databaseDetail;
    private DatabaseReference databaseFavRating;
    private Menu mMenu;
    private String key;
    private String unique_id;
    private ActionBar mActionBar;
    private TextView EnglishNameTextView;
    private TextView BanglaNameTextView;
    private TextView BanglaMeaningTextView;
    private TextView EnglishMeaningTextView;
    private TextView GenderTextView;
    private static final String TAG = "NameDetailFragment";
    public NameDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_name_detail, container, false);
        Utils.getDatabase();
        Bundle bundle = getArguments();
        key = bundle.getString("string_passed");
        databaseDetail = Utils.getDatabase().getReference().child("names").child(key);
        unique_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Name Meaning");

        EnglishNameTextView = (TextView) view.findViewById(R.id.english_name_textview);
        BanglaNameTextView = (TextView) view.findViewById(R.id.bangla_name_textview);
        BanglaMeaningTextView = (TextView) view.findViewById(R.id.bangla_meaning_textview);
        EnglishMeaningTextView = (TextView) view.findViewById(R.id.english_meaning_textview);
        GenderTextView = (TextView) view.findViewById(R.id.gender_english_textview);





        databaseDetail.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NamesPojo post = dataSnapshot.getValue(NamesPojo.class);
                EnglishNameTextView.setText(post.getmNameEnglish());
                BanglaNameTextView.setText(post.getmNameBangla());
                EnglishMeaningTextView.setText(post.getmMeaningEnglish());
                BanglaMeaningTextView.setText(post.getmMeaningBangla());
                GenderTextView.setText(post.getmGender());


                // check gender condition, write gender in Bangla
                if(GenderTextView.getText().toString().matches("Male")){


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
                }
                else {


                    /*
                    Changes actionbar color
                     */
                    mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#EC407A")));
                    //changes status bar color
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = (getActivity()).getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(Color.parseColor("#E91E63"));
                    }

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
        mMenu = menu;
        final MenuItem itemSignout= mMenu.findItem(R.id.action_sign_out);
        itemSignout.setVisible(false);
        final MenuItem itemSearch= mMenu.findItem(R.id.action_search);
        itemSearch.setVisible(false);
        final MenuItem item = mMenu.findItem(R.id.action_favorite);

        databaseFavRating= Utils.getDatabase().getReference().child("favorites").child(unique_id).child(key);
        databaseFavRating.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Object> data=(HashMap<String, Object>) dataSnapshot.getValue();
                if (data==null) {
                    //display unfill favorite icon
                    item.setIcon(R.mipmap.ic_menu_favorite_unfill);
                }
                else {
                    item.setIcon(R.mipmap.ic_menu_favorite_fill);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        int id = item.getItemId();

        if (id == R.id.action_favorite) {

            databaseFavRating= Utils.getDatabase().getReference().child("favorites").child(unique_id).child(key);
            databaseFavRating.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String,Object> data=(HashMap<String, Object>) dataSnapshot.getValue();
                    if (data==null) {
                        Log.e(TAG,"not null"+key+" deviceId"+unique_id);
                        HashMap<String,Object> dataStore=new HashMap<String,Object>();
                        dataStore.put("liked","true");
                        databaseFavRating.setValue(dataStore);
                        Toast.makeText(getActivity(), "Added to Favorite", Toast.LENGTH_SHORT).show();

                        /*
                        favorite heart icon change
                         */
                        setActionIcon(true);

                    }
                    else
                    {
                        databaseFavRating.removeValue();
                        Toast.makeText(getActivity(), "Removed from Favorite", Toast.LENGTH_SHORT).show();

                        /*
                        favorite heart icon change
                         */
                        setActionIcon(false);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            /*
            changes favorite icon on click
             */


            return true;
        }
        if(id== R.id.action_share_w){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Name: " +EnglishNameTextView.getText().toString() +"\nMeaning: " +EnglishMeaningTextView.getText().toString()+"\nGender: "+GenderTextView.getText().toString()+
                                "\nBangla: " +BanglaNameTextView.getText().toString() + "\nMeaning: " +BanglaMeaningTextView.getText().toString()+"\n\nFor more names download Bangla Baby Names App for Android \n" + "http://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
            startActivity(Intent.createChooser(intent, "Share with"));

            return true;
        }
            return super.onOptionsItemSelected(item);

    }

    //Call when you need to change favorite icon
    private void setActionIcon(boolean showWithBadge)
    {
        MenuItem item = mMenu.findItem(R.id.action_favorite);

        if(mMenu != null)
        {
            if(showWithBadge)
            {
                item.setIcon(R.mipmap.ic_menu_favorite_fill);
            }
            else
            {
                item.setIcon(R.mipmap.ic_menu_favorite_unfill);
            }
        }
    }

}
