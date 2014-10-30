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

import java.io.Serializable;
import java.util.Stack;

public class TrackMementoStack implements Serializable {

    private class TrackMemento implements Serializable {

        private static final long serialVersionUID = 7126768840934053008L;

        private Track track;

        public TrackMemento(Track track) {
            this.track = new Track(track);
        }

        public Track getTrack() {
            return track;
        }
    }

    private static final long serialVersionUID = 7481421611372527768L;

    private Stack<TrackMemento> mementoStack;

    public TrackMementoStack() {
        mementoStack = new Stack<TrackMemento>();
    }

    public void pushMemento(Track track) {
        TrackMemento memento = new TrackMemento(track);
        mementoStack.push(memento);
    }

    public Track popMementoAsTrack() {
        if (isEmpty()) {
            return null;
        }

        return mementoStack.pop().getTrack();
    }

    public boolean isEmpty() {
        return mementoStack.isEmpty();
    }
}
