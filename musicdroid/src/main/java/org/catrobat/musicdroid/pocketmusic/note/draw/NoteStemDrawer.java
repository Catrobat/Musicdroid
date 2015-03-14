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

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteFlag;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

public class NoteStemDrawer {

    public static final double LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES = 2.5;

    private NoteFlagDrawer noteFlagDrawer;
    private NoteSheetCanvas noteSheetCanvas;
    private int distanceBetweenLinesHalf;
    private int stemLength;

    public NoteStemDrawer(NoteSheetCanvas noteSheetCanvas, int distanceBetweenLines) {
        noteFlagDrawer = new NoteFlagDrawer(noteSheetCanvas, distanceBetweenLines);
        this.noteSheetCanvas = noteSheetCanvas;
        this.distanceBetweenLinesHalf = distanceBetweenLines / 2;
        this.stemLength = (int) (Math.round(LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES * distanceBetweenLines));
    }

    public RectF drawStem(SymbolPosition symbolPosition, NoteSymbol noteSymbol, MusicalKey key, Paint paint) {
        RectF stemRect = new RectF();
        PointF endPointOfNoteStem = new PointF();

        if (noteSymbol.isStemUp(key)) {
            stemRect.left = symbolPosition.getRight();
            stemRect.right = symbolPosition.getRight();
            stemRect.bottom = symbolPosition.getBottom() - distanceBetweenLinesHalf;
            stemRect.top = symbolPosition.getTop() - stemLength;

            endPointOfNoteStem.x = stemRect.left;
            endPointOfNoteStem.y = stemRect.top;
        } else {
            stemRect.left = symbolPosition.getLeft();
            stemRect.right = symbolPosition.getLeft();
            stemRect.top = symbolPosition.getTop() + distanceBetweenLinesHalf;
            stemRect.bottom = symbolPosition.getBottom() + stemLength;

            endPointOfNoteStem.x = stemRect.left;
            endPointOfNoteStem.y = stemRect.bottom;
        }

        noteSheetCanvas.drawLine(stemRect.left, stemRect.bottom, stemRect.right,
                stemRect.top, paint);

        if (NoteFlag.NO_FLAG != noteSymbol.getFlag()) {
            drawFlag(endPointOfNoteStem, noteSymbol, key, paint);
        }

        return stemRect;
    }

    private void drawFlag(PointF endPointOfNoteStem, NoteSymbol noteSymbol, MusicalKey key, Paint paint) {
        noteFlagDrawer.drawFlag(endPointOfNoteStem, noteSymbol, key, paint);
    }
}
