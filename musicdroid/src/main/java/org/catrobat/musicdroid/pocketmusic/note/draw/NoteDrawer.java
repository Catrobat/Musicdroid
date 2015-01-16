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
    protected SymbolCoordinates drawSymbol(Symbol symbol, Paint paint) {
        if (false == (symbol instanceof  NoteSymbol)) {
            throw new IllegalArgumentException("Symbol is not of type NoteSymbol: " + symbol);
        }

        NoteSymbol noteSymbol = (NoteSymbol) symbol;
        drawCross(noteSymbol);
        SymbolCoordinates bodyCoordinates = drawBody(noteSymbol, paint);
        RectF stemRect = drawStem(noteSymbol, bodyCoordinates, paint);
        drawHelpLines(bodyCoordinates, paint);

        if (null == stemRect) {
            return new SymbolCoordinates(bodyCoordinates.toRectF());
        } else {
            return new SymbolCoordinates(bodyCoordinates.toRectF(), stemRect);
        }
    }

    protected void drawCross(NoteSymbol noteSymbol) {
        Integer xPositionForCross = null;

        for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
            if (noteName.isSigned()) {
                if (xPositionForCross == null) {
                    xPositionForCross = getCenterPointForNextSmallSymbol().x;
                }

                int yPositionForCross = noteSheetCanvas.getHeightHalf() + NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName) * distanceBetweenLines / 2;

                noteCrossDrawer.drawCross(xPositionForCross, yPositionForCross);
            }
        }
    }

    protected SymbolCoordinates drawBody(NoteSymbol noteSymbol, Paint paint) {
        return noteBodyDrawer.drawBody(noteSymbol, paint);
    }

    protected RectF drawStem(NoteSymbol noteSymbol, SymbolCoordinates symbolCoordinates, Paint paint) {
        if (noteSymbol.hasStem()) {
            return noteStemDrawer.drawStem(symbolCoordinates, noteSymbol, key, paint);
        }

        return null;
    }

    protected void drawHelpLines(SymbolCoordinates symbolCoordinates, Paint paint) {
        float topEndOfNoteLines = noteSheetCanvas.getHeightHalf() -
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
        float bottomEndOfNoteLines = noteSheetCanvas.getHeightHalf() +
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;

        float topEndOfHelpLines = symbolCoordinates.getTop() + distanceBetweenLines / 2;
        float bottomEndOfHelpLines = symbolCoordinates.getBottom() - distanceBetweenLines / 2;

        int lengthOfHelpLine = ((int) symbolCoordinates.getRight() - (int) symbolCoordinates.getLeft()) / 3;

        topEndOfNoteLines -= distanceBetweenLines;
        while(topEndOfHelpLines <= topEndOfNoteLines) {
            int startX = (int) (symbolCoordinates.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolCoordinates.getRight() + lengthOfHelpLine);
            int startY = (int) topEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            topEndOfNoteLines -= distanceBetweenLines;
        }

        bottomEndOfNoteLines += distanceBetweenLines;
        while(bottomEndOfHelpLines >= bottomEndOfNoteLines) {
            int startX = (int) (symbolCoordinates.getLeft() - lengthOfHelpLine);
            int stopX = (int) (symbolCoordinates.getRight() + lengthOfHelpLine);
            int startY = (int) bottomEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            bottomEndOfNoteLines += distanceBetweenLines;
        }
    }
}
