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
import org.catrobat.musicdroid.pocketmusic.note.Octave;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiPlayer;
import org.catrobat.musicdroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.note.symbol.BreakSymbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.NoteEventsToSymbolsConverter;
import org.catrobat.musicdroid.pocketmusic.note.symbol.Symbol;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainer;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolContainerToTrackConverter;
import org.catrobat.musicdroid.pocketmusic.note.symbol.SymbolMementoStack;
import org.catrobat.musicdroid.pocketmusic.note.symbol.TrackToSymbolContainerConverter;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.SaveProjectDialog;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class InstrumentActivity extends FragmentActivity {

    public static boolean inCallback = false;

    public static final int MAX_SYMBOLS_SIZE = 60;

    private static final String SAVED_INSTANCE_OCTAVE = "SavedOctave";
    private static final String SAVED_INSTANCE_SYMBOLS = "SavedSymbols";
    private static final String SAVED_INSTANCE_PROJECT = "SavedProject";
    private static final String SAVED_INSTANCE_MEMENTO = "SavedMemento";

    private Octave octave;
    private int beatsPerMinute;
    private MidiPlayer midiPlayer;
    private Project project;
    private SymbolContainer symbolContainer;
    private NoteEventsToSymbolsConverter noteEventsConverter;
    private TickProvider tickProvider;
    private SymbolMementoStack mementoStack;

    private boolean activityInFocus = false;

    public InstrumentActivity(MusicalKey key, MusicalInstrument instrument) {
        // TODO fw consider other BPM
        octave = Octave.DEFAULT_OCTAVE;
        beatsPerMinute = Project.DEFAULT_BEATS_PER_MINUTE;
        midiPlayer = MidiPlayer.getInstance();
        project = null;
        symbolContainer = new SymbolContainer(key, instrument);
        noteEventsConverter = new NoteEventsToSymbolsConverter();
        tickProvider = new TickProvider(beatsPerMinute);
        mementoStack = new SymbolMementoStack();
    }

    public Octave getOctave() {
        return octave;
    }

    public void setOctave(Octave octave) {
        this.octave = octave;
    }

    public SymbolContainer getSymbolContainer() {
        return symbolContainer;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            octave = (Octave) savedInstanceState.getSerializable(SAVED_INSTANCE_OCTAVE);
            symbolContainer = (SymbolContainer) savedInstanceState.getSerializable(SAVED_INSTANCE_SYMBOLS);
            project = (Project) savedInstanceState.getSerializable(SAVED_INSTANCE_PROJECT);
            mementoStack = (SymbolMementoStack) savedInstanceState.getSerializable(SAVED_INSTANCE_MEMENTO);

            if (null != project) {
                setTitle(project.getName());
            }
        }

        handleExtras();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable(SAVED_INSTANCE_OCTAVE, octave);
        savedInstanceState.putSerializable(SAVED_INSTANCE_SYMBOLS, symbolContainer);
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

    public MidiPlayer getMidiPlayer() {
        return midiPlayer;
    }

    public void addNoteEvent(NoteEvent noteEvent) {
        if (symbolContainer.size() >= MAX_SYMBOLS_SIZE) {
            return;
        }

        if (noteEvent.isNoteOn()) {
            tickProvider.startCounting();
        } else {
            tickProvider.stopCounting();
        }

        List<Symbol> symbols = noteEventsConverter.convertNoteEvent(tickProvider.getTick(), noteEvent, beatsPerMinute);

        if (0 < symbols.size()) {
            mementoStack.pushMemento(symbolContainer);
        }

        if (inCallback && (false == noteEvent.isNoteOn()) && (0 < symbols.size())) {
            symbolContainer.replaceMarkedSymbols(symbols.get(0));
        } else {
            symbolContainer.addAll(symbols);
        }

        redraw();
    }

    public void addBreak(BreakSymbol breakSymbol) {
        if (symbolContainer.size() >= MAX_SYMBOLS_SIZE) {
            return;
        }

        mementoStack.pushMemento(symbolContainer);

        if (inCallback) {
            symbolContainer.replaceMarkedSymbols(breakSymbol);
        } else {
            symbolContainer.add(breakSymbol);
        }

        redraw();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        midiPlayer.stop();

        if (id == R.id.action_save_midi) {
            onActionSave();
            return true;
        } else if (id == R.id.action_undo_midi) {
            onActionUndo();
            return true;
        } else if (id == R.id.action_clear_midi) {
            onActionClear();
            return true;
        } else if (id == R.id.action_play_and_stop_midi) {
            if (!getMidiPlayer().isPlaying()) {
                item.setIcon(R.drawable.ic_action_stop);
                item.setTitle(R.string.menu_stop);
                onActionPlay();
            } else {
                item.setIcon(R.drawable.ic_action_play);
                item.setTitle(R.string.menu_play);
                onActionStop();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onActionSave() {
        saveMidiFileByUserInput();
    }

    private void onActionUndo() {
        if (false == mementoStack.isEmpty()) {
            symbolContainer = mementoStack.popMemento();
            redraw();
        }
    }

    private void onActionClear() {
        mementoStack.pushMemento(symbolContainer);
        symbolContainer.clear();
        redraw();

        Toast.makeText(getBaseContext(), R.string.clear_success, Toast.LENGTH_LONG).show();
    }

    private void onActionPlay() {
        if (symbolContainer.size() == 0) {
            return;
        }

        try {
            SymbolContainerToTrackConverter converter = new SymbolContainerToTrackConverter();
            midiPlayer.playTrack(this, getCacheDir(), converter.convertSymbols(symbolContainer, beatsPerMinute), beatsPerMinute);
            ToastDisplayer.showPlayToast(getBaseContext());
        } catch (Exception e) {
            ErrorDialog.createDialog(R.string.dialog_play_error, e).show(getFragmentManager(), "tag");
        }
    }

    private void onActionStop() {
        midiPlayer.stop();
        ToastDisplayer.showStopToast(getBaseContext());
    }

    private void saveMidiFileByUserInput() {
        if (null != project) {
            ProjectToMidiConverter converter = new ProjectToMidiConverter();

            try {
                // TODO fw refactor for several tracks, consider the name for the track.
                SymbolContainerToTrackConverter symbolsConverter = new SymbolContainerToTrackConverter();
                project.addTrack("changeThisName", symbolsConverter.convertSymbols(symbolContainer, beatsPerMinute));
                converter.writeProjectAsMidi(project);
                Toast.makeText(getBaseContext(), R.string.save_success, Toast.LENGTH_LONG).show();
            } catch (MidiException e) {
                ErrorDialog.createDialog(R.string.dialog_save_error, e).show(getFragmentManager(), "tag");
            } catch (IOException e) {
                ErrorDialog.createDialog(R.string.dialog_name_error, e).show(getFragmentManager(), "tag");
            }
        } else {
            Bundle args = new Bundle();
            args.putSerializable(SaveProjectDialog.ARGUMENT_SYMBOLS, symbolContainer);
            args.putInt(SaveProjectDialog.ARGUMENTS_BPM, beatsPerMinute);
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
                MidiToProjectConverter midiConverter = new MidiToProjectConverter();
                File midiFile = new File(ProjectToMidiConverter.MIDI_FOLDER,
                        projectName + ProjectToMidiConverter.MIDI_FILE_EXTENSION);
                setTitle(projectName);

                try {
                    //TODO fw consider more tracks
                    project = midiConverter.convertMidiFileToProject(midiFile);
                    Track track = project.getTrack(project.getTrackNames().iterator().next());
                    TrackToSymbolContainerConverter trackConverter = new TrackToSymbolContainerConverter();
                    symbolContainer = trackConverter.convertTrack(track, beatsPerMinute);
                } catch (MidiException | IOException e) {
                    ErrorDialog.createDialog(R.string.dialog_open_midi_error, e).show(getFragmentManager(), "tag");
                }
                getIntent().removeExtra(ProjectSelectionActivity.INTENT_EXTRA_FILE_NAME);
            }
        }
    }

    public void deleteMarkedSymbols() {
        mementoStack.pushMemento(symbolContainer);
        symbolContainer.deleteMarkedSymbols();
        symbolContainer.resetSymbolMarkers();
    }

    protected abstract void redraw();
}
