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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @author musicdroid
 */
public abstract class NoteSheetCanvas {

    public static final int NOTE_SHEET_PADDING = 20;
    public static final int NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;
    public static final int START_X_POSITION_FOR_DRAWING = NOTE_SHEET_PADDING;
    public static final int BOLD_BAR_WIDTH = 5;
    public static final int THIN_BAR_WIDTH = 2;

	protected static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;

    private Paint paint;
    private Canvas canvas;

    protected int endXPositionForDrawingElements;
    protected int startXPositionForNextElement;

    protected int distanceBetweenLines;

    protected int yPositionOfCenterLine;
    protected int yPositionOfBarTop;
    protected int yPositionOfBarBottom;

    public NoteSheetCanvas(Canvas canvas) {
        this.paint = createPaint();
        this.canvas = canvas;

        this.endXPositionForDrawingElements = canvas.getWidth() - NOTE_SHEET_PADDING;
        this.startXPositionForNextElement = START_X_POSITION_FOR_DRAWING;

        this.distanceBetweenLines = calculateDistanceBetweenLines();

        this.yPositionOfCenterLine = canvas.getHeight() / 2;
        this.yPositionOfBarTop = yPositionOfCenterLine - NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
        this.yPositionOfBarBottom = yPositionOfCenterLine + NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
    }

    private Paint createPaint() {
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);

        return paint;
    }

    private int calculateDistanceBetweenLines() {
        int lineHeight = canvas.getHeight() / POSSIBLE_LINE_SPACES_ON_SCREEN;

        if(lineHeight % 2 == 0)
            return lineHeight;

        return lineHeight - 1;
    }

    public int getWidthForDrawingTrack() {
        return startXPositionForNextElement;
    }

    public int getDistanceBetweenLines() {
        return distanceBetweenLines;
    }

    public int getYPositionOfCenterLine() {
        return yPositionOfCenterLine;
    }

    public void draw() {
        drawLines();
        drawBars();
        drawSheetElements();
    }

    protected abstract void drawSheetElements();

    private void drawLines() {
        for (int startYPositionOfLine = yPositionOfBarTop; startYPositionOfLine <= yPositionOfBarBottom; startYPositionOfLine += distanceBetweenLines) {
            canvas.drawLine(startXPositionForNextElement, startYPositionOfLine, endXPositionForDrawingElements, startYPositionOfLine, paint);
        }
    }

    private void drawBars() {
        drawBar(startXPositionForNextElement, BOLD_BAR_WIDTH);
        startXPositionForNextElement += 2 * BOLD_BAR_WIDTH;

        drawBar(startXPositionForNextElement, THIN_BAR_WIDTH);
        startXPositionForNextElement += BOLD_BAR_WIDTH;

        drawBar(endXPositionForDrawingElements - BOLD_BAR_WIDTH, BOLD_BAR_WIDTH);
    }

    private void drawBar(int startXPositionBar, int barWidth) {
        int endXPositionBar = startXPositionBar + barWidth;
        Rect boldBar = new Rect(startXPositionBar, yPositionOfBarTop, endXPositionBar, yPositionOfBarBottom);

        canvas.drawRect(boldBar, paint);
    }

    public void drawLine(float startX, float startY, float stopX, float stopY) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public void drawOval(RectF rect) {
        canvas.drawOval(rect, paint);
    }

    public Rect drawBitmap(Resources resources, int bitmapId, int bitmapHeight, int xPosition, int yPosition) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, bitmapId);

        Rect rect = PictureTools.calculateProportionalPictureContourRect(bitmap, bitmapHeight,
                xPosition, yPosition);

        canvas.drawBitmap(bitmap, null, rect, null);

        return rect;
    }
}
