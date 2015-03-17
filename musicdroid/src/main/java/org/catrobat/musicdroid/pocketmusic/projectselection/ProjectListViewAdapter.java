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


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectListViewAdapter extends BaseAdapter {

    private final Activity activity;

    private ProjectToMidiConverter projectToMidiConverter;

    private ArrayList<Project> projects;
    private ArrayList<Boolean> projectSelectionBackgroundFlags;
    private ArrayList<Boolean> projectSelectionTrackIsPlayingFlags;

    private boolean playButtonLock = false;
    private ViewHolder viewHolder;

    static class ViewHolder {
        public ImageButton projectPlayButton;
        public ImageButton projectPauseButton;
        public TextView projectNameTextView;
        public TextView projectDurationTextView;
        public RelativeLayout projectListItemLayout;
    }

    public ProjectListViewAdapter(Activity activity, ArrayList<Project> projects) {
        projectToMidiConverter = new ProjectToMidiConverter();
        this.activity = activity;
        this.projects = projects;
        initViewParameters();
    }

    private void initViewParameters() {
        this.projectSelectionBackgroundFlags = new ArrayList<>();
        this.projectSelectionTrackIsPlayingFlags = new ArrayList<>();

        for (int i = 0; i < projects.size(); i++) {
            this.projectSelectionBackgroundFlags.add(false);
            this.projectSelectionTrackIsPlayingFlags.add(false);
        }
    }

    public boolean deleteItemByProjectName(String projectName) {
        if (projectToMidiConverter.deleteMidiByName(projectName)) {
            for (int i = 0; i < projects.size(); i++) {
                if (projectName.equals(projects.get(i).getName())) {
                    projects.remove(i);
                    projectSelectionBackgroundFlags.remove(i);
                    projectSelectionTrackIsPlayingFlags.remove(i);
                }
            }
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    public int getSelectedItemsCount() {
        int counter = 0;
        for (int i = 0; i < projectSelectionBackgroundFlags.size(); i++)
            if (projectSelectionBackgroundFlags.get(i))
                counter++;
        return counter;
    }

    @Override
    public Project getItem(int i) {
        return projects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        viewHolder = new ViewHolder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.project_selection_list_item, null);

            viewHolder.projectListItemLayout = (RelativeLayout) view
                    .findViewById(R.id.project_list_item_relative_layout);
            viewHolder.projectPlayButton = (ImageButton) view
                    .findViewById(R.id.project_play_button);
            viewHolder.projectPauseButton = (ImageButton) view
                    .findViewById(R.id.project_pause_button);
            viewHolder.projectNameTextView = (TextView) view
                    .findViewById(R.id.project_name_text_view);
            viewHolder.projectDurationTextView = (TextView) view
                    .findViewById(R.id.project_duration_text_view);

            view.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) view.getTag();

        initPlayPauseButtonRoutine(position);
        initTextViews(position);
        initBackgroundBehavior(position);

        return view;
    }

    private void initBackgroundBehavior(int position) {
        if (getProjectSelectionBackgroundFlags(position))
            viewHolder.projectListItemLayout.setBackgroundColor(activity.getResources().getColor(R.color.list_view_item_background_color_selected));
        else
            viewHolder.projectListItemLayout.setBackgroundColor(Color.TRANSPARENT);

    }

    private void initPlayPauseButtonRoutine(final int position) {

        if (projectSelectionTrackIsPlayingFlags.get(position)) {
            viewHolder.projectPlayButton.setVisibility(View.INVISIBLE);
            viewHolder.projectPauseButton.setVisibility(View.VISIBLE);
        } else {
            viewHolder.projectPlayButton.setVisibility(View.VISIBLE);
            viewHolder.projectPauseButton.setVisibility(View.INVISIBLE);
        }

        viewHolder.projectPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!playButtonLock) {
                    playButtonLock = true;
                    projectSelectionTrackIsPlayingFlags.set(position, true);

                    // TODO consider more tracks
                    try {
                        Project project = projects.get(position);

                        MidiPlayer.getInstance().playTrack(activity,
                                activity.getCacheDir(),
                                project.getTrack(project.getTrackNames().iterator().next()),
                                project.getBeatsPerMinute());
                    } catch (IOException | MidiException e) {
                        ErrorDialog.createDialog(R.string.dialog_open_midi_error, e).show(activity.getFragmentManager(), "tag");
                    }
                }

                notifyDataSetChanged();
            }
        });

        viewHolder.projectPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                projectSelectionTrackIsPlayingFlags.set(position, false);
                playButtonLock = false;
                MidiPlayer.getInstance().stop();
                notifyDataSetChanged();
            }

        });
    }

    private void initTextViews(int actualPosition) {
        viewHolder.projectNameTextView.setText(activity.getResources().getText(R.string.project_name)
                + projects.get(actualPosition).getName());
        viewHolder.projectDurationTextView.setText(getDurationTextViewText(actualPosition));
    }

    private String getDurationTextViewText(int actualPosition) {
        long timeInMilliseconds = projects.get(actualPosition).getTotalTimeInMilliseconds();
        return activity.getResources().getText(R.string.duration)
                + String.format("%02d", (timeInMilliseconds / 1000) / 60)
                + activity.getResources().getString(R.string.minutes_short) + " "
                + String.format("%02d", timeInMilliseconds / 1000)
                + activity.getResources().getString(R.string.seconds_short);
    }

    public void changePlayPauseButtonState() {
        playButtonLock = false;
        for (int i = 0; i < projectSelectionTrackIsPlayingFlags.size(); i++)
            projectSelectionTrackIsPlayingFlags.set(i, false);
        notifyDataSetChanged();
    }

    public void clearProjectSelectionBackgroundStatus() {
        for (int i = 0; i < projects.size(); i++)
            projectSelectionBackgroundFlags.set(i, false);
    }

    public boolean getProjectSelectionBackgroundFlags(int position) {
        return projectSelectionBackgroundFlags.get(position);
    }

    public void setProjectSelectionBackgroundFlags(int position) {
        if (projectSelectionBackgroundFlags.get(position))
            this.projectSelectionBackgroundFlags.set(position, false);
        else
            this.projectSelectionBackgroundFlags.set(position, true);

        notifyDataSetChanged();
    }

    public int getProjectSelectionSelectedItemsCount() {
        int counter = 0;
        for (int i = 0; i < projectSelectionBackgroundFlags.size(); i++)
            if (projectSelectionBackgroundFlags.get(i)) {
                counter++;
            }
        return counter;
    }

    public Project getSelectedProject() {
        for (int i = 0; i < projectSelectionBackgroundFlags.size(); i++) {
            if (projectSelectionBackgroundFlags.get(i)) {
                return projects.get(i);
            }
        }

        return null;
    }
}
