package org.catrobat.musicdroid.pocketmusic.note;

import java.io.Serializable;

public class NoteEvent implements Serializable {

    private NoteName noteName;
    private boolean noteOn;

    public NoteEvent(NoteName noteName, boolean noteOn) {
        this.noteName = noteName;
        this.noteOn = noteOn;
    }

    public NoteEvent(NoteEvent noteEvent) {
        this.noteName = noteEvent.getNoteName();
        this.noteOn = noteEvent.isNoteOn();
    }

    public NoteName getNoteName() {
        return noteName;
    }

    public boolean isNoteOn() {
        return noteOn;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof NoteEvent)) {
            return false;
        }

        NoteEvent noteEvent = (NoteEvent) obj;

        if ((noteName.equals(noteEvent.getNoteName())) && (noteOn == noteEvent.isNoteOn())) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "[NoteEvent] noteName= " + noteName + " noteOn=" + noteOn;
    }
}
