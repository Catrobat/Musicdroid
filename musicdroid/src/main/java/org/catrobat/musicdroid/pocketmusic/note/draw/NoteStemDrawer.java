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
import android.graphics.PointF;

public class NoteStemDrawer {

	public static final double LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES = 2.5;

    private NoteSheetCanvas noteSheetCanvas;
    private Paint paint;
    private int distanceBetweenLinesHalf;
    private int stemLength;

	public NoteStemDrawer(NoteSheetCanvas noteSheetCanvas, Paint paint, int distanceBetweenLines) {
        this.noteSheetCanvas = noteSheetCanvas;
        this.paint = paint;
        this.distanceBetweenLinesHalf = distanceBetweenLines / 2;
        this.stemLength = (int) (Math.round(LENGTH_OF_STEM_IN_NOTE_LINE_DISTANCES * distanceBetweenLines));
	}

	public void drawStem(NotePositionInformation notePositionInformation, boolean isUpdirectedStem) {
		PointF startPointOfNoteStem = new PointF();
        PointF endPointOfNoteStem = new PointF();

        if(isUpdirectedStem) {
            startPointOfNoteStem.x = notePositionInformation.getRightSideOfSymbol();
            startPointOfNoteStem.y = notePositionInformation.getBottomOfSymbol() - distanceBetweenLinesHalf;
            endPointOfNoteStem.y = notePositionInformation.getTopOfSymbol() - stemLength;
        } else {
            startPointOfNoteStem.x = notePositionInformation.getLeftSideOfSymbol();
            startPointOfNoteStem.y = notePositionInformation.getTopOfSymbol() + distanceBetweenLinesHalf;
            endPointOfNoteStem.y = notePositionInformation.getBottomOfSymbol() + stemLength;
        }

        endPointOfNoteStem.x = startPointOfNoteStem.x;

		noteSheetCanvas.drawLine(startPointOfNoteStem.x, startPointOfNoteStem.y, endPointOfNoteStem.x,
                endPointOfNoteStem.y, paint);
	}
}
