package com.projectcrescendo.projectcrescendo;

/**
 * A Grid View Adapter to allow us to use the GridView in the TutorialActivity to display a list
 * of notes.
 *
 * Created by Alexander on 23/02/2016.
 */
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;

import com.projectcrescendo.projectcrescendo.models.Stave;

interface NoteGridViewAdapterListener {
    void onItemTapListener(int itemPosition);
}

public class NoteGridViewAdapter extends BaseAdapter{

    private static final int GRID_VIEW_MARGIN_TOP = 100;
    private static final int NUMBER_OF_COLUMNS = 7;

    String[] arrayOfGridItems;
    Context context;

    NoteGridViewAdapterListener adapterListener;

    double screenWidth;
    double screenHeight;

    private static LayoutInflater inflater=null;
    public NoteGridViewAdapter(Activity presentedActivity, String[] arrayOfGridItems) {
        this.arrayOfGridItems = arrayOfGridItems;
        context = presentedActivity;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // Getting screen size as per https://stackoverflow.com/questions/6520718/how-to-get-screen-width-and-height
        Point displaySize = new Point();
        presentedActivity.getWindowManager().getDefaultDisplay().getRealSize(displaySize);

        // TODO: Fix the height here...
        screenHeight = presentedActivity.getResources().getDisplayMetrics().heightPixels - GRID_VIEW_MARGIN_TOP;


        Log.d("GridView", "screenWidth: " + screenWidth);
        //Log.d("GridView", "screenHeight: " + screenHeight);


    }

    @Override
    public int getCount() {
        return arrayOfGridItems.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View cellView = inflater.inflate(R.layout.note_grid_cell, null);
        TextView noteCellTextView = (TextView) cellView.findViewById(R.id.textView1);

        // TODO: Fix the height here...
        // Setting height to 50% of screen, as per https://stackoverflow.com/questions/2963152/android-how-to-resize-a-custom-view-programmatically
        int cellHeight = (int)(screenHeight / 3);
        int cellWidth = 330;

        cellView.setLayoutParams(new LinearLayout.LayoutParams(cellWidth, cellHeight));

        noteCellTextView.setText(arrayOfGridItems[position]);

        int colourPosition = position % NUMBER_OF_COLUMNS;

        // Set background colour for the cell depending upon its position
        switch (colourPosition) {
            case 0:
                noteCellTextView.setBackgroundResource(R.color.red);
                break;
            case 1:
                noteCellTextView.setBackgroundResource(R.color.pink);
                break;
            case 2:
                noteCellTextView.setBackgroundResource(R.color.purple);
                break;
            case 3:
                noteCellTextView.setBackgroundResource(R.color.deepPurple);
                break;
            case 4:
                noteCellTextView.setBackgroundResource(R.color.indigo);
                break;
            case 5:
                noteCellTextView.setBackgroundResource(R.color.blue);
                break;
            case 6:
                noteCellTextView.setBackgroundResource(R.color.material_deep_teal_500);
                break;
            case 7:
                noteCellTextView.setBackgroundResource(R.color.material_blue_grey_800);
                break;
            default:
                noteCellTextView.setBackgroundResource(R.color.black);
                break;
        }


        cellView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Inform the listener that the cell's been tapped
                if (adapterListener != null) {
                    adapterListener.onItemTapListener(position);
                }

            }
        });

        return cellView;
    }

    public NoteGridViewAdapterListener getAdapterListener() {
        return adapterListener;
    }

    public void setAdapterListener(NoteGridViewAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

}
