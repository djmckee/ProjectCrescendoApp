package com.projectcrescendo.projectcrescendo;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.MenuItem;

/**
 * A menu to select the tutorial from - tutorial names are to be shown in a list, and a
 * TutorialActivity taking you through the selected tutorial is pushed when you tap on a selection.
 * <p>
 * Created by Jordan Dixon on 01/03/2016.
 */

//http://www.tutorialspoint.com/android/android_list_view.htm
//resource Used

public class TutorialSelectionMenuActivity extends Activity {

    final String[] tutorialArray = {"Tutorial 1, Tutorial 2", "Tutorial 3", "Tutorial 4", "Tutorial 5"}; //array to appear in listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        ListAdapter adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1 , tutorialArray); //creates a view for each array item
        ListView listView = (ListView) findViewById(R.id.listview);//list in activity_instructions
        listView.setAdapter(adapter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public  void onItemClick(AdapterView<?> parent,View view, int position, long id ){
                String.valueOf(parent.getItemAtPosition(position));
                //needs implementation
            }
        });


    }
}
