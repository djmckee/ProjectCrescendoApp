package com.projectcrescendo.projectcrescendo;

/**
 * Created by Alexander on 23/02/2016.
 */
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

interface CustomAdapterListener {
    void onItemTapListener(int itemPosition);
}

public class CustomAdapter extends BaseAdapter{

    String [] result;
    Context context;

    CustomAdapterListener adapterListener;

    double screenWidth;
    double screenHeight;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Activity mainActivity, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // Getting screen size as per https://stackoverflow.com/questions/6520718/how-to-get-screen-width-and-height
        Point displaySize = new Point();
        mainActivity.getWindowManager().getDefaultDisplay().getRealSize(displaySize);
        screenHeight = displaySize.x;
        screenWidth = displaySize.y;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
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
        // TODO Auto-generated method stub

        View rowView;
        TextView tv;
        rowView = inflater.inflate(R.layout.programlist, null);

        // Setting height to 50% of screen, as per https://stackoverflow.com/questions/2963152/android-how-to-resize-a-custom-view-programmatically
        rowView.setLayoutParams(new LinearLayout.LayoutParams((int)(screenWidth / 4), (int)(screenHeight / 4)));


        tv=(TextView) rowView.findViewById(R.id.textView1);


        tv.setText(result[position]);


        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();

                if (adapterListener != null) {
                    adapterListener.onItemTapListener(position);
                }

            }
        });

        return rowView;
    }

    public CustomAdapterListener getAdapterListener() {
        return adapterListener;
    }

    public void setAdapterListener(CustomAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

}
