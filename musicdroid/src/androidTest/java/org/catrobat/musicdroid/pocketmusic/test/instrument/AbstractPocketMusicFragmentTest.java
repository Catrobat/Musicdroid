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

package org.catrobat.musicdroid.pocketmusic.test.instrument;

import android.test.AndroidTestCase;

public class AbstractPocketMusicFragmentTest extends AndroidTestCase {

    AbstractPocketMusicFragmentMock abstractPocketMusicFragment;

    private static final int MIN_WIDTH = 240;
    private static final int MAX_WIDTH = 2000;

    private static final int MIN_HEIGHT = 240;
    private static final int MAX_HEIGHT = 2000;

    @Override
    protected void setUp() {
        abstractPocketMusicFragment = new AbstractPocketMusicFragmentMock();
    }

    public void testGetDisplayWidth() {
        assertTrue(abstractPocketMusicFragment.getDisplayWidth() > MIN_WIDTH);
        assertTrue(abstractPocketMusicFragment.getDisplayWidth() < MAX_WIDTH);
    }

    public void testGetDisplayHeight() {
        assertTrue(abstractPocketMusicFragment.getDisplayHeight() > MIN_HEIGHT);
        assertTrue(abstractPocketMusicFragment.getDisplayHeight() < MAX_HEIGHT);
    }
}
