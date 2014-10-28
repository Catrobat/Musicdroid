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
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

import java.util.LinkedList;
import java.util.List;

public final class NoteBodyDrawer {
	private NoteBodyDrawer() {
	}

	public static NotePositionInformation drawBody(PianoNoteSheetCanvas noteSheetCanvas, NoteSymbol noteSymbol,
			boolean isStemUpdirected, MusicalKey key) {

		int lineHeight =  noteSheetCanvas.getDistanceBetweenLines();
		int noteHeight = lineHeight / 2;
		int noteWidth = noteHeight * 130 / 100;

		Point centerPointOfSpaceForNote = noteSheetCanvas.getCenterPointForNextSymbol();
		List<RectF> noteSurroundingRects = new LinkedList<RectF>();
		NoteName prevNoteName = null;

		for (NoteName noteName : noteSymbol.getNoteNamesSorted()) {
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

			RectF rect = new RectF(left, top, right, bottom);

			noteSurroundingRects.add(rect);
			noteSheetCanvas.drawOval(rect);

			prevNoteName = noteName;
		}

		return new NotePositionInformation(noteSurroundingRects);
	}
}
