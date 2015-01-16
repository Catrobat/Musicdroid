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
package org.catrobat.musicdroid.pocketmusic.instrument.noteSheet;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.DrawElementsTouchDetector;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolCoordinates;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.TrackToSymbolsConverter;

import java.util.LinkedList;
import java.util.List;

public class NoteSheetView extends View {

    private DrawElementsTouchDetector touchDetector;
    private TrackToSymbolsConverter trackConverter;
    private List<Symbol> symbols;
    private MusicalKey key;
    private List<SymbolCoordinates> symbolCoordinates;

	private NoteSheetCanvas noteSheetCanvas;
    private NoteSheetDrawer noteSheetDrawer;
    private int widthBeforeResize;

	public NoteSheetView(Context context, AttributeSet attrs) {
		super(context, attrs);

        touchDetector = new DrawElementsTouchDetector();
        trackConverter = new TrackToSymbolsConverter();
        symbols = new LinkedList<Symbol>();
        symbolCoordinates = new LinkedList<SymbolCoordinates>();
        key = MusicalKey.VIOLIN;
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
        key = track.getKey();
        symbols = trackConverter.convertTrack(track);
		invalidate();
	}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        remeasureDisplayForDrawing();
    }

    private void remeasureDisplayForDrawing() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        if(noteSheetDrawer == null) {
            setMeasuredDimension(screenWidth, screenHeight / 2);
        } else {
            int trackWidth = noteSheetDrawer.getWidthForDrawingTrack();
            if (trackWidth < screenWidth) {
                setMeasuredDimension(screenWidth, getHeight());
            } else {
                setMeasuredDimension(trackWidth, getHeight());
            }
        }
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		noteSheetCanvas = new NoteSheetCanvas(canvas);
        requestLayout();
        noteSheetDrawer = new NoteSheetDrawer(noteSheetCanvas, getResources(), symbols, key);
        symbolCoordinates = noteSheetDrawer.drawNoteSheet();
        ((PianoActivity) getContext()).scrollNoteSheet();
	}

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (MotionEvent.ACTION_UP == e.getAction()) {
            int index = touchDetector.getIndexOfTouchedDrawElement(symbolCoordinates, e.getX(), e.getY());

            if (-1 != index) {
                Symbol symbol = symbols.get(index);
                symbol.setMarked(symbol.isMarked());
            }
        }

        return true;
    }
}