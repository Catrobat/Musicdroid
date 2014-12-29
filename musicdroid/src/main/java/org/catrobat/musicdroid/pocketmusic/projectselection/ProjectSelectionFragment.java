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

package org.catrobat.musicdroid.pocketmusic.projectselection;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class ProjectSelectionFragment extends Fragment {

    private String[] midiFileList;

    private Project[] projects;
    private String[] projectNames;
    private String[] projectDurations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_project_selection, container, false);
        ListView projectsListView = (ListView) rootView.findViewById(R.id.project_list_view);

        midiFileList = getMidiFileList();

        projects = new Project[midiFileList.length];
        projectNames = new String[midiFileList.length];
        projectDurations = new String[midiFileList.length];

        initializeProject();

        ProjectListViewAdapter adapter = new ProjectListViewAdapter(getActivity(), projects.length, projectNames, projectDurations);
        projectsListView.setAdapter(adapter);
        projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                //TODO: only for 1track piano
                Intent intent = new Intent(getActivity(), PianoActivity.class);
                intent.putExtra("fileName", projectNames[position]);
                startActivity(intent);
            }
        });


        return rootView;
    }


    private String[] getMidiFileList() {

        if (ProjectToMidiConverter.MIDI_FOLDER.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File selectedFile = new File(dir, filename);
                    return filename.contains(ProjectToMidiConverter.MIDI_FILE_EXTENSION) || selectedFile.isDirectory();
                }
            };
            return ProjectToMidiConverter.MIDI_FOLDER.list(filter);
        }
        return null;
    }

    private void initializeProject() {
        MidiToProjectConverter midiToProjectConverter = new MidiToProjectConverter();
        for (int i = 0; i < midiFileList.length; i++)
            try {
                projects[i] = midiToProjectConverter.convertMidiFileToProject(new File(ProjectToMidiConverter.MIDI_FOLDER, midiFileList[i]));
                projectNames[i] = projects[i].getName();
                projectDurations[i] = String.valueOf(projects[i].getTrack(0).getTotalTimeInMilliseconds());
            } catch (MidiException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}


