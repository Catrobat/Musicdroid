/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.musicdroid.pocketmusic.note.symbol;

import org.catrobat.musicdroid.pocketmusic.note.draw.SymbolPosition;

public abstract class Symbol {

    protected boolean marked;
    protected SymbolPosition symbolPosition;

    public Symbol() {
        marked = false;
        symbolPosition = null;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    // TODO test
    public SymbolPosition getSymbolPosition() {
        return symbolPosition;
    }

    // TODO test
    public void setSymbolPosition(SymbolPosition symbolPosition) {
        this.symbolPosition = symbolPosition;
    }

    @Override
    //TODO test symbol pos
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof Symbol)) {
            return false;
        }

        Symbol symbol = (Symbol) obj;

        if (symbol.isMarked() == isMarked()) {
            return true;
        }

        return false;
    }
}
