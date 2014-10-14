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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.Track;

public class PianoNoteSheetCanvas extends NoteSheetCanvas {

    private static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;

    private TrackDrawer trackDrawer;
    private Resources resources;
    private Track track;

    private final int widthForOneSymbol;
    private final int widthForOneSmallSymbol;

    public PianoNoteSheetCanvas(Resources res, Canvas canvas, Track track) {
        super(canvas);
        this.trackDrawer = new TrackDrawer();

        this.resources = res;
        this.track = track;
        this.widthForOneSymbol = 3 * getDistanceBetweenLines();
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
        drawBeats();
        drawTrack();
    }

	private void drawKey() {
		Bitmap keyPicture;
		MusicalKey key = track.getKey();

		if (key == MusicalKey.VIOLIN) {
			keyPicture = BitmapFactory.decodeResource(resources, R.drawable.violine);
		} else {
			throw new UnsupportedOperationException();
		}

		int keyPictureHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

		Rect rect = PictureTools.calculateProportionalPictureContourRect(keyPicture, keyPictureHeight,
				startXPositionForNextElement, yPositionOfCenterLine);

		canvas.drawBitmap(keyPicture, null, rect, null);
		startXPositionForNextElement = rect.right;
	}

	private void drawTactUnit() {
		Bitmap tactPicture;

		// TODO: Tact has to be checked here
		tactPicture = BitmapFactory.decodeResource(resources, R.drawable.tact_3_4);

		int tactPictureHeight = distanceBetweenLines * 4;

		Rect rect = PictureTools.calculateProportionalPictureContourRect(tactPicture, tactPictureHeight,
				startXPositionForNextElement, yPositionOfCenterLine);

		canvas.drawBitmap(tactPicture, null, rect, null);
        startXPositionForNextElement = rect.right;
    }

    private void drawBeats() {
        // TODO
    }

    private void drawTrack() {
        trackDrawer.drawTrack(track, this, resources);
    }
}
