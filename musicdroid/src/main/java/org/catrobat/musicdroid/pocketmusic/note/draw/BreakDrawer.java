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

import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;

public class BreakDrawer {

	private NoteSheetCanvas noteSheetCanvas;
	private Resources resources;

	public BreakDrawer(NoteSheetCanvas noteSheetCanvas, Resources resources) {
		this.noteSheetCanvas = noteSheetCanvas;
		this.resources = resources;
	}

	public void drawBreak(BreakSymbol symbol) {
		/*if (symbol.getNoteLength() == NoteLength.WHOLE || symbol.getNoteLength() == NoteLength.WHOLE_DOT) {
			drawWholeBreak(noteSheetCanvas, context, symbol.getNoteLength().hasDot());
		} else if (symbol.getNoteLength() == NoteLength.HALF || symbol.getNoteLength() == NoteLength.HALF_DOT) {
			drawHalfBreak(noteSheetCanvas, context, symbol.getNoteLength().hasDot());
		} else {
			drawBreakPicture(noteSheetCanvas, context, symbol);
		}*/
	}

	private void drawBreakPicture(NoteSheetCanvas noteSheetCanvas, Resources resources, BreakSymbol symbol) {
		int breakHeight = 0;

		//Resources res = context.getResources();
		//Bitmap breakPicture = null;
		/*if (symbol.getNoteLength() == NoteLength.QUARTER || symbol.getNoteLength() == NoteLength.QUARTER_DOT) {
			breakHeight = 3 * noteSheetCanvas.getDistanceBetweenNoteLines();
			breakPicture = BitmapFactory.decodeResource(res, R.drawable.break_4);
		} else if (symbol.getNoteLength() == NoteLength.EIGHT || symbol.getNoteLength() == NoteLength.EIGHT_DOT) {
			breakHeight = 2 * noteSheetCanvas.getDistanceBetweenNoteLines();
			breakPicture = BitmapFactory.decodeResource(res, R.drawable.break_8);
		} else if (symbol.getNoteLength() == NoteLength.SIXTEENTH) {
			breakHeight = 4 * noteSheetCanvas.getDistanceBetweenNoteLines();
			breakPicture = BitmapFactory.decodeResource(res, R.drawable.break_16);
		}

		int xStartPositionForBreak = noteSheetCanvas.getStartXPointForNextSymbolSpace();
*/
		//		Rect rectWohleSpace = new Rect();
		//		rectWohleSpace.left = xStartPositionForBreak;
		//		rectWohleSpace.right = xStartPositionForBreak + noteSheetCanvas.getWidthForOneSymbol();
		//		rectWohleSpace.bottom = noteSheetCanvas.getYPositionOfCenterLine() + 2
		//				* noteSheetCanvas.getDistanceBetweenNoteLines();
		//		rectWohleSpace.top = noteSheetCanvas.getYPositionOfCenterLine() - 2
		//				* noteSheetCanvas.getDistanceBetweenNoteLines();
		/*xStartPositionForBreak += noteSheetCanvas.getWidthForOneSymbol() / 4;

		RectF rect = new RectF(PictureTools.calculateProportionalPictureContourRect(breakPicture, breakHeight,
				xStartPositionForBreak, noteSheetCanvas.getYPositionOfCenterLine()));

		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		noteSheetCanvas.getCanvas().drawBitmap(breakPicture, null, rect, null);
		if (symbol.getNoteLength().hasDot()) {
			DotDrawer.drawDotOnRightSideOfRect(rect, noteSheetCanvas);
		}
		//		noteSheetCanvas.getCanvas().drawRect(rectWohleSpace, paint);

	}

	private void drawHalfBreak(NoteSheetCanvas noteSheetCanvas, Resources resources, boolean hasDot) {
		Point centerPoint = noteSheetCanvas.getCenterPointForNextSymbol();
		RectF rect = new RectF();
		int breakWidth = noteSheetCanvas.getDistanceBetweenNoteLines();
		rect.left = centerPoint.x - breakWidth / 2;
		rect.right = centerPoint.x + breakWidth / 2;
		rect.bottom = centerPoint.y;
		rect.top = centerPoint.y - breakWidth / 2;

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		noteSheetCanvas.getCanvas().drawRect(rect, paint);

		if (hasDot) {
			DotDrawer.drawDotOnRightSideOfRect(rect, noteSheetCanvas);
		}
	}

	private void drawWholeBreak(NoteSheetCanvas noteSheetCanvas, Context context, boolean hasDot) {
		Point centerPoint = noteSheetCanvas.getCenterPointForNextSmallSymbol();
		RectF rect = new RectF();
		int breakWidth = noteSheetCanvas.getDistanceBetweenNoteLines();
		rect.left = centerPoint.x - breakWidth / 2;
		rect.right = centerPoint.x + breakWidth / 2;
		rect.top = centerPoint.y;
		rect.bottom = centerPoint.y + breakWidth / 2;

		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.FILL);
		noteSheetCanvas.getCanvas().drawRect(rect, paint);

		if (hasDot) {
			DotDrawer.drawDotOnRightSideOfRect(rect, noteSheetCanvas);
		}*/
	}
}
