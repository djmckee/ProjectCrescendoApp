package com.projectcrescendo.projectcrescendo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.content.Context;

import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;




// got code from http://prasans.info/2011/03/add-edittexts-dynamically-and-retrieve-values-android/
public class TutorialActivity extends ActionBarActivity {

    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"c","a","b","f","e","b","c","a"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        gv=(GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new CustomAdapter(this, prgmNameList));
    }




}
