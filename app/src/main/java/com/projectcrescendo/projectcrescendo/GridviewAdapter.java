package com.projectcrescendo.projectcrescendo;

/**
 * Created by Jordan on 11/02/2016.
 */

import java.util.ArrayList;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectcrescendo.projectcrescendo.R;

public class GridviewAdapter extends BaseAdapter
{
    private ArrayList<String> notes;
    private ArrayList<Button> vol;
    private Activity activity;

    public GridviewAdapter(Activity activity,ArrayList<String> noteslist, ArrayList<Button> volumeList) {
        super();
        this.notes = noteslist;
        this.vol = volumeList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return notes.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        public Button changevol;
        public EditText txteditnotes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;

        Log.d("GridviewAdapter", "gonna do inflate...");


        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.activity_music, null);

            view.txteditnotes = (EditText) convertView.findViewById(R.id.textView1);
            view.changevol = (Button) convertView.findViewById(R.id.button1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txteditnotes.setText(notes.get(position));

        return convertView;
    }
}