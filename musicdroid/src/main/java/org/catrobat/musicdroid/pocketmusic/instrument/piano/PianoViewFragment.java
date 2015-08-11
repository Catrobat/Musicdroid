package org.catrobat.musicdroid.pocketmusic.instrument.piano;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Octave;
import org.catrobat.musicdroid.pocketmusic.tools.DisplayMeasurements;

import java.util.ArrayList;

public class PianoViewFragment extends Fragment {

    public static int DEFAULT_INACTIVE_BLACK_KEY = 2;
    public static int DEFAULT_BLACK_KEY_HEIGHT_SCALE_FACTOR = 6;
    public static int DEFAULT_PIANO_KEY_WIDTH_SCALE_FACTOR = 0;
    public static int DEFAULT_LANDSCAPE_KEY_WIDTH_SCALE_FACTOR = 1;

    private ArrayList<Button> whiteButtons;
    private ArrayList<Button> blackButtons;

    private DisplayMeasurements displayMeasurements;

    public PianoViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_piano, container, false);
        displayMeasurements = new DisplayMeasurements(getActivity());
        whiteButtons = new ArrayList<>();
        blackButtons = new ArrayList<>();

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, displayMeasurements.getHalfDisplayHeight()));

        findViewsById(rootView);
        prepareViewDependingOnOrientation();
        setOnTouchListeners();

        return rootView;
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

    public void prepareViewDependingOnOrientation() {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            calculatePianoKeyPositions(DEFAULT_LANDSCAPE_KEY_WIDTH_SCALE_FACTOR,
                    DEFAULT_BLACK_KEY_HEIGHT_SCALE_FACTOR);
        else
            calculatePianoKeyPositions(DEFAULT_PIANO_KEY_WIDTH_SCALE_FACTOR,
                    DEFAULT_BLACK_KEY_HEIGHT_SCALE_FACTOR);

        setBlackKeyInvisible(DEFAULT_INACTIVE_BLACK_KEY);
    }

    private void setOnTouchListeners() {
        int j = 0;
        for (int i = 0; i < whiteButtons.size(); i++) {
            whiteButtons.get(i).setOnTouchListener(setOnTouchPianoKey(j));
            if (i == 2)
                j += 1;
            else
                j += 2;
        }
        j = 1;
        for (int i = 0; i < blackButtons.size(); i++) {
            blackButtons.get(i).setOnTouchListener(setOnTouchPianoKey(j));
            if (i == 2)
                j += 1;
            else
                j += 2;
        }
    }

    public void calculatePianoKeyPositions(int pianoKeyWidthScaleFactor, int pianoBlackKeyHeightScaleFactor) {

        int buttonWidth = displayMeasurements.getDisplayWidth() / (Octave.NUMBER_OF_UNSIGNED_HALF_TONE_STEPS_PER_OCTAVE + pianoKeyWidthScaleFactor);

        int blackButtonMargin = getResources().getDimensionPixelSize(R.dimen.black_button_margin);
        int whiteButtonMargin = getResources().getDimensionPixelSize(R.dimen.white_button_margin);

        for (int i = 0; i < blackButtons.size(); i++) {
            RelativeLayout.LayoutParams black_button_rlp = new RelativeLayout.LayoutParams(
                    buttonWidth - blackButtonMargin,
                    displayMeasurements.getDisplayHeight() / pianoBlackKeyHeightScaleFactor
            );

            black_button_rlp.setMargins(((((i * 2) + 1) * buttonWidth) + blackButtonMargin) / 2, 0, 0, 0);
            blackButtons.get(i).setLayoutParams(black_button_rlp);
        }

        for (int i = 0; i < whiteButtons.size(); i++) {
            RelativeLayout.LayoutParams whiteButtonRlp = new RelativeLayout.LayoutParams(
                    buttonWidth - whiteButtonMargin,
                    RelativeLayout.LayoutParams.MATCH_PARENT
            );

            whiteButtonRlp.setMargins(buttonWidth * i, 0, 0, 0);
            whiteButtons.get(i).setLayoutParams(whiteButtonRlp);
        }
    }

    public void setBlackKeyInvisible(int index) {
        if ((index < blackButtons.size()) && (index > 0))
            blackButtons.get(index).setVisibility(View.INVISIBLE);
    }

    private View.OnTouchListener setOnTouchPianoKey(final int index) {
        return (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final Octave octave = ((PianoActivity) getActivity()).getOctave();
                if (isDownActionEvent(event)) {
                    addKeyPress(new NoteEvent(octave.getNoteNames()[index], true));
                } else if (isUpActionEvent(event)) {
                    addKeyPress(new NoteEvent(octave.getNoteNames()[index], false));
                }

                return false;
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
        if ((index < blackButtons.size()) && (index >= 0)) {
            return blackButtons.get(index);
        }

        return null;
    }

    public int getBlackButtonCount() {
        return blackButtons.size();
    }

    public Button getWhiteButtonAtIndex(int index) {
        if ((index < whiteButtons.size()) && (index >= 0)) {
            return whiteButtons.get(index);
        }

        return null;
    }

    public int getWhiteButtonCount() {
        return whiteButtons.size();
    }
}
