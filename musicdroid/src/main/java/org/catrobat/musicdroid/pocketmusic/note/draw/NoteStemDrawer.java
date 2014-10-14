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

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.note.NoteLength;

public final class NoteStemDrawer {
	private static final double LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES = 2.5;

	private NoteStemDrawer() {
	}

	public static void drawStem(NoteSheetCanvas noteSheetCanvas, NoteLength noteLength,
                      NotePositionInformation notePositionInformation, boolean isUpdirectedStem) {

		int stemLength = (int) (Math.round(LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES
				* noteSheetCanvas.getDistanceBetweenLines()));

        int halfHeightOfNote = noteSheetCanvas.getDistanceBetweenLines() /2;

		Point startPointOfNoteStem = new Point();
        Point endPointOfNoteStem = new Point();

        if(isUpdirectedStem) {
            startPointOfNoteStem.x = (int) notePositionInformation.getRightSideOfSymbol();
            startPointOfNoteStem.y = (int) notePositionInformation.getBottomOfSymbol() - halfHeightOfNote;
            endPointOfNoteStem.x = startPointOfNoteStem.x;
            endPointOfNoteStem.y = (int) notePositionInformation.getTopOfSymbol() - stemLength;
        } else {
            startPointOfNoteStem.x = (int) notePositionInformation.getLeftSideOfSymbol();
            startPointOfNoteStem.y = (int) notePositionInformation.getTopOfSymbol() + halfHeightOfNote;
            endPointOfNoteStem.x = startPointOfNoteStem.x;
            endPointOfNoteStem.y = (int) notePositionInformation.getBottomOfSymbol() + stemLength;
        }

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(4);

		noteSheetCanvas.drawLine(startPointOfNoteStem.x, startPointOfNoteStem.y, endPointOfNoteStem.x,
                endPointOfNoteStem.y, paint);

		if (noteLength.hasFlag()) {
			drawCurvyStem(noteSheetCanvas, isUpdirectedStem, endPointOfNoteStem, paint, noteLength);
		}
	}

	private static void drawCurvyStem(NoteSheetCanvas noteSheetCanvas, boolean isUpDirectedStem,
			Point endPointOfNoteStem, Paint paint, NoteLength noteLength) {
		//		Path bezierPath = new Path();
		paint.setStyle(Paint.Style.STROKE);
		//		int xPosition = endPointOfNoteStem.x;
		//		int yPosition = endPointOfNoteStem.y;
		int lineDistance = noteSheetCanvas.getDistanceBetweenLines();

		if (isUpDirectedStem) {
			drawBezierPathUpDirectedStem(noteSheetCanvas, endPointOfNoteStem, paint);
			endPointOfNoteStem.y += lineDistance;
			if (NoteLength.SIXTEENTH == noteLength) {
				drawBezierPathUpDirectedStem(noteSheetCanvas, endPointOfNoteStem, paint);
			}
		} else {
			drawBezierPathDownDirectedStem(noteSheetCanvas, endPointOfNoteStem, paint);
			endPointOfNoteStem.y -= lineDistance;
			if (NoteLength.SIXTEENTH == noteLength) {
				drawBezierPathDownDirectedStem(noteSheetCanvas, endPointOfNoteStem, paint);
			}
		}
	}

	private static void drawBezierPathUpDirectedStem(NoteSheetCanvas noteSheetCanvas, Point endPointOfNoteStem,
			Paint paint) {
		/*int xPosition = endPointOfNoteStem.x;
		int yPosition = endPointOfNoteStem.y;

		int lineDistance = noteSheetCanvas.getDistanceBetweenNoteLines();

		Path bezierPath = new Path();
		bezierPath.moveTo(xPosition, yPosition);
		bezierPath.cubicTo(xPosition, yPosition + 3 * lineDistance / 2, xPosition + lineDistance * 2, yPosition
				+ lineDistance * 2, xPosition + lineDistance / 2, yPosition + lineDistance * 3);
		noteSheetCanvas.getCanvas().drawPath(bezierPath, paint);*/

	}

	private static void drawBezierPathDownDirectedStem(NoteSheetCanvas noteSheetCanvas, Point endPointOfNoteStem,
			Paint paint) {
		/*int xPosition = endPointOfNoteStem.x;
		int yPosition = endPointOfNoteStem.y;

		int lineDistance = noteSheetCanvas.getDistanceBetweenNoteLines();

		Path bezierPath = new Path();
		bezierPath.moveTo(xPosition, yPosition);
		bezierPath.cubicTo(xPosition, yPosition - lineDistance, xPosition + lineDistance * 2, yPosition - lineDistance
				* 2, xPosition + lineDistance, yPosition - lineDistance * 2 - lineDistance / 2);
		noteSheetCanvas.getCanvas().drawPath(bezierPath, paint);
*/
	}
}
