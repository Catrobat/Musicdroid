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

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class ProjectSelectionFragment extends Fragment {

    private ArrayList<Project> projects;
    private ProjectListViewAdapter listViewAdapter;
    private ListView projectsListView;
    private Button newProjectButton;
    private ProjectSelectionActivity projectSelectionActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project_selection, container, false);
        projectsListView = (ListView) rootView.findViewById(R.id.project_list_view);
        newProjectButton = (Button) rootView.findViewById(R.id.new_project_button);

        projects = new ArrayList<>();
        projectSelectionActivity = (ProjectSelectionActivity) getActivity();

        fetchProjectInformation();

        return rootView;
    }

    public void fetchProjectInformation() {
        String[] midiFileList = getMidiFileList();
        MidiToProjectConverter midiToProjectConverter = new MidiToProjectConverter();
        projects.clear();

        if (null != midiFileList) {
            for (String aMidiFile : midiFileList) {
                try {
                    projects.add(midiToProjectConverter.convertMidiFileToProject(new File(ProjectToMidiConverter.MIDI_FOLDER, aMidiFile)));
                } catch (MidiException | IOException e) {
                    ErrorDialog.createDialog(R.string.dialog_open_midi_error, e).show(getFragmentManager(), "tag");
                }
            }
        }

        setListAdapter();
        setOnClickListeners();
    }

    private void setListAdapter() {
        listViewAdapter = new ProjectListViewAdapter(getActivity(), projects);
        projectsListView.setAdapter(listViewAdapter);
    }

    private void setOnClickListeners() {
        projectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                //TODO:  for more tracks
                if (!ProjectSelectionActivity.inCallback) {
                    projectSelectionActivity.stopPlayingTracks();
                    Intent intent = new Intent(getActivity(), PianoActivity.class);
                    intent.putExtra(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME, projects.get(position).getName());
                    startActivity(intent);
                } else {
                    listViewAdapter.setProjectSelectionBackgroundFlags(position);
                    projectSelectionActivity.notifyCheckedItemStateChanged();
                    projectSelectionActivity.notifyNumberOfItemsSelected(listViewAdapter.getProjectSelectionSelectedItemsCount());
                }
            }
        });

        projectsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listViewAdapter.getProjectSelectionSelectedItemsCount() == 0) {
                    projectSelectionActivity.startTapAndHoldActionMode();
                }

                return false;
            }
        });

        newProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectSelectionActivity.stopPlayingTracks();
                Intent intent = new Intent(getActivity(), PianoActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();
        fetchProjectInformation();
    }

    public ProjectListViewAdapter getListViewAdapter() {
        return listViewAdapter;
    }
}
