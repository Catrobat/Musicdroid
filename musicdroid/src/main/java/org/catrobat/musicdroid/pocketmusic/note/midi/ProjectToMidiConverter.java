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
package org.catrobat.musicdroid.pocketmusic.note.midi;

import android.os.Environment;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.ChannelEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.Text;
import com.leff.midi.event.meta.TimeSignature;

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.NoteEvent;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.note.Track;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectToMidiConverter {

    public static final String MIDI_FILE_EXTENSION = ".midi";
	public static final String MIDI_FILE_IDENTIFIER = "Musicdroid Midi File";
    public static final File MIDI_FOLDER = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "musicdroid");

	private static final int MAX_CHANNEL = 16;

	private NoteEventToMidiEventConverter eventConverter;
	private ArrayList<MusicalInstrument> usedChannels;

	public ProjectToMidiConverter() {
		eventConverter = new NoteEventToMidiEventConverter();
		usedChannels = new ArrayList<MusicalInstrument>();
	}
    
	public void writeProjectAsMidi(Project project, String filename) throws IOException, MidiException {
		MidiFile midi = convertProject(project);

        if (!MIDI_FOLDER.exists()) {
            boolean success = MIDI_FOLDER.mkdir();

            if (!success) {
                throw new IOException("Could not create folder: " + MIDI_FOLDER);
            }
        }

		File file = new File(MIDI_FOLDER + File.separator + filename + MIDI_FILE_EXTENSION);

		midi.writeToFile(file);
	}

    public void writeProjectAsMidi(Project project, File file) throws IOException, MidiException {
        MidiFile midi = convertProject(project);

        midi.writeToFile(file);
    }

	private MidiFile convertProject(Project project) throws MidiException {
        for (int i = 0; i < project.size(); i++) {
            Track track = project.getTrack(i);

            if (0 == track.size()) {
                throw new MidiException("Cannot save a project with an empty track!");
            }
        }

		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();

		MidiTrack tempoTrack = createTempoTrackWithMetaInfo(project.getBeatsPerMinute());
		tracks.add(tempoTrack);

		for (int i = 0; i < project.size(); i++) {
			Track track = project.getTrack(i);
			int channel = addInstrumentAndGetChannel(track.getInstrument());

			MidiTrack noteTrack = createNoteTrack(track, channel);

			tracks.add(noteTrack);
		}

		return new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
	}

    private int addInstrumentAndGetChannel(MusicalInstrument instrument) throws MidiException {
		if (usedChannels.contains(instrument)) {
			return usedChannels.indexOf(instrument) + 1;
		} else if (usedChannels.size() == MAX_CHANNEL) {
			throw new MidiException("You cannot have more than " + MAX_CHANNEL + " channels!");
		} else {
			usedChannels.add(instrument);

			return usedChannels.indexOf(instrument) + 1;
		}
	}

	private MidiTrack createTempoTrackWithMetaInfo(int beatsPerMinute) {
		MidiTrack tempoTrack = new MidiTrack();

		Text text = new Text(0, 0, MIDI_FILE_IDENTIFIER);
		tempoTrack.insertEvent(text);

		Tempo tempo = new Tempo();
		tempo.setBpm(beatsPerMinute);
		tempoTrack.insertEvent(tempo);

		TimeSignature timeSignature = new TimeSignature();
		timeSignature.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);
		tempoTrack.insertEvent(timeSignature);

		return tempoTrack;
	}

	private MidiTrack createNoteTrack(Track track, int channel) throws MidiException {
		MidiTrack noteTrack = new MidiTrack();

		ProgramChange program = new ProgramChange(0, channel, track.getInstrument().getProgram());
		noteTrack.insertEvent(program);

		for (long tick : track.getSortedTicks()) {
			List<NoteEvent> noteEventList = track.getNoteEventsForTick(tick);

			for (NoteEvent noteEvent : noteEventList) {
				ChannelEvent channelEvent = eventConverter.convertNoteEvent(tick, noteEvent, channel);
				noteTrack.insertEvent(channelEvent);
			}
		}

		return noteTrack;
	}
}
