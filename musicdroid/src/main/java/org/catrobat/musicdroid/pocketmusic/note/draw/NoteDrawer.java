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

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;

public class NoteDrawer extends SymbolDrawer {

    private NoteCrossDrawer noteCrossDrawer;
    private NoteStemDrawer noteStemDrawer;
    private NoteBodyDrawer noteBodyDrawer;

    private NotePositionInformation notePositionInformation;
    private NoteSymbol noteSymbol;

	public NoteDrawer(NoteSheetCanvas noteSheetCanvas, Paint paint, Resources resources, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        super(noteSheetCanvas, paint, resources, key, drawPosition, distanceBetweenLines);

        noteCrossDrawer = new NoteCrossDrawer(noteSheetCanvas, resources, distanceBetweenLines);
        noteStemDrawer = new NoteStemDrawer(noteSheetCanvas, paint, distanceBetweenLines);
        noteBodyDrawer = new NoteBodyDrawer(this, noteSheetCanvas, paint, key, distanceBetweenLines);
	}

    public void drawSymbol(Symbol symbol) {
        if (false == (symbol instanceof  NoteSymbol)) {
            return;
        }

        this.noteSymbol = (NoteSymbol) symbol;
        drawCross();
        drawBody();
        drawStem();
        drawHelpLines();
    }

    private void drawCross() {
        Integer xPositionForCrosses = null;

        for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
            if (noteName.isSigned()) {
                if (xPositionForCrosses == null) {
                    xPositionForCrosses = getCenterPointForNextSmallSymbol().x;
                }

                int distanceFromCrossToMiddleLine = NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName);
                int yPositionForCross = noteSheetCanvas.getHeightHalf() + distanceFromCrossToMiddleLine * distanceBetweenLines / 2;

                noteCrossDrawer.drawCross(xPositionForCrosses, yPositionForCross);
            }
        }
    }

    private void drawHelpLines() {
        float topEndOfNoteLines = noteSheetCanvas.getHeightHalf() -
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
        float bottomEndOfNoteLines = noteSheetCanvas.getHeightHalf() +
                distanceBetweenLines * NoteSheetDrawer.NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;

        float topEndOfHelpLines = notePositionInformation.getTopOfSymbol() + distanceBetweenLines / 2;
        float bottomEndOfHelpLines = notePositionInformation.getBottomOfSymbol() - distanceBetweenLines / 2;

        int lengthOfHelpLine = ((int) notePositionInformation.getRightSideOfSymbol() - (int) notePositionInformation.getLeftSideOfSymbol()) / 3;

        topEndOfNoteLines -= distanceBetweenLines;
        while(topEndOfHelpLines <= topEndOfNoteLines) {
            int startX = (int) (notePositionInformation.getLeftSideOfSymbol() - lengthOfHelpLine);
            int stopX = (int) (notePositionInformation.getRightSideOfSymbol() + lengthOfHelpLine);
            int startY = (int) topEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            topEndOfNoteLines -= distanceBetweenLines;
        }

        bottomEndOfNoteLines += distanceBetweenLines;
        while(bottomEndOfHelpLines >= bottomEndOfNoteLines) {
            int startX = (int) (notePositionInformation.getLeftSideOfSymbol() - lengthOfHelpLine);
            int stopX = (int) (notePositionInformation.getRightSideOfSymbol() + lengthOfHelpLine);
            int startY = (int) bottomEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            bottomEndOfNoteLines += distanceBetweenLines;
        }
    }

    private void drawBody() {
        this.notePositionInformation = noteBodyDrawer.drawBody(noteSymbol, noteSymbol.isStemUp(key));
    }

    private void drawStem() {
        noteStemDrawer.drawStem(notePositionInformation, noteSymbol.isStemUp(key));
    }
}
