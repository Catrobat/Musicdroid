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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Project implements Serializable {

    public static final int DEFAULT_BEATS_PER_MINUTE = 60;
    private static final long serialVersionUID = 7396763540934053008L;

    private String name;
    private int beatsPerMinute;
    private Map<String, Track> tracks;

    public Project(String name, int beatsPerMinute) {
        this.name = name;
        this.beatsPerMinute = beatsPerMinute;
        this.tracks = new HashMap<>();
    }

    public Project(Project project) {
        name = project.getName();
        beatsPerMinute = project.getBeatsPerMinute();
        tracks = new HashMap<>();

        for (String name : project.tracks.keySet()) {
            tracks.put(name, new Track(project.tracks.get(name)));
        }
    }

    public Project(Project project, String name) {
        this(project);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeatsPerMinute() {
        return beatsPerMinute;
    }

    public void addTrack(String trackName, Track track) {
        tracks.put(trackName, track);
    }

    public Set<String> getTrackNames() {
        return tracks.keySet();
    }

    public Track getTrack(String trackName) {
        return tracks.get(trackName);
    }

    public long getTotalTimeInMilliseconds() {
        long totalTime = 0;

        for (Track track : tracks.values()) {
            long trackTime = track.getTotalTimeInMilliseconds();

            if (trackTime > totalTime) {
                totalTime = trackTime;
            }
        }

        return totalTime;
    }

    public int size() {
        return tracks.size();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Project)) {
            return false;
        }

        Project project = (Project) obj;

        if (false == getName().equals(project.getName())) {
            return false;
        }

        if (getBeatsPerMinute() != project.getBeatsPerMinute()) {
            return false;
        }

        if (tracks.equals(project.tracks)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[Project] name=" + name + " beatsPerMinute=" + beatsPerMinute + " trackCount=" + size();
    }
}
