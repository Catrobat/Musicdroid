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
import android.graphics.Canvas;

import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.draw.PianoNoteSheetCanvas;

/**
 * Created by Florian on 23.10.2014.
 */
public class PianoNoteSheetCanvasMock extends PianoNoteSheetCanvas {

    public PianoNoteSheetCanvasMock(Resources resources, Canvas canvas, Track track) {
        super(resources, canvas, track);
    }

    public int getEndXPositionForDrawingElements() { return endXPositionForDrawingElements; }

    public int getYPositionOfBarTop() { return yPositionOfBarTop; }

    public int getYPositionOfBarBottom() { return yPositionOfBarBottom; }

    public int getWidthForOneSymbol() { return widthForOneSymbol; }

    public int getWidthForOneSmallSymbol() { return widthForOneSmallSymbol; }
}
