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

package org.catrobat.musicdroid.pocketmusic.error;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import org.catrobat.musicdroid.pocketmusic.R;

public class ErrorDialog extends DialogFragment {

    public static final String ARGUMENT_ERROR_MESSAGE_ID = "ERROR_MESSAGE_ID";
    public static final String ARGUMENT_EXCEPTION = "EXCEPTION";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.error)
                .setMessage(getMessage(getString(getArguments().getInt(ARGUMENT_ERROR_MESSAGE_ID)), (Exception) getArguments().getSerializable(ARGUMENT_EXCEPTION), getString(R.string.detail)))
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
    }

    public static ErrorDialog createDialog(int errorMessageId, Exception e) {
        Bundle args = new Bundle();
        args.putInt(ErrorDialog.ARGUMENT_ERROR_MESSAGE_ID, errorMessageId);
        args.putSerializable(ErrorDialog.ARGUMENT_EXCEPTION, e);
        ErrorDialog dialog = new ErrorDialog();
        dialog.setArguments(args);

        return dialog;
    }

    protected String getMessage(String errorMessage, Exception e, String errorDetail) {
        String exceptionMessage = "";

        if (null != e) {
            if ((null != e.getMessage()) && (false == e.getMessage().isEmpty())) {
                exceptionMessage = "\n\n" + errorDetail + "\n" + e.getMessage();
            }
        }

        return errorMessage + exceptionMessage;
    }
}
