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
import android.graphics.Canvas;
import android.graphics.Point;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class PianoNoteSheetCanvas extends NoteSheetCanvas {

    public static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;
    public static final int HEIGHT_OF_TACT_UNIT_IN_LINE_SPACES = 4;

    private TrackDrawer trackDrawer;
    private Resources resources;
    private Track track;

    private final int widthForOneSymbol;
    private final int widthForOneSmallSymbol;

    public PianoNoteSheetCanvas(Resources resources, Canvas canvas, Track track) {
        super(canvas);
        this.trackDrawer = new TrackDrawer();

        this.resources = resources;
        this.track = track;
        this.widthForOneSymbol = 3 * distanceBetweenLines;
        this.widthForOneSmallSymbol = widthForOneSymbol / 4;
    }

    public int getWidthForDrawingTrack() {
        return startXPositionForNextElement;
    }

    private Point getCenterPointForNextSymbol(int symbolWidth) {
        Point centerPoint = new Point();

        int x = startXPositionForNextElement + symbolWidth / 2;
        int y = getYPositionOfCenterLine();

        centerPoint.set(x, y);

        startXPositionForNextElement += symbolWidth;

        return centerPoint;
    }

    public Point getCenterPointForNextSymbol() {
        return getCenterPointForNextSymbol(widthForOneSymbol);
    }

    public Point getCenterPointForNextSmallSymbol() {
        return getCenterPointForNextSymbol(widthForOneSmallSymbol);
    }

    @Override
    protected void drawSheetElements() {
        drawKey();
        drawTactUnit();
        drawTrack();
    }

	private void drawKey() {
		if (track.getKey() != MusicalKey.VIOLIN) {
            throw new UnsupportedOperationException();
		}

		int keyPictureHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

        startXPositionForNextElement = drawBitmap(resources, R.drawable.violine, keyPictureHeight, startXPositionForNextElement, yPositionOfCenterLine).right;
	}

	private void drawTactUnit() {
		int tactPictureHeight = distanceBetweenLines * HEIGHT_OF_TACT_UNIT_IN_LINE_SPACES;

        startXPositionForNextElement = drawBitmap(resources, R.drawable.tact_3_4, tactPictureHeight, startXPositionForNextElement, yPositionOfCenterLine).right;
    }

    private void drawTrack() {
        trackDrawer.drawTrack(track, this, resources);
    }
}
