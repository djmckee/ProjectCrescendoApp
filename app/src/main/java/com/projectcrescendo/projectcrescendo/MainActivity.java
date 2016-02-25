package com.projectcrescendo.projectcrescendo;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.content.Intent;



public class MainActivity extends ActionBarActivity  {

    /**
     * The tutorial manager instance.
     */
    private TutorialManager tutorialManager;


    /**
     * This method is ran on creation of the view. UI and listeners to be set-up here.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blink();

        tutorialManager = new TutorialManager(this);

        Log.d("Debug", "tutorials: " + tutorialManager.getTutorialsList());

        NoteManager noteManager = new NoteManager(this);
        Log.d("Debug", "note names: " + noteManager.getNoteNames());


        // hello world.

        FloatingActionButton startTutorials = (FloatingActionButton) findViewById(R.id.startTutorialsButton);
        startTutorials.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //startActivity(new Intent(MainActivity.this, TutorialActivity.class));
                Log.d("MainActivity", "starting activity...");
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });
    }

    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 700;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.tab_to_start);
                        if(txt.getVisibility() == View.VISIBLE){
                            txt.setVisibility(View.INVISIBLE);
                        }else{
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
}
