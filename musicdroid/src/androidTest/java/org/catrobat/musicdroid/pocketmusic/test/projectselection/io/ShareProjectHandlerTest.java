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

package org.catrobat.musicdroid.pocketmusic.test.projectselection.io;

import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;

import org.catrobat.musicdroid.pocketmusic.projectselection.io.ShareProjectHandler;

import java.io.File;

public class ShareProjectHandlerTest extends AndroidTestCase {

    private ShareProjectHandlerMock handler;

    @Override
    protected void setUp() {
        handler = new ShareProjectHandlerMock();
    }

    public void testOnSend() {
        File file = new File("");
        handler.onSend(file);

        Uri uri = Uri.parse(file.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/midi");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        assertEquals(intent.getType(), handler.getIntent().getType());
        assertEquals(intent.getExtras().getParcelable(Intent.EXTRA_STREAM), uri);
        assertEquals(ShareProjectHandler.SHARE_RESULT_CODE, handler.getResultCode());
    }
}
