package com.banglababynames.doradevelopers.banglababynames;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.banglababynames.doradevelopers.banglababynames.Pojo.NamesPojo;

import java.util.ArrayList;

/**
 * Created by amirdora on 5/26/2017.
 */

public class WordAdapter extends ArrayAdapter<NamesPojo> {

    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param words is the list of {@link NamesPojo}s to be displayed.
     */
    public WordAdapter(Context context, ArrayList<NamesPojo> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_name, parent, false);
        }

        NamesPojo currentName = getItem(position);
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_name_textview);
        englishTextView.setText(currentName.getmNameEnglish());
        TextView banglaTextView = (TextView) listItemView.findViewById(R.id.bangla_name_textview);
        banglaTextView.setText(currentName.getmNameBangla());

        return listItemView;
    }
}