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

package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetView;
import org.catrobat.musicdroid.pocketmusic.properties.PianoProperties;

/**
 * Created by Andrej on 23.06.2014.
 */
public class NoteSheetViewFragment extends Fragment {
    private NoteSheetView noteSheetView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState == null)
          noteSheetView = new NoteSheetView(getActivity());
        noteSheetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getDisplayHeight()/3));
        noteSheetView.setBackgroundColor(Color.WHITE);
        return noteSheetView;
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
    private int getDisplayHeight() {
        return initializeDisplay()[PianoProperties.Y_POS];

    }
    public void redraw(Track track){
        noteSheetView.redraw(track);
    }


}
