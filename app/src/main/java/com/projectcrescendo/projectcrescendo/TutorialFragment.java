package com.projectcrescendo.projectcrescendo;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * This class displays tutorial text to the user, allowing them to view the instructional text
 * explaining to them what they should do at this point in the tutorial to progress further.
 * It is presented by the TutorialActivity.
 * <p>
 * Created by Jordan on 08/03/2016.
 */
public class TutorialFragment extends DialogFragment {

    /**
     * The text for this instruction within the tutorial, set by the TutorialActivity and displayed
     * to the user within a TextView in this fragment.
     */
    private String tutorialText;

    /**
     * The TextView that contains the tutorial text to display to the user in this fragment.
     */
    private TextView tutorialTextView;


    /**
     * Inflates this view from the XML Layout.
     *
     * @param inflater           the layout inflater, provided by the OS.
     * @param container          the view that this view is to be presented in.
     * @param savedInstanceState any saved instance state.
     * @return an inflated version of this fragment's XML layout.
     */
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


        Button closeButton = (Button) this.getView().findViewById(R.id.tutorial_Button);

        closeButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        getActivity().getFragmentManager().popBackStack();
                    }
                }
        );

    }

    /**
     * Returns the tutorial text that is displayed within this fragment.
     *
     * @return the tutorial instruction text that is displayed to the user within this fragment.
     */
    public String getTutorialText() {
        return tutorialText;
    }

    /**
     * Sets the tutorial instruction text to display to the user within this fragment.
     *
     * @param tutorialText the text to display to the user in this fragment.
     */
    public void setTutorialText(String tutorialText) {
        this.tutorialText = tutorialText;

        if (tutorialTextView != null) {
            tutorialTextView.setText(tutorialText);
        }

    }

}