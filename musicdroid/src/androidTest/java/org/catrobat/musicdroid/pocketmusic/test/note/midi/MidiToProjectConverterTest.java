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
package org.catrobat.musicdroid.pocketmusic.test.note.midi;

import android.os.Environment;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.midi.MidiToProjectConverter;
import org.catrobat.musicdroid.pocketmusic.midi.ProjectToMidiConverter;
import org.catrobat.musicdroid.pocketmusic.note.Project;
import org.catrobat.musicdroid.pocketmusic.test.note.ProjectTestDataFactory;

import java.io.File;
import java.io.IOException;

public class MidiToProjectConverterTest extends AndroidTestCase {

	public void testConvertMidi() throws MidiException, IOException {
        String midiFileAbsolutePath = Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + File.separator + "TestMidi.midi";
		ProjectToMidiConverter projectConverter = new ProjectToMidiConverter();
		MidiToProjectConverter midiConverter = new MidiToProjectConverter();
		Project expectedProject = ProjectTestDataFactory.createProjectWithSemiComplexTracks();

        try {
            projectConverter.convertProjectAndWriteMidi(expectedProject, midiFileAbsolutePath);
            Project actualProject = midiConverter.readFileAndConvertMidi(new File(midiFileAbsolutePath));

            assertEquals(expectedProject, actualProject);
        } catch (Exception e) {
            throw e;
        } finally {
            File file = new File(midiFileAbsolutePath);
            file.delete();
        }
	}
}
