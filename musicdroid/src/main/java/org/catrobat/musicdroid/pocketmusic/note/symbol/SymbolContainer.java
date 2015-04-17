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

package org.catrobat.musicdroid.pocketmusic.note.symbol;

import org.catrobat.musicdroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.musicdroid.pocketmusic.note.MusicalKey;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class SymbolContainer implements Serializable {

    private static final long serialVersionUID = 7483012569872527915L;

    private MusicalKey key;
    private MusicalInstrument instrument;
    private LinkedList<Symbol> symbols;

    public SymbolContainer(MusicalKey key, MusicalInstrument instrument) {
        this.key = key;
        this.instrument = instrument;

        symbols = new LinkedList<>();
    }

    public SymbolContainer(SymbolContainer symbolContainer) {
        key = symbolContainer.getKey();
        instrument = symbolContainer.getInstrument();

        symbols = new LinkedList<>();

        for (int i = 0; i < symbolContainer.size(); i++) {
            Symbol symbol = symbolContainer.get(i);

            if (symbol instanceof NoteSymbol) {
                symbols.add(new NoteSymbol((NoteSymbol) symbol));
            } else {
                symbols.add(new BreakSymbol((BreakSymbol) symbol));
            }
        }
    }

    public MusicalKey getKey() {
        return key;
    }

    public MusicalInstrument getInstrument() {
        return instrument;
    }

    public int size() {
        return symbols.size();
    }

    public void add(Symbol symbol) {
        symbols.add(symbol);
    }

    public void addAll(List<Symbol> symbols) {
        this.symbols.addAll(symbols);
    }

    public void addAll(SymbolContainer symbolContainer) {
        addAll(symbolContainer.symbols);
    }

    public void removeAll(SymbolContainer symbolContainer) {
        this.symbols.removeAll(symbolContainer.symbols);
    }

    public void clear() {
        symbols.clear();
    }

    public Symbol get(int index) {
        return symbols.get(index);
    }

    public int getMarkedSymbolCount() {
        int count = 0;

        for (Symbol symbol : symbols) {
            if (symbol.isMarked()) {
                count++;
            }
        }

        return count;
    }

    public void deleteMarkedSymbols() {
        List<Integer> deletedIndices = new LinkedList<Integer>();

        for (int i = 0; i < symbols.size(); i++) {
            Symbol symbol = symbols.get(i);

            if (symbol.isMarked()) {
                deletedIndices.add(i);
            }
        }

        for (int i = deletedIndices.size() - 1; i >= 0; i--) {
            int index = deletedIndices.get(i);
            symbols.remove(index);
        }
    }

    public void resetSymbolMarkers() {
        for (Symbol symbol : symbols) {
            symbol.setMarked(false);
        }
    }

    public void replaceMarkedSymbols(Symbol newSymbol) {
        for (int i = 0; i < symbols.size(); i++) {
            Symbol currentSymbol = symbols.get(i);

            if (currentSymbol.isMarked()) {
                newSymbol.setMarked(true);
                symbols.set(i, newSymbol);
            }
        }
    }

    public boolean isEmpty() {
        return symbols.isEmpty();
    }

    @Override
    public String toString() {
        return "[SymbolContainer] key=" + key + " instrument=" + instrument + " size=" + size();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof SymbolContainer)) {
            return false;
        }

        SymbolContainer symbolContainer = (SymbolContainer) obj;

        if (symbolContainer.getKey() != getKey()) {
            return false;
        }

        if (symbolContainer.getInstrument() != getInstrument()) {
            return false;
        }

        if (size() == symbolContainer.size()) {
            for (int i = 0; i < size(); i++) {
                if (false == get(i).equals(symbolContainer.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
