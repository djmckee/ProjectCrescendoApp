package com.projectcrescendo.projectcrescendo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jordan on 08/03/2016.
 */
public class Tutorial_Fragment extends DialogFragment {
    private String settext;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView  textView =  (TextView) container.findViewById(R.id.tutorial1textview); //add the textview to the fragment
        textView.setText(gettutorialText()); //set the text to display on the screen
        return inflater.inflate(R.layout.tutorial_fragment, container, false);

    }

    public void setTutorialText(String setText){
        this.settext = setText; //set text for all the tutorials
    }

    private String gettutorialText(){
        return settext;
    }

}
