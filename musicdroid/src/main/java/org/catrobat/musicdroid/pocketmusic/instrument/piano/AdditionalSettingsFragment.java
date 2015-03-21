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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.catrobat.musicdroid.pocketmusic.R;

public class AdditionalSettingsFragment extends Fragment {

    private Button switchOctaveUpButton;
    private Button switchOctaveDownButton;
    private Button switchToBreakViewButton;

    private boolean pianoViewVisible = true;
    private PianoActivity pianoActivity;

    public AdditionalSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_additional_piano_settings, container, false);
        pianoActivity = (PianoActivity) getActivity();

        switchToBreakViewButton = (Button) rootView.findViewById(R.id.switch_to_break_button);
        switchOctaveDownButton = (Button) rootView.findViewById(R.id.switch_octave_down);
        switchOctaveUpButton = (Button) rootView.findViewById(R.id.switch_octave_up);

        setOnClickListeners();

        return rootView;
    }

    public void setOnClickListeners() {
        switchToBreakViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPianoViewVisible()) {
                    pianoActivity.switchToBreakView();
                } else {
                    pianoActivity.switchToPianoView();
                }
            }
        });

        switchOctaveDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pianoActivity.setOctave(pianoActivity.getOctave().previous());
            }
        });

        switchOctaveUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pianoActivity.setOctave(pianoActivity.getOctave().next());
            }
        });
    }

    public void setPianoViewVisible(boolean pianoViewVisible) {
        this.pianoViewVisible = pianoViewVisible;
    }

    public boolean isPianoViewVisible() {
        return pianoViewVisible;
    }
}
