package com.projectcrescendo.projectcrescendo;



import android.app.Activity;
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

/**
 * A callback interface so that the activity presenting the note grid view (i.e. the TutorialActivity)
 * can receive callbacks when a
 *
 * This interface allows us to maintain a good level of abstraction, and adhere to the
 * Model-View-Controller design pattern.
 *
 * Created by Dylan McKee on 24/02/2016.
 */
interface NoteGridViewAdapterListener {
    /**
     * This callback method is fired when an item in the grid of beats is tapped.
     * @param itemPosition the position of the item tapped in the grid, zero-indexed so as to line up
     *                     with the array containing the data that populates the grid.
     */
    void onItemTapListener(int itemPosition);
}

/**
 * A Grid View Adapter to allow us to use the GridView in the TutorialActivity to display a list
 * of notes.
 * <p>
 * Created by Alexander on 23/02/2016.
 */
public class NoteGridViewAdapter extends BaseAdapter {

    private static final int GRID_VIEW_MARGIN_TOP = 100;
    private static final int[] GRID_COLOUR_RESOURCES = {R.color.red,
            R.color.pink, R.color.purple, R.color.deepPurple,
            R.color.indigo, R.color.blue, R.color.material_deep_teal_500};
    private static LayoutInflater inflater = null;
    String[] arrayOfGridItems;
    Context context;
    NoteGridViewAdapterListener adapterListener;
    double screenWidth;
    double screenHeight;

    public NoteGridViewAdapter(Activity presentedActivity, String[] arrayOfGridItems) {
        this.arrayOfGridItems = arrayOfGridItems;
        context = presentedActivity;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
        int cellHeight = (int) (screenHeight / 3);
        int cellWidth = 330;

        cellView.setLayoutParams(new LinearLayout.LayoutParams(cellWidth, cellHeight));

        noteCellTextView.setText(arrayOfGridItems[position]);

        int colourPosition = position % GRID_COLOUR_RESOURCES.length;

        // Set background colour for the cell depending upon its position
        noteCellTextView.setBackgroundResource(GRID_COLOUR_RESOURCES[colourPosition]);


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
