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

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ProjectTestDataFactory {

    private ProjectTestDataFactory() {
    }

    public static Project createProject() {
        return new Project("TestProject", Project.DEFAULT_BEATS_PER_MINUTE);
    }

    public static Project createProject(String name) {
        return new Project(name, Project.DEFAULT_BEATS_PER_MINUTE);
    }

    public static Project createProject(int beatsPerMinute) {
        return new Project("TestProject", beatsPerMinute);
    }

    public static Project createProjectWithTrack(MusicalInstrument instrument) {
        Project project = createProject();
        Track track = TrackTestDataFactory.createTrack(instrument);
        project.addTrack("someRandomTrackName1", track);

        return project;
    }

    public static Project createProjectWithTrack() {
        Project project = createProject();
        Track track = TrackTestDataFactory.createTrack();
        project.addTrack("someRandomTrackName1", track);

        return project;
    }

    public static Project createProjectWithSemiComplexTracks() {
        Project project = createProject();
        Track track1 = TrackTestDataFactory.createSemiComplexTrack(MusicalInstrument.GUNSHOT);
        Track track2 = TrackTestDataFactory.createSemiComplexTrack(MusicalInstrument.WHISTLE);

        project.addTrack("someRandomTrackName1", track1);
        project.addTrack("someRandomTrackName2", track2);

        return project;
    }

    public static Project createProjectWithOneSimpleTrack(String projectName) {
        Project project = createProject(projectName);
        Track track = TrackTestDataFactory.createSimpleTrack();
        project.addTrack("someRandomTrackName1", track);

        return project;
    }

    public static ArrayList<File> getProjectFilesInStorage() {
        ArrayList<File> projectFiles = new ArrayList<>();
        if (ProjectToMidiConverter.MIDI_FOLDER.isDirectory()) {
            Collections.addAll(projectFiles, ProjectToMidiConverter.MIDI_FOLDER.listFiles());
        }

        return projectFiles;
    }

    public static boolean checkIfProjectInStorage(String projectName) {
        String fileName = projectName + ProjectToMidiConverter.MIDI_FILE_EXTENSION;
        ArrayList<File> projects = getProjectFilesInStorage();
        for (int i = 0; i < projects.size(); i++)
            if (projects.get(i).getName().equals(fileName))
                return true;
        return false;
    }
}
