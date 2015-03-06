/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.instrument;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;
import org.catrobat.musicdroid.pocketmusic.ToastDisplayer;
import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;
import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.TrackMementoStack;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolsToTrackConverter;
import org.catrobat.musicdroid.pocketmusic.note.symbol.TrackToSymbolsConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.SaveProjectDialog;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public abstract class InstrumentActivity extends FragmentActivity {

    public static final int MAX_TRACK_SIZE_IN_SYMBOLS = 60;

    private static final String R_RAW = "raw";
    private static final String SAVED_INSTANCE_TRACK = "SavedTrack";
    private static final String SAVED_INSTANCE_MEMENTO = "SavedMemento";
    private static final String SAVED_INSTANCE_PROJECT = "SavedProject";

    private MidiPlayer midiPlayer;
    private Project project;
    private Track track;
    private List<Symbol> symbols;
    private TrackToSymbolsConverter trackConverter;
    private TickProvider tickProvider;
    private TrackMementoStack mementoStack;

    private boolean activityInFocus = false;

    public InstrumentActivity(MusicalKey key, MusicalInstrument instrument) {
        midiPlayer = MidiPlayer.getInstance();

        project = null;
        track = new Track(key, instrument, Project.DEFAULT_BEATS_PER_MINUTE);
        symbols = new LinkedList<Symbol>();
        trackConverter = new TrackToSymbolsConverter();
        tickProvider = new TickProvider(track.getBeatsPerMinute());

        mementoStack = new TrackMementoStack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            setTrack((Track) savedInstanceState.getSerializable(SAVED_INSTANCE_TRACK));
            mementoStack = (TrackMementoStack) savedInstanceState.getSerializable(SAVED_INSTANCE_MEMENTO);
            project = (Project) savedInstanceState.getSerializable(SAVED_INSTANCE_PROJECT);

            if (null != project) {
                setTitle(project.getName());
            }
        }

        handleExtras();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable(SAVED_INSTANCE_TRACK, track);
        savedInstanceState.putSerializable(SAVED_INSTANCE_MEMENTO, mementoStack);

        if (null != project) {
            savedInstanceState.putSerializable(SAVED_INSTANCE_PROJECT, project);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        midiPlayer.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setTrack(Track track) {
        this.track = track;
        tickProvider.setTickBasedOnTrack(track);

        symbols = trackConverter.convertTrack(track);
    }

    public void pushMemento(Track track) {
        mementoStack.pushMemento(track);
    }

    public Track getTrack() {
        return track;
    }

    public List<Symbol> getSymbols() { return symbols; }

    public MidiPlayer getMidiPlayer() {
        return midiPlayer;
    }

    public void addNoteEvent(NoteEvent noteEvent) {
        if (symbols.size() >= MAX_TRACK_SIZE_IN_SYMBOLS) {
            return;
        }

        if (noteEvent.isNoteOn()) {
            mementoStack.pushMemento(track);

            int midiResourceId = getResources().getIdentifier(noteEvent.getNoteName().toString().toLowerCase(Locale.getDefault()), R_RAW, getPackageName());
            midiPlayer.playNote(this, midiResourceId);
            tickProvider.startCounting();
        } else {
            tickProvider.stopCounting();
        }

        track.addNoteEvent(tickProvider.getTick(), noteEvent);
        symbols = trackConverter.convertTrack(track);
        redraw();
    }

    public void addBreak(BreakSymbol breakSymbol) {
        if (symbols.size() >= MAX_TRACK_SIZE_IN_SYMBOLS) {
            return;
        }

        mementoStack.pushMemento(track);
        symbols.add(breakSymbol);
        redraw();

        SymbolsToTrackConverter converter = new SymbolsToTrackConverter();

        Track newTrack = converter.convertSymbols(symbols, track.getKey(), track.getInstrument(), track.getBeatsPerMinute());

        track = newTrack;
        tickProvider.increaseTickByBreak(breakSymbol);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        midiPlayer.stop();

        if (id == R.id.action_save_midi) {
            onActionSaveMidi();
            return true;
        } else if (id == R.id.action_undo_midi) {
            onActionUndoMidi();
            return true;
        } else if (id == R.id.action_clear_midi) {
            onActionDeleteMidi();
            return true;
        } else if (id == R.id.action_play_and_stop_midi) {
            if(!getMidiPlayer().isPlaying()) {
                item.setIcon(R.drawable.ic_action_stop);
                item.setTitle(R.string.action_stop_midi);
                onActionPlayMidi();
            } else {
                item.setIcon(R.drawable.ic_action_play);
                item.setTitle(R.string.action_play_midi);
                onActionStopMidi();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onActionSaveMidi() {
        saveMidiFileByUserInput();
    }

    private void onActionUndoMidi() {
        if (false == mementoStack.isEmpty()) {
            setTrack(mementoStack.popMementoAsTrack());
            redraw();
        }
    }

    private void onActionDeleteMidi() {
        setTrack(new Track(track.getKey(), track.getInstrument(), track.getBeatsPerMinute()));
        mementoStack.clear();
        redraw();

        Toast.makeText(getBaseContext(), R.string.action_delete_midi_success, Toast.LENGTH_LONG).show();
    }

    private void onActionPlayMidi() {
        if (track.empty()) {
            return;
        }

        try {
            midiPlayer.playTrack(this, getCacheDir(), track, Project.DEFAULT_BEATS_PER_MINUTE);
            ToastDisplayer.showPlayToast(getBaseContext());
        } catch (Exception e) {
            ErrorDialog.createDialog(R.string.action_play_midi_error, e).show(getFragmentManager(), "tag");
        }
    }

    private void onActionStopMidi() {
        midiPlayer.stop();
        ToastDisplayer.showStopToast(getBaseContext());
    }

    private void saveMidiFileByUserInput() {
        if (null != project) {
            ProjectToMidiConverter converter = new ProjectToMidiConverter();

            try {
                converter.writeProjectAsMidi(project);
                Toast.makeText(getBaseContext(), R.string.dialog_project_save_success, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                ErrorDialog.createDialog(R.string.dialog_project_name_exists_error, e).show(getFragmentManager(), "tag");
            }
        } else {
            Bundle args = new Bundle();
            args.putSerializable(SaveProjectDialog.ARGUMENT_TRACK, getTrack());
            SaveProjectDialog dialog = new SaveProjectDialog();
            dialog.setArguments(args);
            dialog.show(getFragmentManager(), "tag");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        activityInFocus = hasFocus;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!activityInFocus) {
            midiPlayer.stop();
            midiPlayer.clearPlayQueue();
        }
    }


    private void handleExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME)) {
                final String projectName = extras.getString(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME);
                MidiToProjectConverter converter = new MidiToProjectConverter();
                File midiFile = new File(ProjectToMidiConverter.MIDI_FOLDER,
                        projectName + ProjectToMidiConverter.MIDI_FILE_EXTENSION);
                setTitle(projectName);

                try {
                    project = converter.convertMidiFileToProject(midiFile);
                    //TODO: consider more tracks
                    setTrack(project.getTrack(0));
                } catch (MidiException | IOException e) {
                    ErrorDialog.createDialog(R.string.midi_open, e).show(getFragmentManager(), "tag");
                }
                getIntent().removeExtra(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME);
            }
        }
    }

    protected abstract void redraw();
}
