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
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

import java.util.List;

public final class NoteBodyDrawer {

    public static final int NOTE_WIDTH_SCALE = 130 / 100;

    private SymbolDrawer symbolDrawer;
    private SymbolDotDrawer symbolDotDrawer;
    private NoteSheetCanvas noteSheetCanvas;
    private MusicalKey key;
    private int distanceBetweenLines;

    public NoteBodyDrawer(SymbolDrawer symbolDrawer, NoteSheetCanvas noteSheetCanvas, MusicalKey key, int distanceBetweenLines) {
        this.symbolDrawer = symbolDrawer;
        this.symbolDotDrawer = new SymbolDotDrawer(noteSheetCanvas, distanceBetweenLines);
        this.noteSheetCanvas = noteSheetCanvas;
        this.key = key;
        this.distanceBetweenLines = distanceBetweenLines;
    }

    public SymbolPosition drawBody(NoteSymbol noteSymbol, Paint paint) {
        boolean isStemUpdirected = noteSymbol.isStemUp(key);
        int lineHeight = distanceBetweenLines;
        int noteHeight = lineHeight / 2;
        int noteWidth = noteHeight * NOTE_WIDTH_SCALE;

        Point centerPointOfSpaceForNote = symbolDrawer.getCenterPointForNextSymbol();
        List<NoteName> sortedNoteNames = noteSymbol.getNoteNamesSorted();
        RectF[] noteSurroundingRects = new RectF[sortedNoteNames.size()];
        NoteName prevNoteName = null;

        for (int i = 0; i < sortedNoteNames.size(); i++) {
            NoteName noteName = sortedNoteNames.get(i);
            NoteLength noteLength = noteSymbol.getNoteLength(noteName);
            Point centerPointOfActualNote = new Point(centerPointOfSpaceForNote);
            centerPointOfActualNote.y += NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName)
                    * noteHeight;
            int left = centerPointOfActualNote.x - noteWidth;
            int top = centerPointOfActualNote.y - noteHeight;
            int right = centerPointOfActualNote.x + noteWidth;
            int bottom = centerPointOfActualNote.y + noteHeight;

            if (prevNoteName != null) {
                int differenceBetweenNotesInHalfTones = Math.abs(NoteName.calculateDistanceCountingNoneSignedNotesOnly(
                        prevNoteName, noteName));

                if (differenceBetweenNotesInHalfTones == 1) {
                    if (isStemUpdirected) {
                        right += 2 * noteWidth;
                        left += 2 * noteWidth;
                    } else {
                        left -= 2 * noteWidth;
                        right -= 2 * noteWidth;
                    }
                }
            }

            RectF noteRect = new RectF(left, top, right, bottom);

            noteSurroundingRects[i] = noteRect;
            Paint.Style savedStyle = paint.getStyle();

            if (noteLength.isFilled()) {
                paint.setStyle(Paint.Style.FILL);
            } else {
                paint.setStyle(Paint.Style.STROKE);
            }

            noteSheetCanvas.drawOval(noteRect, paint);
            paint.setStyle(savedStyle);

            if (noteLength.hasDot()) {
                Rect roundedNoteRect = new Rect();
                noteRect.roundOut(roundedNoteRect);
                symbolDotDrawer.drawDot(new Rect(roundedNoteRect), paint);
            }

            prevNoteName = noteName;
        }

        return new SymbolPosition(noteSurroundingRects);
    }
}
