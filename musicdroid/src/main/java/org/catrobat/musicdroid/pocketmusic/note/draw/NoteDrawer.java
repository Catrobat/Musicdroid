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
import android.graphics.Color;
import android.graphics.Paint;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

public class NoteDrawer {

	private PianoNoteSheetCanvas noteSheetCanvas;
	private Resources resources;
	private MusicalKey key;
    private NotePositionInformation notePositionInformation;
    private NoteSymbol noteSymbol;

	public NoteDrawer(PianoNoteSheetCanvas noteSheetCanvas, MusicalKey key, Resources resources) {
		this.noteSheetCanvas = noteSheetCanvas;
		this.resources = resources;
		this.key = key;
	}

    public void drawNoteSymbol(NoteSymbol symbol) {
        this.noteSymbol = symbol;
        drawCross();
        drawBody();
        //drawDot();
        drawStem();
        //drawFlag();
        drawHelpLines();
    }

    private void drawCross() {
        Integer xPositionForCrosses = null;

        for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
            if (noteName.isSigned()) {
                if (xPositionForCrosses == null) {
                    xPositionForCrosses = noteSheetCanvas.getCenterPointForNextSmallSymbol().x;
                }

                int distanceFromCrossToMiddleLine = NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName);
                int yPositionForCross = noteSheetCanvas.getYPositionOfCenterLine() + distanceFromCrossToMiddleLine * noteSheetCanvas.getDistanceBetweenLines()
                        / 2;

                CrossDrawer.drawCross(noteSheetCanvas, xPositionForCrosses,
                        yPositionForCross, resources);
            }
        }
    }

    private void drawHelpLines() {
        float topEndOfNoteLines = noteSheetCanvas.getYPositionOfCenterLine() -
                noteSheetCanvas.getDistanceBetweenLines() * noteSheetCanvas.NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
        float bottomEndOfNoteLines = noteSheetCanvas.getYPositionOfCenterLine() +
                noteSheetCanvas.getDistanceBetweenLines() * noteSheetCanvas.NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;

        float topEndOfHelpLines = notePositionInformation.getTopOfSymbol() + noteSheetCanvas.getDistanceBetweenLines()/2;
        float bottomEndOfHelpLines = notePositionInformation.getBottomOfSymbol() - noteSheetCanvas.getDistanceBetweenLines()/2;

        int lengthOfHelpLine = ((int) notePositionInformation.getRightSideOfSymbol() - (int) notePositionInformation.getLeftSideOfSymbol()) / 3;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        while(topEndOfHelpLines <= topEndOfNoteLines) {

            int startX = (int) (notePositionInformation.getLeftSideOfSymbol() - lengthOfHelpLine);
            int stopX = (int) (notePositionInformation.getRightSideOfSymbol() + lengthOfHelpLine);
            int startY = (int) topEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            topEndOfNoteLines -= noteSheetCanvas.getDistanceBetweenLines();
        }

        while(bottomEndOfHelpLines >= bottomEndOfNoteLines) {
            int startX = (int) (notePositionInformation.getLeftSideOfSymbol() - lengthOfHelpLine);
            int stopX = (int) (notePositionInformation.getRightSideOfSymbol() + lengthOfHelpLine);
            int startY = (int) bottomEndOfNoteLines;
            int stopY = startY;
            noteSheetCanvas.drawLine(startX, startY, stopX, stopY, paint);

            bottomEndOfNoteLines += noteSheetCanvas.getDistanceBetweenLines();
        }
    }

    private void drawBody() {
        boolean isStemUpdirected = noteSymbol.isStemUp(key);

        this.notePositionInformation = NoteBodyDrawer.drawBody(noteSheetCanvas, noteSymbol, isStemUpdirected, key);
    }

    private void drawStem() {
        boolean isStemUpdirected = noteSymbol.isStemUp(key);

        NoteStemDrawer.drawStem(noteSheetCanvas, NoteLength.QUARTER, this.notePositionInformation,
                isStemUpdirected);
    }

	public void drawNoteSymbol2(NoteSymbol symbol) {
		boolean isStemUpdirected = symbol.isStemUp(key);

		/*
		List<RectF> noteSurroundingRects = NoteBodyDrawer.drawBody(noteSheetCanvas, symbol, isStemUpdirected, key);
		drawHelpLines(noteSheetCanvas, noteSurroundingRects, symbol, key);

		Point startPointOfNoteStem = new Point();
		Point endPointOfNoteStem = new Point();

		if (!isStemUpdirected) {
			startPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(0).bottom + noteSurroundingRects.get(0).top) / 2.0);
			endPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(noteSurroundingRects.size() - 1).bottom + noteSurroundingRects
							.get(noteSurroundingRects.size() - 1).top) / 2.0);
		} else {
			startPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(noteSurroundingRects.size() - 1).bottom + noteSurroundingRects
							.get(noteSurroundingRects.size() - 1).top) / 2.0);
			endPointOfNoteStem.y = (int) Math
					.round((noteSurroundingRects.get(0).bottom + noteSurroundingRects.get(0).top) / 2.0);
		}

		if (!isStemUpdirected) {
			startPointOfNoteStem.x = (int) noteSurroundingRects.get(0).right;
			endPointOfNoteStem.x = (int) noteSurroundingRects.get(0).right;
		} else {

			startPointOfNoteStem.x = (int) noteSurroundingRects.get(0).left;
			endPointOfNoteStem.x = (int) noteSurroundingRects.get(0).left;
		}
	*/
	}
}
