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

import android.graphics.Canvas;
import android.graphics.Point;

/**
 * @author musicdroid
 */
public class NoteSheetCanvas {

	private static final int POSSIBLE_LINE_SPACES_ON_SCREEN = 12;
	private int startXPositionNotes;
	private int endXPositionNotes;
	private int startXPointForNextSymbolSpace;
	private int widthForOneSymbol;
	private int numberOfSymbolsOnScreen;
	private Canvas canvas;

	public NoteSheetCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public int getHeightOfAllNoteLines() {
		return getDistanceBetweenNoteLines() * 4;
	}

	public int getWidthForSmallSymbol() {
		return widthForOneSymbol / 4;
	}

	public int getWidthForOneSymbol() {
		return widthForOneSymbol;
	}

	public void setWidthForOneSymbol(int widthForOneSymbol) {
		this.widthForOneSymbol = widthForOneSymbol;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public int getDistanceBetweenNoteLines() {
		int lineHeight = canvas.getHeight() / POSSIBLE_LINE_SPACES_ON_SCREEN;

		if (lineHeight % 2 == 0) {
			return lineHeight;
		}

		return lineHeight - 1;
	}

	public int getYPositionOfCenterLine() {
		return canvas.getHeight() / 2;
	}

	public int getStartXPositionNotes() {
		return startXPositionNotes;
	}

	public void setStartXPositionNotes(int startXPositionNotes) {
		this.startXPositionNotes = startXPositionNotes;
		this.startXPointForNextSymbolSpace = this.startXPositionNotes;
		calculateWidthForOneSymbolAndSetNumberOfSymbolsOnScreen();
	}

	public void setEndXPositionNotes(int endXPositionNotes) {
		this.endXPositionNotes = endXPositionNotes;
	}

	private void calculateWidthForOneSymbolAndSetNumberOfSymbolsOnScreen() {
		int possibleWidthForAllSymbol = endXPositionNotes - startXPositionNotes;
		this.widthForOneSymbol = 3 * getDistanceBetweenNoteLines();
		numberOfSymbolsOnScreen = (int) Math.floor(possibleWidthForAllSymbol / widthForOneSymbol);
	}

	public Point getCenterPointForNextSymbol() {
		Point centerPoint = new Point();
		int x = getStartXPointForNextSymbolSpace() + getWidthForOneSymbol() / 2;

		int y = getYPositionOfCenterLine();
		centerPoint.set(x, y);
		return centerPoint;
	}

	public Point getCenterPointForNextSmallSymbol() {
		Point centerPoint = new Point();
		int x = getStartXPointForNextSmallSymbolSpace() + getWidthForSmallSymbol() / 2;

		int y = getYPositionOfCenterLine();
		centerPoint.set(x, y);
		return centerPoint;
	}

	public int getStartXPointForNextSymbolSpace() {
		int startXPointForNextSymbolSpace = this.startXPointForNextSymbolSpace;
		this.startXPointForNextSymbolSpace += widthForOneSymbol;
		return startXPointForNextSymbolSpace;
	}

	public int getStartXPointForNextSmallSymbolSpace() {
		int startXPointForNextSymbolSpace = this.startXPointForNextSymbolSpace;
		this.startXPointForNextSymbolSpace += this.getWidthForSmallSymbol();
		return startXPointForNextSymbolSpace;
	}

}
