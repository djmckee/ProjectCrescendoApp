package com.twobard.pianoview;

import java.util.ArrayList;

/**
 * This code is from GitHub, originally written by 2bard and released on GitHub, and used within our
 * team project to provide a virtual piano instrument - we took the original code from
 * https://github.com/2bard/AndroidPianoView
 * @author https://github.com/2bard
 *
 */
public class Finger {
    private ArrayList<Key> keys = new ArrayList<Key>();
    
    public Boolean isPressing(Key key){
        return this.keys.contains(key);
    }

    public void press(Key key){
        
        if(this.isPressing(key)){
            return;
        }
        
        key.press(this);
        this.keys.add(key);
    }
    
    public void lift(){
        for(Key key : keys){
            key.depress(this);
        }
        keys.clear();
    }
}
