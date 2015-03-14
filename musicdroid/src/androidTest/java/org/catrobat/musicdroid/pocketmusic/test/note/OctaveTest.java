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
package org.catrobat.musicdroid.pocketmusic.test.note;

import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.note.NoteName;
import org.catrobat.musicdroid.pocketmusic.note.Octave;

import java.util.Arrays;

public class OctaveTest extends AndroidTestCase {

    public void testGetNoteNames() {
        NoteName[] noteNames = new NoteName[]{
                NoteName.C1, NoteName.C1S,
                NoteName.D1, NoteName.D1S,
                NoteName.E1, NoteName.F1,
                NoteName.F1S, NoteName.G1,
                NoteName.G1S, NoteName.A1,
                NoteName.A1S, NoteName.B1};
        Octave octave = Octave.createContraOctave();

        assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
    }

    public void testCreateCustomOctave1() {
        NoteName startNoteName = NoteName.D1S;
        NoteName[] noteNames = new NoteName[]{
                NoteName.D1S, NoteName.E1,
                NoteName.F1, NoteName.F1S,
                NoteName.G1, NoteName.G1S,
                NoteName.A1, NoteName.A1S,
                NoteName.B1, NoteName.C2,
                NoteName.C2S, NoteName.D2};
        Octave octave = Octave.createCustomOctave(startNoteName);

        assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
    }

    public void testCreateCustomOctave2() {
        NoteName startNoteName = NoteName.C8;
        NoteName[] noteNames = new NoteName[]{
                NoteName.C8};
        Octave octave = Octave.createCustomOctave(startNoteName);

        assertTrue(Arrays.equals(noteNames, octave.getNoteNames()));
    }
}
