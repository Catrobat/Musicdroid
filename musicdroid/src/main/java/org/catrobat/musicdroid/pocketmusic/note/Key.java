package org.catrobat.musicdroid.pocketmusic.note;

public enum Key {
    BASS(NoteName.D3), VIOLIN(NoteName.B4);

    private NoteName noteNameOnMiddleLine;

    private Key(NoteName noteNameOnMiddleLine) {
        this.noteNameOnMiddleLine = noteNameOnMiddleLine;
    }

    public NoteName getNoteNameOnMiddleLine() {
        return noteNameOnMiddleLine;
    }
}

