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


import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.instrument.piano.PianoActivity;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;

import java.util.ArrayList;

public class ProjectListViewAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Project> projects;
    private ArrayList<Boolean> projectSelectionCheckBoxStatus;
    private ArrayList<Integer> projectSelectionCheckBoxVisibility;
    private MidiPlayer midiPlayer;

    static class ViewHolder {
        public ImageButton projectPlayButton;
        public ImageButton projectPauseButton;
        public TextView projectNameTextView;
        public TextView projectDurationTextView;
        public CheckBox projectSelectionCheckBox;
    }

    public ProjectListViewAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
        midiPlayer = MidiPlayer.getInstance();
        initFlags();
    }

    public void deleteItemByProjectName(String projectName){
        for(int i = 0; i < projects.size(); i++)
            if(projectName.equals(projects.get(i).getName())){
                projects.remove(i);
                projectSelectionCheckBoxStatus.remove(i);
                projectSelectionCheckBoxVisibility.remove(i);
            }
        notifyDataSetChanged();

    }

    private void initFlags() {
        this.projectSelectionCheckBoxStatus = new ArrayList<>();
        this.projectSelectionCheckBoxVisibility = new ArrayList<>();

        for (int i = 0; i < projects.size(); i++) {
            this.projectSelectionCheckBoxVisibility.add(View.INVISIBLE);
            this.projectSelectionCheckBoxStatus.add(false);
        }
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int i) {
        return projects.get(i);
    }

    public String getItemName(int i) {
        return projects.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.project_list_item, null);


            viewHolder.projectPlayButton = (ImageButton) view
                    .findViewById(R.id.project_play_button);
            viewHolder.projectPauseButton = (ImageButton) view
                    .findViewById(R.id.project_pause_button);
            viewHolder.projectNameTextView = (TextView) view
                    .findViewById(R.id.project_name_text_view);
            viewHolder.projectDurationTextView = (TextView) view
                    .findViewById(R.id.project_duration_text_view);
            viewHolder.projectSelectionCheckBox = (CheckBox) view
                    .findViewById(R.id.project_selection_check_box);

            view.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) view.getTag();

        initPlayPauseButtonRoutine(viewHolder);
        initTextViews(viewHolder, position);
        initCheckBoxBehavior(viewHolder, position);

        return view;
    }

    private void initCheckBoxBehavior(ViewHolder viewHolder, int position) {
        //noinspection ResourceType
        viewHolder.projectSelectionCheckBox.setVisibility(projectSelectionCheckBoxVisibility.get(position));
        viewHolder.projectSelectionCheckBox.setTag(position);
        viewHolder.projectSelectionCheckBox.setChecked(projectSelectionCheckBoxStatus.get(position));
        viewHolder.projectSelectionCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                int rowId = (Integer) v.getTag();
                projectSelectionCheckBoxStatus.set(rowId,checkBox.isChecked());
            }
        });
    }

    private void initTextViews(ViewHolder viewHolder, int actualPosition) {
        viewHolder.projectNameTextView.setText(context.getResources().getText(R.string.project_name) + projects.get(actualPosition).getName());
        viewHolder.projectDurationTextView.setText(context.getResources().getText(R.string.project_duration) +""+projects.get(actualPosition).getTrack(0).getTotalTimeInMilliseconds());
    }

    private void initPlayPauseButtonRoutine(ViewHolder viewHolder){
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.projectPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.projectPauseButton.setVisibility(View.VISIBLE);
                finalViewHolder.projectPlayButton.setVisibility(View.INVISIBLE);
            }
        });
        viewHolder.projectPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalViewHolder.projectPlayButton.setVisibility(View.VISIBLE);
                finalViewHolder.projectPauseButton.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void setDelMode(boolean enabled) {
        if (enabled) {
            for (int i = 0; i < projects.size(); i++)
                projectSelectionCheckBoxVisibility.set(i, View.VISIBLE);
        } else {
            for (int i = 0; i < projects.size(); i++)
                projectSelectionCheckBoxVisibility.set(i, View.INVISIBLE);

        }
        notifyDataSetChanged();
    }
    public boolean getProjectSelectionCheckBoxStatus(int position) {
        return projectSelectionCheckBoxStatus.get(position);
    }

    public void clearProjectSelectionCheckBoxStatus() {
        for (int i = 0; i < projects.size(); i++)
            projectSelectionCheckBoxStatus.set(i,false);
    }


}