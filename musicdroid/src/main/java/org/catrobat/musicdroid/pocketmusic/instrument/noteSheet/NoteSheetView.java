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

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.draw.DrawElementsTouchDetector;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;
import org.catrobat.musicdroid.pocketmusic.tools.DisplayMeasurements;

public class NoteSheetView extends View {

    protected DrawElementsTouchDetector touchDetector;

    protected SymbolContainer symbolContainer;

    protected NoteSheetCanvas noteSheetCanvas;
    protected NoteSheetDrawer noteSheetDrawer;
    protected int widthBeforeResize;

    private DisplayMeasurements displayMeasurements;

    public NoteSheetView(Context context, AttributeSet attrs) {
        super(context, attrs);

        touchDetector = new DrawElementsTouchDetector();
        widthBeforeResize = getWidth();

        if (context instanceof Activity) {
            displayMeasurements = new DisplayMeasurements((Activity) getContext());
        }
    }

    public boolean checkForScrollAndRecalculateWidth() {
        if (widthBeforeResize != getWidth()) {
            widthBeforeResize = getWidth();
            return true;
        }

        return false;
    }

    public void redraw(SymbolContainer symbolContainer) {
        this.symbolContainer = symbolContainer;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        remeasureDisplayForDrawing();
    }

    private void remeasureDisplayForDrawing() {
        if (noteSheetDrawer == null) {
            setMeasuredDimension(displayMeasurements.getDisplayWidth(), displayMeasurements.getHalfDisplayHeight());
        } else {
            int trackWidth = noteSheetDrawer.getWidthForDrawingTrack();
            if (trackWidth < displayMeasurements.getDisplayWidth()) {
                setMeasuredDimension(displayMeasurements.getDisplayWidth(), getHeight());
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
        noteSheetDrawer = new NoteSheetDrawer(noteSheetCanvas, getResources(), symbolContainer);
        noteSheetDrawer.drawNoteSheet();
        ((PianoActivity) getContext()).scrollNoteSheet();
    }

    public boolean onEditMode(MotionEvent e) {
        if (MotionEvent.ACTION_UP == e.getAction()) {
            float widthForOneSymbol = noteSheetDrawer.getWidthForOneSymbol();
            float tolerance = widthForOneSymbol / 4f;
            int index = touchDetector.getIndexOfTouchedDrawElement(symbolContainer, e.getX(), e.getY(), tolerance, widthForOneSymbol, noteSheetDrawer.getStartPositionForSymbols());

            if (DrawElementsTouchDetector.INVALID_INDEX != index) {
                Symbol symbol = symbolContainer.get(index);
                symbol.setMarked(!symbol.isMarked());
                invalidate();
            }
        }

        return true;
    }
}
