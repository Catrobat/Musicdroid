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

import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.test.note.symbol.BreakSymbolTestDataFactory;

public class SymbolDrawerTest extends AbstractDrawerTest {

    private NoteSheetDrawPosition drawPosition;
    private SymbolDrawerMock symbolDrawer;

    @Override
    protected void setUp() {
        super.setUp();

        drawPosition = new NoteSheetDrawPosition(START_X_POSITION, END_X_POSITION);
        symbolDrawer = new SymbolDrawerMock(noteSheetCanvas, paintDefault, getContext().getResources(), MusicalKey.VIOLIN, drawPosition, distanceBetweenLines);
    }

    public void testGetCenterPointForNextSymbol() {
        Point expectedPoint = new Point(calculateStartX(symbolDrawer.getWidthForOneSymbol()), noteSheetCanvas.getHalfHeight());
        Point actualPoint = symbolDrawer.getCenterPointForNextSymbol();

        assertEquals(expectedPoint, actualPoint);
    }

    public void testGetCenterPointForNextSmallSymbol() {
        Point expectedPoint = new Point(calculateStartX(symbolDrawer.getWidthForOneSmallSymbol()), noteSheetCanvas.getHalfHeight());
        Point actualPoint = symbolDrawer.getCenterPointForNextSmallSymbol();

        assertEquals(expectedPoint, actualPoint);
    }

    private int calculateStartX(int symbolWidth) {
        return drawPosition.getStartXPositionForNextElement() + (symbolWidth / 2);
    }

    public void testDrawSymbolDefault() {
        Symbol symbol = BreakSymbolTestDataFactory.createBreakSymbol(false);

        symbolDrawer.drawSymbol(symbol);

        assertEquals(NoteSheetDrawer.COLOR_DEFAULT, symbolDrawer.getLastUsedPaint().getColor());
    }

    public void testDrawSymbolMarked() {
        Symbol symbol = BreakSymbolTestDataFactory.createBreakSymbol(true);

        symbolDrawer.drawSymbol(symbol);

        assertEquals(NoteSheetDrawer.COLOR_MARKED, symbolDrawer.getLastUsedPaint().getColor());
    }
}
