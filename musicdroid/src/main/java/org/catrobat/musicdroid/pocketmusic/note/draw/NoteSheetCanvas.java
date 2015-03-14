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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class NoteSheetCanvas {

    protected Canvas canvas;

    public NoteSheetCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public int getHalfHeight() {
        return getHeight() / 2;
    }

    public int getWidth() {
        return canvas.getWidth();
    }

    public int getHeight() {
        return canvas.getHeight();
    }

    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public void drawRect(Rect rect, Paint paint) {
        canvas.drawRect(rect, paint);
    }

    public void drawOval(RectF rect, Paint paint) {
        canvas.drawOval(rect, paint);
    }

    public void drawPath(Path path, Paint paint) {
        canvas.drawPath(path, paint);
    }

    public Rect drawBitmap(Resources resources, int bitmapId, int bitmapHeight, int xPosition, int yPosition, Paint paint) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, bitmapId);

        Rect rect = calculateProportionalRect(bitmap, bitmapHeight, xPosition, yPosition);

        if (paint.getColor() != NoteSheetDrawer.COLOR_DEFAULT) {
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            bitmap = recolorBitmap(bitmap, NoteSheetDrawer.COLOR_DEFAULT, paint.getColor());
        }

        canvas.drawBitmap(bitmap, null, rect, null);

        return rect;
    }

    protected Bitmap recolorBitmap(Bitmap bitmap, int oldColor, int newColor) {
        int areaSize = bitmap.getHeight() * bitmap.getWidth();
        int[] pixelArray = new int[areaSize];

        bitmap.getPixels(pixelArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int i = 0; i < areaSize; i++) {
            if (pixelArray[i] == oldColor)
                pixelArray[i] = newColor;
        }

        bitmap.setPixels(pixelArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        return bitmap;
    }

    protected Rect calculateProportionalRect(Bitmap originalPicture, int height, int startXPosition, int yCenterPosition) {
        int keyPictureWidth = originalPicture.getWidth() * height / originalPicture.getHeight();

        Point leftUpperOfRect = new Point(startXPosition, yCenterPosition - height / 2);
        Point rightBottomOfRect = new Point(startXPosition + keyPictureWidth, yCenterPosition + height / 2);

        return new Rect(leftUpperOfRect.x, leftUpperOfRect.y, rightBottomOfRect.x, rightBottomOfRect.y);
    }
}
