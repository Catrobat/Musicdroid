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

    public void testPushPreparedMemento1() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        mementoStack.pushPreparedMemento();

        assertTrue(mementoStack.isEmpty());
    }

    public void testPushPreparedMemento2() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        Track track = TrackTestDataFactory.createSimpleTrack();
        mementoStack.prepareMemento(track);
        mementoStack.pushPreparedMemento();

        assertFalse(mementoStack.isEmpty());
    }

    public void testPopMementoAsTrack1() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        Track track = mementoStack.popMementoAsTrack();

        assertNull(track);
    }

    public void testPopMementoAsTrack2() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        Track expectedTrack = TrackTestDataFactory.createSimpleTrack();
        mementoStack.prepareMemento(expectedTrack);
        mementoStack.pushPreparedMemento();
        Track actualTrack = mementoStack.popMementoAsTrack();

        assertTrue(expectedTrack != actualTrack);
        assertTrue(expectedTrack.equals(actualTrack));
    }

    public void testIsEmpty1() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        assertTrue(mementoStack.isEmpty());
    }

    public void testIsEmpty2() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        mementoStack.prepareMemento(TrackTestDataFactory.createSimpleTrack());

        assertTrue(mementoStack.isEmpty());
    }

    public void testIsEmpty3() {
        TrackMementoStack mementoStack = new TrackMementoStack();

        mementoStack.prepareMemento(TrackTestDataFactory.createSimpleTrack());
        mementoStack.pushPreparedMemento();

        assertFalse(mementoStack.isEmpty());
    }
}