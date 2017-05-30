package com.banglababynames.doradevelopers.banglababynames;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.banglababynames.doradevelopers.banglababynames.Pojo.NamesPojo;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

/**
 * Created by amirdora on 5/24/2017.
 */
public class FirebaseNamesListAdapter extends FirebaseListAdapter<NamesPojo> {
    public FirebaseNamesListAdapter(Activity activity, Class<NamesPojo> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, NamesPojo model, int position) {


        TextView EnglishNameTextView = (TextView) v.findViewById(R.id.english_name_textview);

        EnglishNameTextView.setText(model.getmNameEnglish());

        TextView BanglaNameTextView = (TextView) v.findViewById(R.id.bangla_name_textview);

        BanglaNameTextView.setText(model.getmNameBangla());


    }
}
