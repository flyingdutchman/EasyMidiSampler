package ch.flyingdutchman.model;

import java.util.Vector;

/**
 * Represents the savable characteristics of the software. It contains the list of
 * mappings between song and MIDI.
 */
public class Preset {
    private Vector<MidiMap> mapping;

    /**
     * Constructs a new Preset that represents the savable characteristics of the software
     */
    public Preset() {
        mapping = new Vector<>();
    }

    /**
     *
     *
     * @return
     */
    public Vector<MidiMap> getMapping() {
        return mapping;
    }
}
