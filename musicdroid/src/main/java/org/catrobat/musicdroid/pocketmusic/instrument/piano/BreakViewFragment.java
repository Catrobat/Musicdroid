/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2015 The Catrobat Team
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

package org.catrobat.musicdroid.pocketmusic.instrument.piano;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;

public class BreakViewFragment extends Fragment {

    private ImageButton break14Button;
    private ImageButton break18Button;
    private ImageButton break116Button;

    public BreakViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_break, container, false);

        break14Button = (ImageButton) rootView.findViewById(R.id.break_4_button);
        break18Button = (ImageButton) rootView.findViewById(R.id.break_8_button);
        break116Button = (ImageButton) rootView.findViewById(R.id.break_16_button);

        setOnTouchListeners();

        return rootView;
    }

    public void setOnTouchListeners() {
        final PianoActivity pianoActivity = (PianoActivity) getActivity();

        break14Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pianoActivity.addBreak(new BreakSymbol(NoteLength.QUARTER), pianoActivity.getNoteSheetView());
            }
        });

        break18Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pianoActivity.addBreak(new BreakSymbol(NoteLength.EIGHT), pianoActivity.getNoteSheetView());
            }
        });

        break116Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pianoActivity.addBreak(new BreakSymbol(NoteLength.SIXTEENTH), pianoActivity.getNoteSheetView());
            }
        });
    }
}
