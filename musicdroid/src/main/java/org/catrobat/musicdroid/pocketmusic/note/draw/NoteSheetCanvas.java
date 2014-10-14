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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @author musicdroid
 */
public abstract class NoteSheetCanvas {

	protected static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;
    protected static final int BOLD_BAR_WIDTH = 5;
    protected static final int THIN_BAR_WIDTH = 2;
    protected static final int NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;
    protected static final int NOTE_SHEET_PADDING = 20;
    protected static final int START_X_POSITION_FOR_DRAWING = NOTE_SHEET_PADDING;

    protected int endXPositionForDrawingElements;
    protected int yPositionOfCenterLine;
    protected int startXPositionForNextElement;
    protected int distanceBetweenLines;
    protected int halfBarHeight;

    protected Paint paint;
    protected Canvas canvas;

    public NoteSheetCanvas(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        this.canvas = canvas;
        this.endXPositionForDrawingElements = canvas.getWidth() - NOTE_SHEET_PADDING;
        this.yPositionOfCenterLine = canvas.getHeight() / 2;
        this.startXPositionForNextElement = START_X_POSITION_FOR_DRAWING;
        this.distanceBetweenLines = getDistanceBetweenLines();
        this.halfBarHeight = distanceBetweenLines * NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS;
    }

    public int getDistanceBetweenLines() {
        int lineHeight = canvas.getHeight() / POSSIBLE_LINE_SPACES_ON_SCREEN;

        if(lineHeight % 2 == 0)
            return lineHeight;

        return lineHeight - 1;
    }

    public int getYPositionOfCenterLine() {
        return yPositionOfCenterLine;
    }

    public void draw() {
        drawLines();
        drawLineEndBars();
        drawSheetElements();
    }

    protected abstract void drawSheetElements();

    private void drawLines() {
        for (int lineDistanceFromCenterLine = -NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine <= NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine++) {
            int actualLinePosition = yPositionOfCenterLine + lineDistanceFromCenterLine * distanceBetweenLines;
            canvas.drawLine(startXPositionForNextElement, actualLinePosition, endXPositionForDrawingElements,
                    actualLinePosition, paint);
        }
    }

    private void drawLineEndBars() {
        paint.setStyle(Paint.Style.FILL);
        drawEndBar();
        drawFrontBars();
    }

    private void drawFrontBars() {
        drawBoldBar(startXPositionForNextElement);
        drawThinBar(startXPositionForNextElement + 2 * BOLD_BAR_WIDTH);
    }

    private void drawEndBar() {
        int leftPositionOfEndBar = endXPositionForDrawingElements - BOLD_BAR_WIDTH;
        drawBoldBar(leftPositionOfEndBar);
    }

    private void drawThinBar(int xBarStartPosition) {
        int xEndThinBar = xBarStartPosition + THIN_BAR_WIDTH;
        Rect boldBar = new Rect(xBarStartPosition, yPositionOfCenterLine - halfBarHeight, xEndThinBar, yPositionOfCenterLine + halfBarHeight);

        canvas.drawRect(boldBar, paint);
        startXPositionForNextElement = xEndThinBar;
    }

    private void drawBoldBar(int xBarStartPosition) {
        Rect boldBar = new Rect(xBarStartPosition, yPositionOfCenterLine - halfBarHeight, xBarStartPosition + BOLD_BAR_WIDTH, yPositionOfCenterLine
                + halfBarHeight);

        canvas.drawRect(boldBar, paint);
    }

    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public void drawOval(RectF rect, Paint paint) {
        canvas.drawOval(rect, paint);
    }

    public void drawBitmap(Bitmap crossPicture, Rect src, Rect dest, Paint paint) {
        canvas.drawBitmap(crossPicture, src, dest, paint);
    }
}
