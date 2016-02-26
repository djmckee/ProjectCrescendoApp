package com.projectcrescendo.projectcrescendo;

/**
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

interface CustomAdapterListener {
    void onItemTapListener(int itemPosition);
}

public class CustomAdapter extends BaseAdapter{

    private static int GRID_VIEW_MARGIN_TOP = 100;
    private static int GRID_VIEW_MARGIN_SIDES = 350;

    String[] arrayOfGridItems;
    Context context;

    CustomAdapterListener adapterListener;

    double screenWidth;
    double screenHeight;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Activity presentedActivity, String[] arrayOfGridItems) {
        // TODO Auto-generated constructor stub
        this.arrayOfGridItems = arrayOfGridItems;
        context = presentedActivity;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // Getting screen size as per https://stackoverflow.com/questions/6520718/how-to-get-screen-width-and-height
        Point displaySize = new Point();
        presentedActivity.getWindowManager().getDefaultDisplay().getRealSize(displaySize);
        screenHeight = (displaySize.x) - GRID_VIEW_MARGIN_TOP;
        screenWidth = (displaySize.y) - GRID_VIEW_MARGIN_SIDES;

        Log.d("GridView", "screenWidth: " + screenWidth);
        Log.d("GridView", "screenHeight: " + screenHeight);


    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayOfGridItems.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View cellView = inflater.inflate(R.layout.note_grid_cell, null);
        TextView noteCellTextView = (TextView) cellView.findViewById(R.id.textView1);

        // Setting height to 50% of screen, as per https://stackoverflow.com/questions/2963152/android-how-to-resize-a-custom-view-programmatically
        int cellHeight = (int)(screenWidth / 2);
        int cellWidth = (int)(screenHeight / 4);

        cellView.setLayoutParams(new LinearLayout.LayoutParams(cellWidth, cellHeight));

        noteCellTextView.setText(arrayOfGridItems[position]);

        // Set background colour for the cell depending upon its position
        switch (position) {
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

    public CustomAdapterListener getAdapterListener() {
        return adapterListener;
    }

    public void setAdapterListener(CustomAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

}
