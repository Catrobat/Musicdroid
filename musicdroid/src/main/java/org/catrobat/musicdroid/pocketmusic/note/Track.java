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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Track implements Serializable {

    private static final long serialVersionUID = 7483021689872527955L;

    private MusicalInstrument instrument;
    private HashMap<Long, List<NoteEvent>> events;
    private MusicalKey key;
    private long lastTick;

    public Track(MusicalKey key, MusicalInstrument instrument) {
        this.events = new HashMap<Long, List<NoteEvent>>();
        this.instrument = instrument;
        this.key = key;
        this.lastTick = 0;
    }

    public Track(Track track) {
        this.events = new HashMap<Long, List<NoteEvent>>();
        this.instrument = track.getInstrument();
        this.key = track.getKey();
        this.lastTick = track.getLastTick();

        for (long tick : track.getSortedTicks()) {
            List<NoteEvent> noteEventList = new LinkedList<NoteEvent>();
            this.events.put(tick, noteEventList);

            for (NoteEvent noteEvent : track.getNoteEventsForTick(tick)) {
                noteEventList.add(new NoteEvent(noteEvent));
            }
        }
    }

    public MusicalInstrument getInstrument() {
        return instrument;
    }

    public MusicalKey getKey() {
        return key;
    }


    public void addNoteEvent(long tick, NoteEvent noteEvent) {
        List<NoteEvent> noteEventList = null;

        if (events.containsKey(tick)) {
            noteEventList = events.get(tick);
        } else {
            noteEventList = new LinkedList<NoteEvent>();
            events.put(tick, noteEventList);
        }

        if (false == noteEvent.isNoteOn()) {
            lastTick = tick;
        }

        noteEventList.add(noteEvent);
    }

    public List<NoteEvent> getNoteEventsForTick(long tick) {
        List<NoteEvent> noteEvents = events.get(tick);

        Collections.sort(noteEvents, new Comparator<NoteEvent>() {
            @Override
            public int compare(NoteEvent noteEvent1, NoteEvent noteEvent2) {
                if (noteEvent1.isNoteOn() == noteEvent2.isNoteOn()) {
                    return 0;
                } else if (noteEvent1.isNoteOn()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        return noteEvents;
    }

    public Set<Long> getSortedTicks() {
        return new TreeSet<Long>(events.keySet());
    }

    public int size() {
        int size = 0;

        for (List<NoteEvent> noteEventList : events.values()) {
            size += noteEventList.size();
        }

        return size;
    }

    public long getLastTick() {
        return lastTick;
    }

    public long getTotalTimeInMilliseconds() {
        return NoteLength.tickToMilliseconds(lastTick);
    }

    public boolean empty() {
        return (0 == size());
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Track)) {
            return false;
        }

        Track track = (Track) obj;

        if (track.getInstrument() != getInstrument()) {
            return false;
        }

        if (track.getKey() != getKey()) {
            return false;
        }

        Set<Long> ownTrackTicks = getSortedTicks();
        Set<Long> otherTrackTicks = track.getSortedTicks();

        if (otherTrackTicks.equals(ownTrackTicks)) {
            for (long tick : ownTrackTicks) {
                List<NoteEvent> ownNoteEventList = getNoteEventsForTick(tick);
                List<NoteEvent> otherNoteEventList = track.getNoteEventsForTick(tick);

                if (false == ownNoteEventList.equals(otherNoteEventList)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[Track] instrument=" + instrument + " key=" + key + " size=" + size();
    }

    public void increaseLastTick(long difference) {
        lastTick += difference;
    }
}
