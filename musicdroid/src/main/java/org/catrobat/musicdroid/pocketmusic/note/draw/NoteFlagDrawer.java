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

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteFlag;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteSymbol;

public class NoteFlagDrawer {

    private NoteSheetCanvas noteSheetCanvas;
    private int distanceBetweenLines;

    public NoteFlagDrawer(NoteSheetCanvas noteSheetCanvas, int distanceBetweenLines) {
        this.noteSheetCanvas = noteSheetCanvas;
        this.distanceBetweenLines = distanceBetweenLines;
    }

    public void drawFlag(PointF endPointOfNoteStem, NoteSymbol noteSymbol, MusicalKey key, Paint paint) {
        boolean isStemUp = noteSymbol.isStemUp(key);
        Paint.Style savedStyle = paint.getStyle();
        paint.setStyle(Paint.Style.STROKE);

        drawBezierPath(endPointOfNoteStem, isStemUp, paint);

        if (NoteFlag.DOUBLE_FLAG == noteSymbol.getFlag()) {
            if (isStemUp) {
                endPointOfNoteStem.y += distanceBetweenLines;
            } else {
                endPointOfNoteStem.y -= distanceBetweenLines;
            }

            drawBezierPath(endPointOfNoteStem, isStemUp, paint);
        }

        paint.setStyle(savedStyle);
    }

    private void drawBezierPath(PointF endPointOfNoteStem, boolean isStemUp, Paint paint) {
        float xPosition = endPointOfNoteStem.x;
        float yPosition = endPointOfNoteStem.y;

        Path bezierPath = new Path();
        bezierPath.moveTo(xPosition, yPosition);

        if (isStemUp) {
            bezierPath.cubicTo(xPosition, yPosition + 3 * distanceBetweenLines / 2, xPosition + distanceBetweenLines * 2, yPosition + distanceBetweenLines * 2, xPosition + distanceBetweenLines / 2, yPosition + distanceBetweenLines * 3);
        } else {
            bezierPath.cubicTo(xPosition, yPosition - distanceBetweenLines, xPosition + distanceBetweenLines * 2, yPosition - distanceBetweenLines * 2, xPosition + distanceBetweenLines, yPosition - distanceBetweenLines * 2 - distanceBetweenLines / 2);
        }

        noteSheetCanvas.drawPath(bezierPath, paint);
    }
}
