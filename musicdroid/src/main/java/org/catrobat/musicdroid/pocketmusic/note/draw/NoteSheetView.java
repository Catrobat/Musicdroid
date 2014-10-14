/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class NoteSheetView extends View {

	private Track track;

	private PianoNoteSheetCanvas noteSheetCanvas;
    private int widthBeforeResize;

	public NoteSheetView(Context context, AttributeSet attrs) {
		super(context, attrs);
		track = new Track(MusicalKey.VIOLIN, MusicalInstrument.ELECTRIC_PIANO_1); // TODO fw aus Settings auslesen
        widthBeforeResize = getWidth();
	}

    public boolean checkForScrollAndRecalculateWidth() {
        if (widthBeforeResize != getWidth()) {
            widthBeforeResize = getWidth();
            return true;
        }

        return false;
    }

	public void redraw(Track track) {
		this.track = track;
		invalidate();
	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        remeasureDisplayForDrawing();
    }

    private void remeasureDisplayForDrawing() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();

        if(noteSheetCanvas == null) {
            setMeasuredDimension(d.getWidth(), d.getHeight() / 2); //TODO: Replace deprecated methods
        } else {
            int trackWidth = noteSheetCanvas.getWidthForDrawingTrack();
            if (trackWidth < d.getWidth()) {
                setMeasuredDimension(d.getWidth(), getHeight());
            } else {
                setMeasuredDimension(trackWidth, getHeight());
            }
        }
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		noteSheetCanvas = new PianoNoteSheetCanvas(getResources(), canvas, track);
        requestLayout();
        noteSheetCanvas.draw();
        ((PianoActivity) getContext()).scrollNoteSheet();
	}

}