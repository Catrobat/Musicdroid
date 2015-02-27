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

package org.catrobat.musicdroid.pocketmusic.test.error;

import android.os.Bundle;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.error.ErrorDialog;

public class ErrorDialogTest extends AndroidTestCase {

    public void testCreateDialog() {
        int errorMessageId = 42;
        Exception e = new Exception();
        ErrorDialog errorDialog = ErrorDialog.createDialog(errorMessageId, e);

        Bundle args = errorDialog.getArguments();

        assertEquals(errorMessageId, args.getInt(ErrorDialog.ARGUMENT_ERROR_MESSAGE_ID));
        assertEquals(e, args.getSerializable(ErrorDialog.ARGUMENT_EXCEPTION));
    }

    public void testGetMessage1() {
        assertDialogMessage("Error", null, "some unimportant text");
    }

    public void testGetMessage2() {
        assertDialogMessage("Error", new Exception(), "some unimportant text");
    }

    public void testGetMessage3() {
        assertDialogMessage("Error", new Exception(""), "some unimportant text");
    }

    public void testGetMessage4() {
        assertDialogMessage("Error", new Exception("detailed error"), "Detail:");
    }

    private void assertDialogMessage(String errorMessage, Exception e, String errorDetail) {
        ErrorDialogMock errorDialog = new ErrorDialogMock();
        String exceptionMessage = "";

        if (null != e) {
            if (null != e.getMessage() && false == e.getMessage().isEmpty()) {
                exceptionMessage = "\n\n" + errorDetail + "\n" + e.getMessage();
            }
        }

        String actualString = errorDialog.getMessage(errorMessage, e, errorDetail);

        assertEquals(errorMessage + exceptionMessage, actualString);
    }
}
