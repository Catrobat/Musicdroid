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

package org.catrobat.musicdroid.pocketmusic.uitest.projectselection.dialog;

import android.test.AndroidTestCase;
import android.widget.Toast;

import org.catrobat.musicdroid.pocketmusic.R;

public class AbstractProjectNameDialogTest extends AndroidTestCase {

    private AbstractProjectNameDialogMock dialog;

    @Override
    protected void setUp() {
        dialog = new AbstractProjectNameDialogMock();
    }

    public void testOnPositiveButtonNullString() {
        dialog.setUserInput(null);
        dialog.onPositiveButton();

        String expectedToastText = dialog.toToastText(AbstractProjectNameDialogMock.CANCEL_MESSAGE_ID, Toast.LENGTH_LONG);
        String actualToastText = dialog.pollNextToastText();

        assertEquals(expectedToastText, actualToastText);
        assertEquals(false, dialog.isOnNewProjectNameCalled());
    }

    public void testOnPositiveButtonEmptyString() {
        dialog.setUserInput("");
        dialog.onPositiveButton();

        String expectedToastText = dialog.toToastText(AbstractProjectNameDialogMock.CANCEL_MESSAGE_ID, Toast.LENGTH_LONG);
        String actualToastText = dialog.pollNextToastText();

        assertEquals(expectedToastText, actualToastText);
        assertEquals(false, dialog.isOnNewProjectNameCalled());
    }

    public void testOnPositiveButtonFileExists() {
        dialog.setUserInput("some user input");
        dialog.setProjectExists(true);
        dialog.onPositiveButton();

        String expectedToastText = dialog.toToastText(R.string.dialog_project_name_exists_error, Toast.LENGTH_LONG);
        String actualToastText = dialog.pollNextToastText();

        assertEquals(expectedToastText, actualToastText);
        assertEquals(false, dialog.isOnNewProjectNameCalled());
    }

    public void testOnPositiveButton() {
        dialog.setUserInput("some user input");
        dialog.setProjectExists(false);
        dialog.onPositiveButton();

        String expectedToastText = dialog.toToastText(AbstractProjectNameDialogMock.SUCCESS_MESSAGE_ID, Toast.LENGTH_LONG);
        String actualToastText = dialog.pollNextToastText();

        assertEquals(expectedToastText, actualToastText);
        assertEquals(true, dialog.isOnNewProjectNameCalled());
    }

    public void testOnNegativeButton() {
        dialog.onNegativeButton();

        String expectedToastText = dialog.toToastText(AbstractProjectNameDialogMock.CANCEL_MESSAGE_ID, Toast.LENGTH_LONG);
        String actualToastText = dialog.pollNextToastText();

        assertEquals(expectedToastText, actualToastText);
        assertEquals(false, dialog.isOnNewProjectNameCalled());
    }
}
