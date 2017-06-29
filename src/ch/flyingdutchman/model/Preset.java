package ch.flyingdutchman.model;

import java.io.File;
import java.util.Vector;

/**
 * Represents a preset of the program. It contains the list of
 * mappings between song and MIDI.
 */
public class Preset {
    private Vector<MidiMap> mapping;

    public Preset() {
        mapping = new Vector<>();
    }

    public Vector<MidiMap> getMapping() {
        return mapping;
    }
}
