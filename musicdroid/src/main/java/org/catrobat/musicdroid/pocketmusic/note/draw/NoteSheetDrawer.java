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
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;

public class NoteSheetDrawer {

    public static final int COLOR_DEFAULT = Color.BLACK;
    public static final int COLOR_MARKED = Color.RED;

    public static final int NOTE_SHEET_PADDING = 20;

    public static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;
    public static final int NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;

    public static final int BOLD_BAR_WIDTH = 5;

    public static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;

    private NoteSheetCanvas noteSheetCanvas;
    private Resources resources;
    private SymbolContainer symbolContainer;

    private Paint paint;
    private NoteSheetDrawPosition drawPosition;
    private int startPositionForSymbols;
    protected int distanceBetweenLines;
    protected int yPositionOfBarTop;
    protected int yPositionOfBarBottom;

    private SymbolsDrawer symbolsDrawer;

    public NoteSheetDrawer(NoteSheetCanvas noteSheetCanvas, Resources resources, SymbolContainer symbolContainer) {
        this.noteSheetCanvas = noteSheetCanvas;
        this.resources = resources;
        this.symbolContainer = symbolContainer;

        paint = createPaint();
        drawPosition = new NoteSheetDrawPosition(NOTE_SHEET_PADDING, noteSheetCanvas.getWidth() - NOTE_SHEET_PADDING);
        startPositionForSymbols = 0;
        distanceBetweenLines = calculateDistanceBetweenLines();
        yPositionOfBarTop = noteSheetCanvas.getHalfHeight() - NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
        yPositionOfBarBottom = noteSheetCanvas.getHalfHeight() + NUMBER_OF_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;

        symbolsDrawer = new SymbolsDrawer(noteSheetCanvas, paint, resources, symbolContainer, drawPosition, distanceBetweenLines);
    }

    private Paint createPaint() {
        Paint paint = new Paint();

        paint.setColor(COLOR_DEFAULT);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);

        return paint;
    }

    private int calculateDistanceBetweenLines() {
        int lineHeight = noteSheetCanvas.getHeight() / POSSIBLE_LINE_SPACES_ON_SCREEN;

        if (lineHeight % 2 == 0)
            return lineHeight;

        return lineHeight - 1;
    }

    public int getWidthForDrawingTrack() {
        return drawPosition.getStartXPositionForNextElement();
    }

    public int getWidthForOneSymbol() {
        return symbolsDrawer.getWidthForOneSymbol();
    }

    public int getStartPositionForSymbols() {
        return startPositionForSymbols;
    }

    public void drawNoteSheet() {
        drawLines();
        drawBars();
        drawKey();
        drawSymbols();
    }

    protected void drawLines() {
        for (int startYPositionOfLine = yPositionOfBarTop; startYPositionOfLine <= yPositionOfBarBottom; startYPositionOfLine += distanceBetweenLines) {
            noteSheetCanvas.drawLine(drawPosition.getStartXPositionForNextElement(), startYPositionOfLine, drawPosition.getEndXPositionForDrawingElements(), startYPositionOfLine, paint);
        }
    }

    protected void drawBars() {
        drawBar(drawPosition.getStartXPositionForNextElement());
        drawPosition.increasesStartXPositionForNextElement(2 * BOLD_BAR_WIDTH);

        drawBar(drawPosition.getEndXPositionForDrawingElements() - BOLD_BAR_WIDTH);
    }

    private void drawBar(int startXPositionBar) {
        int endXPositionBar = startXPositionBar + BOLD_BAR_WIDTH;
        Rect boldBar = new Rect(startXPositionBar, yPositionOfBarTop, endXPositionBar, yPositionOfBarBottom);

        noteSheetCanvas.drawRect(boldBar, paint);
    }

    protected void drawKey() {
        if (symbolContainer.getKey() != MusicalKey.VIOLIN) {
            throw new UnsupportedOperationException();
        }

        int keyPictureHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

        Rect keyRect = noteSheetCanvas.drawBitmap(resources, R.drawable.violine, keyPictureHeight, drawPosition.getStartXPositionForNextElement(), noteSheetCanvas.getHalfHeight(), paint);
        startPositionForSymbols = keyRect.right;
        drawPosition.setStartXPositionForNextElement(startPositionForSymbols);
    }

    private void drawSymbols() {
        symbolsDrawer.drawSymbols();
    }
}
