package org.catrobat.musicdroid.pocketmusic;

public class NoteEvent {

    private NoteName noteName;
    private boolean noteOn;

    public NoteEvent(NoteName noteName, boolean noteOn) {
        this.noteName = noteName;
        this.noteOn = noteOn;
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
