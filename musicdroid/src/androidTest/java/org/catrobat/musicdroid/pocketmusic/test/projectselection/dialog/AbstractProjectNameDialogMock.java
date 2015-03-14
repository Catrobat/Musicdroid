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

package org.catrobat.musicdroid.pocketmusic.test.projectselection.dialog;

import android.app.Dialog;
import android.os.Bundle;

import org.catrobat.musicdroid.pocketmusic.note.midi.MidiException;
import org.catrobat.musicdroid.pocketmusic.projectselection.dialog.AbstractProjectNameDialog;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class AbstractProjectNameDialogMock extends AbstractProjectNameDialog {

    public static final int TITLE_ID = 0;
    public static final int MESSAGE_ID = 1;
    public static final int SUCCESS_MESSAGE_ID = 2;
    public static final int ERROR_MESSAGE_ID = 3;
    public static final int CANCEL_MESSAGE_ID = 4;

    private String userInput;
    private boolean projectExists;
    private boolean onDialogCreationCalled;
    private boolean onNewProjectNameCalled;
    private Queue<String> toastTexts;

    public AbstractProjectNameDialogMock() {
        super(TITLE_ID, MESSAGE_ID, SUCCESS_MESSAGE_ID, ERROR_MESSAGE_ID, CANCEL_MESSAGE_ID);
        userInput = "";
        projectExists = false;
        onNewProjectNameCalled = false;
        toastTexts = new LinkedList<String>();
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public void setProjectExists(boolean projectExists) {
        this.projectExists = projectExists;
    }

    public boolean isOnDialogCreationCalled() {
        return onDialogCreationCalled;
    }

    public boolean isOnNewProjectNameCalled() {
        return onNewProjectNameCalled;
    }

    public String pollNextToastText() {
        return toastTexts.poll();
    }

    public String toToastText(int resId, int toastLength) {
        return resId + " " + toastLength;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initDialog();

        return null;
    }

    @Override
    protected void initDialog() {
        onDialogCreationCalled = true;
    }

    @Override
    protected void onNewProjectName(String name) throws IOException, MidiException {
        onNewProjectNameCalled = true;
    }

    @Override
    protected void updateActivity() {
    }

    @Override
    public void onPositiveButton() {
        super.onPositiveButton();
    }

    @Override
    public void onNegativeButton() {
        super.onNegativeButton();
    }

    @Override
    protected boolean projectNameExists(String projectName) throws IOException {
        return projectExists;
    }

    @Override
    protected String getUserInput() {
        return userInput;
    }

    @Override
    protected void makeToastText(int resId, int toastLength) {
        toastTexts.add(toToastText(resId, toastLength));
    }
}
