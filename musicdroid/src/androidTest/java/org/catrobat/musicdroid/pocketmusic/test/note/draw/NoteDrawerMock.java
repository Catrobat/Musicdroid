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
import android.graphics.Paint;
import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteDrawer;
import org.catrobat.musicdroid.pocketmusic.note.draw.NotePositionInformation;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetCanvas;
import org.catrobat.musicdroid.pocketmusic.note.draw.NoteSheetDrawPosition;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

public class NoteDrawerMock extends NoteDrawer {

    public NoteDrawerMock(NoteSheetCanvas noteSheetCanvas, Paint paint, Resources resources, MusicalKey key, NoteSheetDrawPosition drawPosition, int distanceBetweenLines) {
        super(noteSheetCanvas, paint, resources, key, drawPosition, distanceBetweenLines);
    }

    @Override
    public void drawCross(NoteSymbol noteSymbol) {
        super.drawCross(noteSymbol);
    }

    @Override
    public void drawBody(NoteSymbol noteSymbol) {
        super.drawBody(noteSymbol);
    }

    @Override
    public void drawStem(NoteSymbol noteSymbol) {
        super.drawStem(noteSymbol);
    }

    @Override
    public void drawHelpLines() {
        super.drawHelpLines();
    }

    @Override
    public Point getCenterPointForNextSmallSymbol() {
        return super.getCenterPointForNextSmallSymbol();
    }

    public NotePositionInformation getNotePositionInformation() {
        return notePositionInformation;
    }

    public Point getCenterPointForNextSmallSymbolNoDrawPositionChange() {
        Point point = super.getCenterPointForNextSmallSymbol();

        drawPosition.setStartXPositionForNextElement(drawPosition.getStartXPositionForNextElement() - widthForOneSmallSymbol);

        return point;
    }
}
