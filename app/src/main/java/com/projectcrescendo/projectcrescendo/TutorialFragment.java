package com.projectcrescendo.projectcrescendo;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by jordan on 08/03/2016.
 */
public class TutorialFragment extends DialogFragment {

    // TODO: Add JavaDoc comments explaining what this class does, what the variables hold and what the methods do.

    private String tutorialText;
    private TextView tutorialTextView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tutorial_fragment,
                container, false);
    }

    /**
     * When the fragment is created, this method ensures that the UI elements are instantiated from
     * the XML and refreshes the notes list and intonation initially.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tutorialTextView = (TextView) this.getView().findViewById(R.id.tutorial_Text_View);
        if (tutorialText != null) {
            tutorialTextView.setText(tutorialText);
        }


        Button closebutton = (Button) this.getView().findViewById(R.id.tutorial_Button);

        closebutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        getActivity().getFragmentManager().popBackStack();
                    }
                }
        );

    }

    public String getTutorialText() {
        return tutorialText;
    }

    public void setTutorialText(String tutorialText) {
        this.tutorialText = tutorialText;

        if (tutorialTextView != null) {
            tutorialTextView.setText(tutorialText);
        }

    }


    // TODO: Probably delete this code!??!? (* confused emoji  *)
    //  public View onCreateView(LayoutInflater inflater, ViewGroup container,
    //                         Bundle savedInstanceState) {
//
//        View v = inflater.inflate(R.layout.tutoiral_fragment, container, false);
//        View tv = v.findViewById(R.id.text);
//
//        TextView  textView =  (TextView) v.findViewById(R.id.tutorial1textview); //add the textview to the fragment
//        textView.setText(gettutorialText()); //set the text to display on the screen
//
//        Button exitButton = (Button)v.findViewById(R.id.tutorial_Button);
//        exitButton.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                FragmentManager fm = getFragmentManager();
//                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//                FragmentOne f = (FragmentOne) fm.findFragmentByTag("tag");
//
//                if(f == null) {  // not added
//                    f = new FragmentOne();
//                    ft.add(R.id.frg1, f, "tag");
//                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//
//                } else {  // already added
//
//                    ft.remove(f);
//                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//                }
//
//                ft.commit();
//            }
//        });
//
//        return v;
//    }



}