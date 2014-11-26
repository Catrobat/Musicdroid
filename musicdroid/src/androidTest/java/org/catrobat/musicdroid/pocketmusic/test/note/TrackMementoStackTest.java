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

import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.TrackMementoStack;

/**
 * Created by Daniel on 16.10.2014.
 */
public class TrackMementoStackTest extends AndroidTestCase {

    private TrackMementoStack mementoStack;

    @Override
    protected void setUp() {
        mementoStack = new TrackMementoStack();
    }

    public void testPushMemento() {
        Track track = TrackTestDataFactory.createSimpleTrack();
        mementoStack.pushMemento(track);

        assertFalse("The memento stack has not to be empty after pushing a track",
                mementoStack.isEmpty());
    }

    public void testPopMementoAsTrack1() {
        Track track = mementoStack.popMementoAsTrack();

        assertNull("Pop of an empty stack must return null", track);
    }

    public void testPopMementoAsTrack2() {
        Track expectedTrack = TrackTestDataFactory.createSimpleTrack();
        mementoStack.pushMemento(expectedTrack);
        Track actualTrack = mementoStack.popMementoAsTrack();

        assertTrue("The objects have not to be the same after push and pop of memento stack",
                expectedTrack != actualTrack);
        assertTrue("The objects have to be equal after push and pop from memento stack",
                expectedTrack.equals(actualTrack));
    }

    public void testIsEmpty() {
        assertTrue(mementoStack.isEmpty());
    }

    public void testClear() {
        mementoStack.pushMemento(TrackTestDataFactory.createTrack());
        mementoStack.clear();

        int expectedStackSize = 0;
        int actualStackSize = mementoStack.size();

        assertEquals("The expected size is 0", expectedStackSize, actualStackSize);
    }
}
