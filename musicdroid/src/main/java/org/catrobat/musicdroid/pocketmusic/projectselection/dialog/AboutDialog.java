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
package org.catrobat.musicdroid.pocketmusic.projectselection.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.catrobat.musicdroid.pocketmusic.R;

public class AboutDialog extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_about, null);

        TextView aboutUrlTextView = (TextView) view.findViewById(R.id.dialog_about_text_view_url);
        aboutUrlTextView.setMovementMethod(LinkMovementMethod.getInstance());

        String aboutUrl = getString(R.string.about_link_template, getResources().getString(R.string.about_catrobat_licence_link),
                getString(R.string.dialog_pocketcode_license_link_text));

        aboutUrlTextView.setText(Html.fromHtml(aboutUrl));

        TextView aboutUrlCatrobatView = (TextView) view.findViewById(R.id.dialog_about_text_catrobat_url);
        aboutUrlCatrobatView.setMovementMethod(LinkMovementMethod.getInstance());

        String aboutCatrobatUrl = getString(R.string.about_link_template, getResources().getString(R.string.about_catrobat_project_homepage),
                getString(R.string.dialog_catrobat_link_text));

        aboutUrlCatrobatView.setText(Html.fromHtml(aboutCatrobatUrl));

        TextView aboutVersionNameTextView = (TextView) view.findViewById(R.id.dialog_about_text_view_version_name);
        String versionName = this.getString(R.string.android_version_prefix) + getVersionName(getActivity());
        aboutVersionNameTextView.setText(versionName);

        Dialog aboutDialog = new AlertDialog.Builder(getActivity()).setView(view).setTitle(R.string.dialog_title)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create();
        aboutDialog.setCanceledOnTouchOutside(true);

        return aboutDialog;
    }

    public static String getVersionName(Context context) {
        String versionName = "unknown";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e(AboutDialog.class.getSimpleName(), "Name not found", nameNotFoundException);
        }
        return versionName;
    }
}
