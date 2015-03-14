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

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolPosition;

import java.util.LinkedList;
import java.util.List;

public class NoteDrawer extends SymbolDrawer {

    private NoteCrossDrawer noteCrossDrawer;
    private NoteStemDrawer noteStemDrawer;
    private NoteBodyDrawer noteBodyDrawer;

    public NoteDrawer(NoteSheetCanvas noteSheetCanvas, Paint paint, Resources resources, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        super(noteSheetCanvas, paint, resources, key, drawPosition, distanceBetweenLines);

        noteCrossDrawer = new NoteCrossDrawer(noteSheetCanvas, resources, distanceBetweenLines);
        noteStemDrawer = new NoteStemDrawer(noteSheetCanvas, distanceBetweenLines);
        noteBodyDrawer = new NoteBodyDrawer(this, noteSheetCanvas, key, distanceBetweenLines);
    }

    @Override
    protected void drawSymbol(Symbol symbol, Paint paint) {
        if (false == (symbol instanceof NoteSymbol)) {
            throw new IllegalArgumentException("Symbol is not of type NoteSymbol: " + symbol);
        }

        NoteSymbol noteSymbol = (NoteSymbol) symbol;
        List<RectF> crossRects = drawCross(noteSymbol, paint);
        SymbolPosition bodyPosition = drawBody(noteSymbol, paint);
        RectF stemRect = drawStem(noteSymbol, bodyPosition, paint);
        drawHelpLines(bodyPosition, paint);

        SymbolPosition symbolPosition = new SymbolPosition(bodyPosition.toRectF());

        if (noteSymbol.hasStem()) {
            symbolPosition.calculatePosition(stemRect);
        }

        if (false == crossRects.isEmpty()) {
            symbolPosition.calculatePosition(crossRects.toArray(new RectF[crossRects.size()]));
        }

        symbol.setSymbolPosition(symbolPosition);
    }

    protected List<RectF> drawCross(NoteSymbol noteSymbol, Paint paint) {
        Integer xPositionForCross = null;
        List<RectF> crossRects = new LinkedList<RectF>();

        for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
            if (noteName.isSigned()) {
                if (xPositionForCross == null) {
                    xPositionForCross = getCenterPointForNextSmallSymbol().x;
                }

                int yPositionForCross = noteSheetCanvas.getHalfHeight() + NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName) * distanceBetweenLines / 2;

                crossRects.add(noteCrossDrawer.drawCross(xPositionForCross, yPositionForCross, paint));
            }
        }

        return crossRects;
    }

    protected SymbolPosition drawBody(NoteSymbol noteSymbol, Paint paint) {
        return noteBodyDrawer.drawBody(noteSymbol, paint);
    }

    protected RectF drawStem(NoteSymbol noteSymbol, SymbolPosition symbolPosition, Paint paint) {
        if (noteSymbol.hasStem()) {
            return noteStemDrawer.drawStem(symbolPosition, noteSymbol, key, paint);
        }

        return null;
    }

    protected void drawHelpLines(SymbolPosition symbolPosition, Paint paint) {
        float topEndOfNoteLines = noteSheetCanvas.getHalfHeight() -
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
        float bottomEndOfNoteLines = noteSheetCanvas.getHalfHeight() +
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;

        float topEndOfHelpLines = symbolPosition.getTop() + distanceBetweenLines / 2;
        float bottomEndOfHelpLines = symbolPosition.getBottom() - distanceBetweenLines / 2;

        int lengthOfHelpLine = ((int) symbolPosition.getRight() - (int) symbolPosition.getLeft()) / 3;

        topEndOfNoteLines -= distanceBetweenLines;
        while (topEndOfHelpLines <= topEndOfNoteLines) {
            int startX = (int) (symbolPosition.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolPosition.getRight() + lengthOfHelpLine);
            int startY = (int) topEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            topEndOfNoteLines -= distanceBetweenLines;
        }

        bottomEndOfNoteLines += distanceBetweenLines;
        while (bottomEndOfHelpLines >= bottomEndOfNoteLines) {
            int startX = (int) (symbolPosition.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolPosition.getRight() + lengthOfHelpLine);
            int startY = (int) bottomEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            bottomEndOfNoteLines += distanceBetweenLines;
        }
    }
}
