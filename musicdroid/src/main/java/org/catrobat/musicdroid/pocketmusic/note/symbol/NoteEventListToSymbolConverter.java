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
package org.catrobat.musicdroid.pocketmusic.note.symbol;

import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.NoteLength;
import org.catrobat.musicdroid.pocketmusic.note.NoteName;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NoteEventListToSymbolConverter {

	private long lastTick;
	private Map<NoteName, Long> openNotes;
	private Symbol currentSymbol;

	public NoteEventListToSymbolConverter() {
		lastTick = 0;
		openNotes = new HashMap<NoteName, Long>();
		currentSymbol = null;
	}

	public List<Symbol> convertNoteEventList(long tick, List<NoteEvent> noteEvents, int beatsPerMinute) {
		List<Symbol> symbols = new LinkedList<Symbol>();

		for (NoteEvent noteEvent : noteEvents) {
			NoteName noteName = noteEvent.getNoteName();

			if (noteEvent.isNoteOn()) {
				if (lastTick != tick) {
					NoteLength noteLength = NoteLength.getNoteLengthFromTickDuration(tick - lastTick, beatsPerMinute);
					symbols.add(new BreakSymbol(noteLength));
				}

				if (openNotes.isEmpty()) {
					currentSymbol = new NoteSymbol();
				}

				openNotes.put(noteName, tick);
			} else {
				long lastTickForNote = openNotes.get(noteName);
				NoteLength noteLength = NoteLength.getNoteLengthFromTickDuration(tick - lastTickForNote, beatsPerMinute);

				if (currentSymbol instanceof NoteSymbol) {
					((NoteSymbol) currentSymbol).addNote(noteName, noteLength);
				}

				openNotes.remove(noteName);

				if (false == symbols.contains(currentSymbol)) {
					symbols.add(currentSymbol);
				}
			}

			lastTick = tick;
		}

		return symbols;
	}
}
