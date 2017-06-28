package ch.flyingdutchman.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a preset of the program. It contains the list of
 * mappings between song and MIDI.
 */
public class Preset {
    List<MidiMap> mapping;

    public Preset() {
        mapping = new ArrayList<>();
    }
}
