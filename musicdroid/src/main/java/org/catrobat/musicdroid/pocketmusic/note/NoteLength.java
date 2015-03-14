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

import java.util.HashMap;
import java.util.Map;

public enum NoteLength {
    WHOLE_DOT(4f + 2f), WHOLE(4f), HALF_DOT(2f + 1f), HALF(2f), QUARTER_DOT(1f + 1 / 2f),
    QUARTER(1f), EIGHT_DOT(1 / 2f + 1 / 4f), EIGHT(1 / 2f), SIXTEENTH(1 / 4f);

    private static final NoteLengthMillisecondsHelper NOTE_LENGTH_MILLISECONDS_HELPER = new NoteLengthMillisecondsHelper();
    private static final NoteLength[] SORTED_NOTE_LENGTHS = new NoteLength[]{SIXTEENTH, EIGHT, EIGHT_DOT, QUARTER, QUARTER_DOT, HALF, HALF_DOT, WHOLE, WHOLE_DOT};
    private static final long DEFAULT_TICK_DURATION_MODIFIER = 8;
    private static final NoteLength SMALLEST_NOTE_LENGTH = SIXTEENTH;

    private static final int MINUTE_IN_SECONDS = 60;
    private static final int SECOND_IN_MILLISECONDS = 1000;

    private float length;

    private NoteLength(float length) {
        this.length = length;
    }

    public static NoteLength getNoteLengthFromTickDuration(long duration, int beatsPerMinute) {
        NoteLength noteLength = SMALLEST_NOTE_LENGTH;
        NoteLength[] allNoteLengths = NoteLength.values();

        for (int i = (allNoteLengths.length - 1); i >= 0; i--) {
            long difference = duration - allNoteLengths[i].toTicks(beatsPerMinute);

            if (difference < 0) {
                break;
            }

            noteLength = allNoteLengths[i];
        }

        return noteLength;
    }

    public static NoteLength getNoteLengthFromMilliseconds(long millis, int beatsPerMinute) {
        long[] calculatedMilliseconds = NOTE_LENGTH_MILLISECONDS_HELPER.getMilliseconds(beatsPerMinute);
        long bottomLimit = calculatedMilliseconds[0];
        int bottomIndex = 0;
        long topLimit = calculatedMilliseconds[calculatedMilliseconds.length - 1];
        int topIndex = 0;

        for (int i = 0; i < calculatedMilliseconds.length; i++) {
            long calculatedMillis = calculatedMilliseconds[i];
            if (millis > calculatedMillis) {
                bottomLimit = calculatedMillis;
                bottomIndex = i;
            } else {
                topLimit = calculatedMillis;
                topIndex = i;
                break;
            }
        }

        long distanceBottom = Math.abs(bottomLimit - millis);
        long distanceTop = Math.abs(topLimit - millis);

        if (distanceBottom > distanceTop) {
            return SORTED_NOTE_LENGTHS[topIndex];
        } else {
            return SORTED_NOTE_LENGTHS[bottomIndex];
        }
    }

    public long toTicks(int beatsPerMinute) {
        return Math.round(beatsPerMinute * DEFAULT_TICK_DURATION_MODIFIER * length);
    }

    public long toMilliseconds(int beatsPerMinute) {
        return Math.round(beatsPerMinute * length * SECOND_IN_MILLISECONDS / MINUTE_IN_SECONDS);
    }

    public static long tickToMilliseconds(long tick) {
        long millis = tick * SECOND_IN_MILLISECONDS / MINUTE_IN_SECONDS / DEFAULT_TICK_DURATION_MODIFIER;
        return millis;
    }

    public boolean hasStem() {
        if ((this == WHOLE) || (this == WHOLE_DOT)) {
            return false;
        }

        return true;
    }

    public NoteFlag getFlag() {
        if (this == SIXTEENTH) {
            return NoteFlag.DOUBLE_FLAG;
        } else if ((this == EIGHT) || (this == EIGHT_DOT)) {
            return NoteFlag.SINGLE_FLAG;
        } else {
            return NoteFlag.NO_FLAG;
        }
    }

    public boolean hasDot() {
        return (WHOLE_DOT == this) || (HALF_DOT == this) || (QUARTER_DOT == this) || (EIGHT_DOT == this);
    }

    public boolean isFilled() {
        return (WHOLE_DOT != this) && (WHOLE != this) && (HALF != this) && (HALF_DOT != this);
    }

    public boolean isHalfOrHigher() {
        if ((WHOLE_DOT == this) || (WHOLE == this) || (HALF_DOT == this) || (HALF == this)) {
            return true;
        }

        return false;
    }

    private static class NoteLengthMillisecondsHelper {

        private Map<Integer, long[]> millisecondsCalculationMap;

        public NoteLengthMillisecondsHelper() {
            millisecondsCalculationMap = new HashMap<Integer, long[]>();
        }

        public long[] getMilliseconds(int beatsPerMinute) {
            if (false == millisecondsCalculationMap.containsKey(beatsPerMinute)) {
                millisecondsCalculationMap.put(beatsPerMinute, calculateMilliseconds(beatsPerMinute));
            }

            return millisecondsCalculationMap.get(beatsPerMinute);
        }

        private long[] calculateMilliseconds(int beatsPerMinute) {
            long[] milliseconds = new long[SORTED_NOTE_LENGTHS.length];

            for (int i = 0; i < milliseconds.length; i++) {
                NoteLength noteLength = SORTED_NOTE_LENGTHS[i];
                milliseconds[i] = noteLength.toMilliseconds(beatsPerMinute);
            }

            return milliseconds;
        }
    }
}
