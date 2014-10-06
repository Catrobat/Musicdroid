package org.catrobat.musicdroid.pocketmusic.instrument.piano;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Octave;

import java.util.ArrayList;

public  class PianoViewFragment extends Fragment {

    public static int DEFAULT_BLACK_KEY_WIDTH_SCALE_FACTOR = 6;
    public static int DEFAULT_PIANO_KEY_HEIGHT_SCALE_FACTOR = 0;

    public static int X_INDEX = 0;
    public static int Y_INDEX = 1;

    private ArrayList<Button> whiteButtons;
    private ArrayList<Button> blackButtons;
    private NoteName[] noteNames;

    public PianoViewFragment() {
        whiteButtons = new ArrayList<>();
        blackButtons = new ArrayList<>();

        Octave octave = Octave.createOneLineOctave();
        noteNames = octave.getNoteNames();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_piano, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getDisplayHeight()/2));

        findViewsById(rootView);

        calculatePianoKeyPositions(DEFAULT_PIANO_KEY_HEIGHT_SCALE_FACTOR,
                DEFAULT_BLACK_KEY_WIDTH_SCALE_FACTOR);

        setBlackKeyVisibilityAtIndex(Button.INVISIBLE, Octave.DEFAULT_INACTIVE_BLACK_KEY_INDEX);

        setOnTouchListeners();
        return rootView;
    }

    private void setOnTouchListeners(){
        int j = 0;
        for(int i = 0; i < whiteButtons.size(); i++){
            whiteButtons.get(i).setOnTouchListener(setOnTouchPianoKey(noteNames[j]));
            // TODO: works only for the specific octave, do for all // in Octave.java - noteNames Array
            if(i == 2)
                j+=1;
            else
                j+=2;
        }
        j = 1;
        for(int i = 0; i < blackButtons.size(); i++){
            blackButtons.get(i).setOnTouchListener(setOnTouchPianoKey(noteNames[j]));
            if(i == 2)
                j+=1;
            else
                j+=2;
        }
    }
    private void findViewsById(View rootView) {
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_01_white));
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_02_white));
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_03_white));
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_04_white));
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_05_white));
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_06_white));
        whiteButtons.add((Button) rootView.findViewById(R.id.oct_button_07_white));

        blackButtons.add((Button) rootView.findViewById(R.id.oct_button_01_black));
        blackButtons.add((Button) rootView.findViewById(R.id.oct_button_02_black));
        blackButtons.add((Button) rootView.findViewById(R.id.oct_button_03_black));
        blackButtons.add((Button) rootView.findViewById(R.id.oct_button_04_black));
        blackButtons.add((Button) rootView.findViewById(R.id.oct_button_05_black));
        blackButtons.add((Button) rootView.findViewById(R.id.oct_button_06_black));
    }

    private int[] initializeDisplay() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int[] widthAndHeight = new int[2];
        widthAndHeight[0] = size.x;
        widthAndHeight[1] = size.y;
        return widthAndHeight;
    }

    public int getDisplayWidth() {
        return initializeDisplay()[X_INDEX];
    }

    public int getDisplayHeight() {
        return initializeDisplay()[Y_INDEX];
    }

    public void calculatePianoKeyPositions(int pianoKeyWidthScaleFactor, int pianoBlackKeyHeightScaleFactor) {

        int buttonWidth = getDisplayWidth() / (Octave.NUMBER_OF_UNSIGNED_HALF_TONE_STEPS_PER_OCTAVE + pianoKeyWidthScaleFactor);

        ArrayList<RelativeLayout.LayoutParams> blackKeyLayoutParams = new ArrayList<>();
        ArrayList<RelativeLayout.LayoutParams> whiteKeyLayoutParams = new ArrayList<>();


        for (int i = 0; i < blackButtons.size(); i++) {

            blackKeyLayoutParams.add(new RelativeLayout.LayoutParams(
                    buttonWidth,
                    getDisplayHeight() / pianoBlackKeyHeightScaleFactor
            ));

            blackKeyLayoutParams.get(i).setMargins((buttonWidth / 2) * ((i * 2) + 1), 0, 0, 0);
            blackButtons.get(i).setLayoutParams(blackKeyLayoutParams.get(i));


        }

        for (int i = 0; i < whiteButtons.size(); i++) {
            whiteKeyLayoutParams.add(new RelativeLayout.LayoutParams(
                    buttonWidth,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            ));

            whiteKeyLayoutParams.get(i).setMargins(buttonWidth * i, 0, 0, 0);
            whiteButtons.get(i).setLayoutParams(whiteKeyLayoutParams.get(i));
        }

    }

    public void setBlackKeyVisibilityAtIndex(int visibility, int index) {
        if((index < blackButtons.size()) && (index >= 0))
            blackButtons.get(index).setVisibility(visibility);
    }

    private View.OnTouchListener setOnTouchPianoKey(final NoteName noteName){
        return (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if (isDownActionEvent(event)) {

                    Log.d("t1", noteName.toString());
                    addKeyPress(new NoteEvent(noteName, true));

                } else if (isUpActionEvent(event)) {

                    addKeyPress(new NoteEvent(noteName, false));
                }

                return true;
            }

            private boolean isDownActionEvent(MotionEvent event) {
                return (event.getAction() == android.view.MotionEvent.ACTION_DOWN);
            }

            private boolean isUpActionEvent(MotionEvent event) {
                return (event.getAction() == android.view.MotionEvent.ACTION_UP);
            }
        });
    }

    private void addKeyPress(NoteEvent noteEvent) {
        PianoActivity pianoActivity = (PianoActivity) getActivity();
        pianoActivity.addNoteEvent(noteEvent);
    }

    public Button getBlackButtonAtIndex(int index) {
        if((index < blackButtons.size()) && (index >= 0)) {
            return blackButtons.get(index);
        }

        return null;
    }

    public int getBlackButtonCount() {
        return blackButtons.size();
    }

    public Button getWhiteButtonAtIndex(int index) {
        if((index < whiteButtons.size()) && (index >= 0)) {
            return whiteButtons.get(index);
        }

        return null;
    }

    public int getWhiteButtonCount() {
        return whiteButtons.size();
    }
}