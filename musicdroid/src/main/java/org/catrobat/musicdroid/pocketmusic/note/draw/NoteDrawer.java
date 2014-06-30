/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2014 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.note.draw;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

import java.util.List;

public class NoteDrawer {

	private NoteSheetCanvas noteSheetCanvas;
	private Context context;
	private MusicalKey key;

	public NoteDrawer(NoteSheetCanvas noteSheetCanvas, MusicalKey key, Context context) {
		this.noteSheetCanvas = noteSheetCanvas;
		this.context = context;
		this.key = key;
	}

	public void drawNoteSymbol(NoteSymbol symbol) {
		boolean isStemUpdirected = symbol.isStemUp(key);

		Integer xPositionForCrosses = null;

		for (NoteName noteName : symbol.getNoteNamesSorted()) {
			if (noteName.isSigned()) {
				if (xPositionForCrosses == null) {
					xPositionForCrosses = noteSheetCanvas.getStartXPointForNextSmallSymbolSpace();
				}
				CrossDrawer.drawCross(noteSheetCanvas, xPositionForCrosses,
						NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteName), context);
			}
		}

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
		NoteStemDrawer.drawStem(noteSheetCanvas, NoteLength.SIXTEENTH, startPointOfNoteStem, endPointOfNoteStem,
				!isStemUpdirected);
	}

	private void drawHelpLines(NoteSheetCanvas noteSheetCanvas, List<RectF> noteSurroundingRects, NoteSymbol symbol,
			MusicalKey key) {

		int numberOfHalfLineDistancesWithoutHelpLines = 5;
		List<NoteName> noteNames = symbol.getNoteNamesSorted();
		for (int noteIndex = 0; noteIndex < noteNames.size(); noteIndex++) {
			int absDistance = Math.abs(NoteName.calculateDistanceToMiddleLineCountingSignedNotesOnly(key,
					noteNames.get(noteIndex)));
			if (absDistance > numberOfHalfLineDistancesWithoutHelpLines) {

				for (int halfTones = 5; halfTones <= absDistance; halfTones++) {
					if (halfTones % 2 == 0) {
						Paint paint = new Paint();
						paint.setColor(Color.BLACK);
						paint.setStyle(Style.STROKE);
						paint.setStrokeWidth(4);
						int startX = (int) (noteSurroundingRects.get(noteIndex).left - (noteSurroundingRects.get(
								noteIndex).width() / 3));
						int stopX = (int) (noteSurroundingRects.get(noteIndex).right + (noteSurroundingRects.get(
								noteIndex).width() / 3));
						int distanceFromCenterLineToLinePosition = halfTones
								* noteSheetCanvas.getDistanceBetweenNoteLines() / 2;
						if (NoteName
								.calculateDistanceToMiddleLineCountingSignedNotesOnly(key, noteNames.get(noteIndex)) < 0) {
							distanceFromCenterLineToLinePosition *= (-1);
						}
						int startY = noteSheetCanvas.getYPositionOfCenterLine() + distanceFromCenterLineToLinePosition;
						int stopY = startY;
						noteSheetCanvas.getCanvas().drawLine(startX, startY, stopX, stopY, paint);
					}
				}
			}
		}
	}
}
