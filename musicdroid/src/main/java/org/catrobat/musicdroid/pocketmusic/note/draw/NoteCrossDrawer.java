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
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import org.catrobat.musicdroid.pocketmusic.R;

public class NoteCrossDrawer {

    private NoteSheetCanvas noteSheetCanvas;
    private Resources resources;
    private int distanceBetweenLines;

    public NoteCrossDrawer(NoteSheetCanvas noteSheetCanvas, Resources resources, int distanceBetweenLines) {
        this.noteSheetCanvas = noteSheetCanvas;
        this.resources = resources;
        this.distanceBetweenLines = distanceBetweenLines;
    }

    public RectF drawCross(int xPosition, int yPosition, Paint paint) {
        int crossHeight = 2 * distanceBetweenLines;

        Rect crossRect = noteSheetCanvas.drawBitmap(resources, R.drawable.cross, crossHeight, xPosition, yPosition, paint);

        return new RectF(crossRect.left, crossRect.top, crossRect.right, crossRect.bottom);
    }
}
