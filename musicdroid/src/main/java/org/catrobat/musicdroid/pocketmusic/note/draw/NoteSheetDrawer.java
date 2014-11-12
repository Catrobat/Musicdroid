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

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class NoteSheetDrawer {

    public static final int NOTE_SHEET_PADDING = 20;

    public static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;
    public static final int NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;

    public static final int BOLD_BAR_WIDTH = 5;
    public static final int THIN_BAR_WIDTH = 2;

    public static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;
    public static final int HEIGHT_OF_TACT_UNIT_IN_LINE_SPACES = 4;

    private NoteSheetCanvas canvas;
    private Resources resources;
    private Track track;

    private Paint paint;
    private NoteSheetDrawPosition drawPosition;
    private int distanceBetweenLines;
    private int yPositionOfBarTop;
    private int yPositionOfBarBottom;

    public NoteSheetDrawer(NoteSheetCanvas canvas, Resources resources, Track track) {
        this.canvas = canvas;
        this.resources = resources;
        this.track = track;

        paint = createPaint();
        drawPosition = new NoteSheetDrawPosition(NOTE_SHEET_PADDING, canvas.getWidth() - NOTE_SHEET_PADDING);
        distanceBetweenLines = calculateDistanceBetweenLines();
        yPositionOfBarTop = canvas.getHeightHalf() - NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
        yPositionOfBarBottom = canvas.getHeightHalf() + NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
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
        return drawPosition.getStartXPositionForNextElement();
    }

    public void drawNoteSheet() {
        drawLines();
        drawBars();
        drawKey();
        drawTactUnit();
        drawTrack();
    }

    private void drawLines() {
        for (int startYPositionOfLine = yPositionOfBarTop; startYPositionOfLine <= yPositionOfBarBottom; startYPositionOfLine += distanceBetweenLines) {
            canvas.drawLine(drawPosition.getStartXPositionForNextElement(), startYPositionOfLine, drawPosition.getEndXPositionForDrawingElements(), startYPositionOfLine, paint);
        }
    }

    private void drawBars() {
        drawBar(drawPosition.getStartXPositionForNextElement(), BOLD_BAR_WIDTH);
        drawPosition.increasesStartXPositionForNextElement(2 * BOLD_BAR_WIDTH);

        drawBar(drawPosition.getStartXPositionForNextElement(), THIN_BAR_WIDTH);
        drawPosition.increasesStartXPositionForNextElement(BOLD_BAR_WIDTH);

        drawBar(drawPosition.getEndXPositionForDrawingElements() - BOLD_BAR_WIDTH, BOLD_BAR_WIDTH);
    }

    private void drawBar(int startXPositionBar, int barWidth) {
        int endXPositionBar = startXPositionBar + barWidth;
        Rect boldBar = new Rect(startXPositionBar, yPositionOfBarTop, endXPositionBar, yPositionOfBarBottom);

        canvas.drawRect(boldBar, paint);
    }

    private void drawKey() {
        if (track.getKey() != MusicalKey.VIOLIN) {
            throw new UnsupportedOperationException();
        }

        int keyPictureHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

        drawPosition.setStartXPositionForNextElement(canvas.drawBitmap(resources, R.drawable.violine, keyPictureHeight, drawPosition.getStartXPositionForNextElement(), canvas.getHeightHalf()).right);
    }

    private void drawTactUnit() {
        int tactPictureHeight = distanceBetweenLines * HEIGHT_OF_TACT_UNIT_IN_LINE_SPACES;

        drawPosition.setStartXPositionForNextElement(canvas.drawBitmap(resources, R.drawable.tact_3_4, tactPictureHeight, drawPosition.getStartXPositionForNextElement(), canvas.getHeightHalf()).right);
    }

    private void drawTrack() {
        TrackDrawer trackDrawer = new TrackDrawer(canvas, paint, resources, track, drawPosition, distanceBetweenLines);
        trackDrawer.drawTrack();
    }
}
