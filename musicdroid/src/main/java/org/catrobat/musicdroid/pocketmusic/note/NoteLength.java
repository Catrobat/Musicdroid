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

package org.catrobat.musicdroid.pocketmusic.note;

public enum NoteLength {
    WHOLE_DOT(4f + 2f), WHOLE(4f), HALF_DOT(2f + 1f), HALF(2f), QUARTER_DOT(1f + 1 / 2f),
    QUARTER(1f), EIGHT_DOT(1 / 2f + 1 / 4f), EIGHT(1 / 2f), SIXTEENTH(1 / 4f);

    private static final long DEFAULT_DURATION = 384 / 48 * 60;
    private static final NoteLength SMALLEST_NOTE_LENGTH = SIXTEENTH;

    private long tickDuration;

    private NoteLength(float length) {
        this.tickDuration = Math.round(DEFAULT_DURATION * length);
    }

    public static NoteLength getNoteLengthFromTickDuration(long duration) {
        NoteLength noteLength = SMALLEST_NOTE_LENGTH;
        NoteLength[] allNoteLengths = NoteLength.values();

        for (int i = (allNoteLengths.length - 1); i >= 0; i--) {
            long difference = duration - allNoteLengths[i].getTickDuration();

            if (difference < 0) {
                break;
            }

            noteLength = allNoteLengths[i];
        }

        return noteLength;
    }

    public long getTickDuration() {
        return tickDuration;
    }

    public boolean hasStem() {
        if ((this == WHOLE) || (this == WHOLE_DOT)) {
            return false;
        }

        return true;
    }

    public boolean hasFlag() {
        return ((this == EIGHT) || (this == EIGHT_DOT) || (this == SIXTEENTH));
    }

    public int getAmountOfFlags() {
        if (this == SIXTEENTH) {
            return 2;
        } else if ((this == EIGHT) || (this == EIGHT_DOT)) {
            return 1;
        }

        return 0;
    }

    public boolean hasDot() {
        return (this == WHOLE_DOT) || (this == HALF_DOT) || (this == QUARTER_DOT) || (this == EIGHT_DOT);
    }

    public boolean isFilled() {
        return (this != WHOLE_DOT) && (this != WHOLE) && (this != HALF) && (this != HALF_DOT);
    }
}
