package com.projectcrescendo.projectcrescendo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
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
 * <p>
 * This interface allows us to maintain a good level of abstraction, and adhere to the
 * Model-View-Controller design pattern.
 * <p>
 * Created by Dylan McKee on 24/02/2016.
 */
interface NoteGridViewAdapterListener {
    /**
     * This callback method is fired when an item in the grid of beats is tapped.
     *
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
class NoteGridViewAdapter extends BaseAdapter {

    /**
     * The margin between the top of the screen and the grid, in dp.
     */
    private static final int GRID_VIEW_MARGIN_TOP = 100;

    /**
     * An array containing colour resources to use as grid background colours.
     */
    private static final int[] GRID_COLOUR_RESOURCES = {R.color.red,
            R.color.pink, R.color.purple, R.color.deepPurple,
            R.color.indigo, R.color.blue, R.color.material_deep_teal_500};

    /**
     * A layout inflater to instantiate the xml layout for the grid cells.
     */
    private static LayoutInflater inflater = null;

    /**
     * A string array containing the grid items to display.
     */
    private final String[] arrayOfGridItems;
    /**
     * The height of the screen that the grid is presented on, in pixels.
     */
    private final double screenHeight;
    /**
     * A callback listener to provide callbacks when the cells in the grid are tapped.
     */
    private NoteGridViewAdapterListener adapterListener;

    /**
     * Constructs a NoteGridViewAdapter.
     *
     * @param presentedActivity the activity that the grid is contained within.
     * @param arrayOfGridItems  the String items to display in the grid.
     */
    public NoteGridViewAdapter(Activity presentedActivity, String[] arrayOfGridItems) {
        this.arrayOfGridItems = arrayOfGridItems;

        inflater = (LayoutInflater) presentedActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Getting screen size as per https://stackoverflow.com/questions/6520718/how-to-get-screen-width-and-height
        Point displaySize = new Point();
        presentedActivity.getWindowManager().getDefaultDisplay().getRealSize(displaySize);

        // Compute height by removing padding from display height in pixels.
        screenHeight = presentedActivity.getResources().getDisplayMetrics().heightPixels - GRID_VIEW_MARGIN_TOP;

    }

    /**
     * Returns the number of items to be displayed in the grid.
     *
     * @return the number of items to display in the grid.
     */
    @Override
    public int getCount() {
        return arrayOfGridItems.length;
    }

    /**
     * Returns the item at the index passed into the this method.
     *
     * @param position the index of the item to return
     * @return the item at the 'position' index.
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * Returns a unique identifying integer for the item at that position.
     *
     * @param position the position to return the ID for.
     * @return the unique ID of the item.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Creates, sets up and returns the cell view for the grid at the specified index.
     *
     * @param position    the index to create and return the grid cell for.
     * @param convertView the blank grid view cell, to add our custom view into.
     * @param parent      the grid view instance.
     * @return a grid view cell set up for the current position index in the grid.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View cellView = inflater.inflate(R.layout.note_grid_cell, null);
        TextView noteCellTextView = (TextView) cellView.findViewById(R.id.textView1);

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

    /**
     * Returns the listener for the current instance.
     *
     * @return the listener for the current instance.
     */
    public NoteGridViewAdapterListener getAdapterListener() {
        return adapterListener;
    }

    /**
     * Sets the grid view adapter listener for the current instance.
     *
     * @param adapterListener the new listener for the current instance.
     */
    public void setAdapterListener(NoteGridViewAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

}
