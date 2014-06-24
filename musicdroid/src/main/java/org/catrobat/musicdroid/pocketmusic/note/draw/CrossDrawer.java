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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.tools.PictureTools;

public final class CrossDrawer {
	private CrossDrawer() {
	}

	public static void drawCross(NoteSheetCanvas noteSheetCanvas, int xPosition, int yPosition, Context context) {
		int crossHeight = 2 * noteSheetCanvas.getDistanceBetweenNoteLines();

		Resources res = context.getResources();
		Bitmap crossPicture = BitmapFactory.decodeResource(res, R.drawable.cross);
		int xStartPositionForCrosses = noteSheetCanvas.getStartXPointForNextSmallSymbolSpace();

		Rect rect = PictureTools.calculateProportionalPictureContourRect(crossPicture, crossHeight,
                xStartPositionForCrosses,
                noteSheetCanvas.getYPositionOfCenterLine() + yPosition * noteSheetCanvas.getDistanceBetweenNoteLines()
                        / 2
        );

		noteSheetCanvas.getCanvas().drawBitmap(crossPicture, null, rect, null);
	}
}
