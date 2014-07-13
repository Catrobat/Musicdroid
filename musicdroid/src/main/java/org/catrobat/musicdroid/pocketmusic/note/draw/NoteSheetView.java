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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.widget.ScrollView;


import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.tools.PictureTools;

/**
 * @author musicdroid
 */
public class NoteSheetView extends ScrollView {

	private static final int BOLD_BAR_WIDTH = 5;
	private static final int THIN_BAR_WIDTH = 2;
	private static final int NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS = 2;
	private static final int HEIGHT_OF_KEY_IN_LINE_SPACES = 6;
	private static final int NOTE_SHEET_PADDING = 20;

	private Paint paint;

	private Track track;
	private int xStartPositionOfLine;
	private int xEndPositionOfLine;
	private int yCenter;
	private int distanceBetweenLines;
	private int halfBarHeight;
	private int xPositionOfNextSheetElement;
	private NoteSheetCanvas noteSheetCanvas;
	private TrackDrawer trackDrawer;

	public NoteSheetView(Context context) {
		super(context);
		paint = new Paint();
		track = new Track(MusicalKey.VIOLIN, MusicalInstrument.ACOUSTIC_GRAND_PIANO); // TODO fw
		long tick = 0;
		NoteEvent note = new NoteEvent(NoteName.D4, true);
		track.addNoteEvent(tick, note);
		tick += NoteLength.SIXTEENTH.getTickDuration();
		NoteEvent note2 = new NoteEvent(NoteName.D4, false);
		track.addNoteEvent(tick, note2);

		tick += NoteLength.QUARTER_DOT.getTickDuration();
		NoteEvent note3 = new NoteEvent(NoteName.D4, true);
		track.addNoteEvent(tick, note3);
		tick += NoteLength.HALF_DOT.getTickDuration();
		NoteEvent note4 = new NoteEvent(NoteName.D4, false);
		track.addNoteEvent(tick, note4);

		trackDrawer = new TrackDrawer();
		xPositionOfNextSheetElement = NOTE_SHEET_PADDING;
	}

	public void redraw(Track track) {
		this.track = track;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		noteSheetCanvas = new NoteSheetCanvas(canvas);
		xEndPositionOfLine = noteSheetCanvas.getCanvas().getWidth() - NOTE_SHEET_PADDING;
		yCenter = noteSheetCanvas.getYPositionOfCenterLine();
		xStartPositionOfLine = NOTE_SHEET_PADDING;
		distanceBetweenLines = noteSheetCanvas.getDistanceBetweenNoteLines();
		halfBarHeight = NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS * distanceBetweenLines;
		paint.setColor(Color.BLACK);

		drawSheetElements();

		xPositionOfNextSheetElement = NOTE_SHEET_PADDING;
	}

	;

	private void drawSheetElements() {
		drawLines();
		drawLineEndBars();
		drawKey();
		drawTactUnit();
		drawBeats();
		drawTrack();
	}

	private void drawLines() {
		for (int lineDistanceFromCenterLine = -NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine <= NUMBER_LINES_FROM_CENTER_LINE_IN_BOTH_DIRECTIONS; lineDistanceFromCenterLine++) {
			int actualLinePosition = yCenter + lineDistanceFromCenterLine * distanceBetweenLines;
			noteSheetCanvas.getCanvas().drawLine(xStartPositionOfLine, actualLinePosition, xEndPositionOfLine,
					actualLinePosition, paint);
		}
	}

	private void drawLineEndBars() {
		paint.setStyle(Style.FILL);
		drawEndBar();
		drawFrontBars();
	}

	private void drawFrontBars() {
		drawBoldBar(xStartPositionOfLine);
		drawThinBar(xStartPositionOfLine + 2 * BOLD_BAR_WIDTH);
	}

	private void drawEndBar() {
		int leftPositionOfEndBar = xEndPositionOfLine - BOLD_BAR_WIDTH;
		drawBoldBar(leftPositionOfEndBar);
		noteSheetCanvas.setEndXPositionNotes(leftPositionOfEndBar);
	}

	private void drawThinBar(int xBarStartPosition) {
		int xEndThinBar = xBarStartPosition + THIN_BAR_WIDTH;
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight, xEndThinBar, yCenter + halfBarHeight);

		noteSheetCanvas.getCanvas().drawRect(boldBar, paint);
		xPositionOfNextSheetElement = xEndThinBar;
	}

	private void drawBoldBar(int xBarStartPosition) {
		Rect boldBar = new Rect(xBarStartPosition, yCenter - halfBarHeight, xBarStartPosition + BOLD_BAR_WIDTH, yCenter
				+ halfBarHeight);

		noteSheetCanvas.getCanvas().drawRect(boldBar, paint);
	}

	private void drawKey() {
		Resources res = getContext().getResources();
		Bitmap keyPicture;
		MusicalKey key = track.getKey();
		if (key == MusicalKey.VIOLIN) {
			keyPicture = BitmapFactory.decodeResource(res, R.drawable.violine);
		} else {
			keyPicture = BitmapFactory.decodeResource(res, R.drawable.violine);
		}

		int keyPictureHeight = distanceBetweenLines * HEIGHT_OF_KEY_IN_LINE_SPACES;

		Rect rect = PictureTools.calculateProportionalPictureContourRect(keyPicture, keyPictureHeight,
                xPositionOfNextSheetElement, yCenter);

		noteSheetCanvas.getCanvas().drawBitmap(keyPicture, null, rect, null);
		xPositionOfNextSheetElement = rect.right;
	}

	private void drawTactUnit() {
		Resources res = getContext().getResources();
		Bitmap tactPicture;

		// TODO: Tact has to be checked here
		tactPicture = BitmapFactory.decodeResource(res, R.drawable.tact_3_4);

		int tactPictureHeight = distanceBetweenLines * 4;

		Rect rect = PictureTools.calculateProportionalPictureContourRect(tactPicture, tactPictureHeight,
				xPositionOfNextSheetElement, yCenter);

		noteSheetCanvas.getCanvas().drawBitmap(tactPicture, null, rect, null);
		noteSheetCanvas.setStartXPositionNotes(rect.right);
	}

	private void drawBeats() {
		// TODO
	}

	private void drawTrack() {
		trackDrawer.drawTrack(track, noteSheetCanvas, getContext());
	}
}