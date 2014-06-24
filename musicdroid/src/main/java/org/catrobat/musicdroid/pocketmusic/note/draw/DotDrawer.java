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

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;

public final class DotDrawer {
	private DotDrawer() {
	}

	public static void drawDotOnRightSideOfRect(RectF rect, NoteSheetCanvas noteSheetCanvas) {

		int dotRadius = 5;
		int pixelDistanceBetweenRectAndDot = 10;

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);

		RectF dotRect = new RectF();
		float x = rect.right + pixelDistanceBetweenRectAndDot;
		float y = (rect.top + rect.bottom) / 2;
		PointF centerPointForDot = new PointF(x, y);
		dotRect.left = centerPointForDot.x;
		dotRect.right = centerPointForDot.x + 2 * dotRadius;
		dotRect.bottom = centerPointForDot.y + dotRadius;
		dotRect.top = centerPointForDot.y - dotRadius;

		noteSheetCanvas.getCanvas().drawOval(dotRect, paint);

	}
}
