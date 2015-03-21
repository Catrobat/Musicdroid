/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.musicdroid.pocketmusic.instrument.noteSheet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.InstrumentActivity;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Octave;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;
import org.catrobat.musicdroid.pocketmusic.tools.DisplayMeasurements;

public class NoteSheetViewFragment extends Fragment {

    private NoteSheetView noteSheetView;
    private TextView trackSizeTextView;
    private TextView octaveTextView;

    private DisplayMeasurements displayMeasurements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_notesheetview, container, false);

        displayMeasurements = new DisplayMeasurements(getActivity());
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, displayMeasurements.getHalfDisplayHeight()));

        noteSheetView = (NoteSheetView) rootView.findViewById(R.id.note_sheet_view);
        trackSizeTextView = (TextView) rootView.findViewById(R.id.track_size_text_view);
        octaveTextView = (TextView) rootView.findViewById(R.id.octave_state_text_view);

        noteSheetView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ((PianoActivity) getActivity()).startEditMode();
                return false;
            }
        });

        noteSheetView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (PianoActivity.inCallback) {
                    noteSheetView.onEditMode(motionEvent);
                    ((PianoActivity) getActivity()).notifyCheckedItemStateChanged();
                }

                return false;
            }
        });

        return rootView;
    }

    public void redraw(SymbolContainer symbolContainer) {
        noteSheetView.redraw(symbolContainer);
        setSymbolCountText(symbolContainer.size());
        setOctaveText(((PianoActivity) getActivity()).getOctave());
    }

    public String getTrackSizeTextViewText() {
        return trackSizeTextView.getText().toString();
    }

    public NoteSheetView getNoteSheetView() {
        return noteSheetView;
    }

    public boolean checkForScrollAndRecalculateWidth() {
        return noteSheetView.checkForScrollAndRecalculateWidth();
    }

    public void setSymbolCountText(int symbolCount) {
        trackSizeTextView.setText(symbolCount + " / " + InstrumentActivity.MAX_SYMBOLS_SIZE);
    }

    public String getOctaveText() {
        return octaveTextView.getText().toString();
    }

    public void setOctaveText(Octave octave) {
        int offset = octave.ordinal() - Octave.DEFAULT_OCTAVE.ordinal();

        octaveTextView.setText(getString(R.string.octave) + " " + offset);
    }
}
