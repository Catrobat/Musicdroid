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

public class NoteStemDrawer {

	private static final double LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES = 2.5;

    private NoteSheetCanvas canvas;
    private Paint paint;
    private int distanceBetweenLines;

	public NoteStemDrawer(NoteSheetCanvas canvas, Paint paint, int distanceBetweenLines) {
        this.canvas = canvas;
        this.paint = paint;
        this.distanceBetweenLines = distanceBetweenLines;
	}

	public void drawStem(NotePositionInformation notePositionInformation, boolean isUpdirectedStem) {

		int stemLength = (int) (Math.round(LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES * distanceBetweenLines));

        int distanceBetweenLinesHalf = distanceBetweenLines / 2;

		Point startPointOfNoteStem = new Point();
        Point endPointOfNoteStem = new Point();

        if(isUpdirectedStem) {
            startPointOfNoteStem.x = (int) notePositionInformation.getRightSideOfSymbol();
            startPointOfNoteStem.y = (int) notePositionInformation.getBottomOfSymbol() - distanceBetweenLinesHalf;
            endPointOfNoteStem.x = startPointOfNoteStem.x;
            endPointOfNoteStem.y = (int) notePositionInformation.getTopOfSymbol() - stemLength;
        } else {
            startPointOfNoteStem.x = (int) notePositionInformation.getLeftSideOfSymbol();
            startPointOfNoteStem.y = (int) notePositionInformation.getTopOfSymbol() + distanceBetweenLinesHalf;
            endPointOfNoteStem.x = startPointOfNoteStem.x;
            endPointOfNoteStem.y = (int) notePositionInformation.getBottomOfSymbol() + stemLength;
        }

		canvas.drawLine(startPointOfNoteStem.x, startPointOfNoteStem.y, endPointOfNoteStem.x,
                endPointOfNoteStem.y, paint);
	}
}
