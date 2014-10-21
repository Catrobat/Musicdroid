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
	}
}
