package ch.flyingdutchman.model;

import javax.sound.midi.MidiDevice;

/**
 * This class represent the current state of the program.
 * It tracks whether it has been recently modified.
 */
public class State {
    private Preset currentPreset;
    private MidiDevice midiDevice;
    private boolean unSaved;

    /**
     * Constructs a new state with the given preset
     *
     * @param preset the preset loaded into the state
     */
    public State(Preset preset) {
        currentPreset = preset;
        unSaved = false;
    }

    /**
     * Set the currently used MIDI device.
     *
     * @param midiDevice the midiDevice parameter, may be null
     */
    public void setMidiDevice(MidiDevice midiDevice) {
        this.midiDevice = midiDevice;
    }

    /**
     * Return whether or not the state is in a unsaved status
     *
     * @return State save status
     */
    public boolean isUnSaved() {
        return unSaved;
    }
}
