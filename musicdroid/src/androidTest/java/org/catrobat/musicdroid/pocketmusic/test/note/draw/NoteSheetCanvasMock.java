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

package org.catrobat.musicdroid.pocketmusic.test.note.draw;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Rect;

import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;

public class NoteSheetCanvasMock extends NoteSheetCanvas {

    public NoteSheetCanvasMock(CanvasMock canvas) {
        super(canvas);
    }

    @Override
    public Rect calculateProportionalRect(Bitmap originalPicture, int height, int startXPosition, int yCenterPosition) {
        return super.calculateProportionalRect(originalPicture, height, startXPosition, yCenterPosition);
    }

    @Override
    public Rect drawBitmap(Resources resources, int bitmapId, int bitmapHeight, int xPosition, int yPosition, Paint paint) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, bitmapId);

        Rect rect = calculateProportionalRect(bitmap, bitmapHeight, xPosition, yPosition);

        canvas.drawBitmap(bitmap, null, rect, paint);

        return rect;
    }

    @Override
    public Bitmap recolorBitmap(Bitmap bitmap, int oldColor, int newColor) {
        return super.recolorBitmap(bitmap, oldColor, newColor);
    }
}
