/*
 * Musicdroid: An on-device music generator for Android
 * Copyright (C) 2010-2015 The Catrobat Team
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

package org.catrobat.musicdroid.pocketmusic.projectselection.io;

import android.content.Intent;

import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.projectselection.ProjectSelectionActivity;

import java.io.File;
import java.io.IOException;

public abstract class IOHandler {

    protected ProjectSelectionActivity projectSelectionActivity;

    protected IOHandler(ProjectSelectionActivity projectSelectionActivity) {
        this.projectSelectionActivity = projectSelectionActivity;
    }

    protected void startIntent(Intent intent, int resultCode) {
        projectSelectionActivity.startActivityForResult(intent, resultCode);
    }

    public abstract void onSend(File file);

    public abstract void onReceive(int requestCode, int resultCode, File targetFile) throws IOException, MidiException;
}
