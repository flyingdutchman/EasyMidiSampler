package ch.flyingdutchman.model;

import javax.sound.midi.MidiDevice;
import java.util.Observable;
import java.util.Vector;

/**
 * This class represent the current state of the program.
 * It tracks whether it has been recently modified.
 */
public class State extends Observable {

    public static final int UPDATE_MAPPING_LIST = 0;

    private Preset currentPreset;
    private MidiDevice midiDevice;
    private boolean unSaved;

    /**
     * Constructs a new state with the given preset
     *
     * @param preset the preset loaded into the state
     */
    public State(Preset preset) {
        this.currentPreset = preset;
        unSaved = false;
    }

    public Vector<MidiMap> getMapping() {
        return currentPreset.getMapping();
    }

    public void addMapping(MidiMap midiMap) {
        currentPreset.getMapping().add(midiMap);
        unSaved = true;
        setChanged();
        notifyObservers(UPDATE_MAPPING_LIST);
    }

    public void setMapping(int index, MidiMap midiMap) {
        currentPreset.getMapping().set(index, midiMap);
        unSaved = true;
        setChanged();
        notifyObservers(UPDATE_MAPPING_LIST);
    }

    public void deleteMapping(int index) {
        currentPreset.getMapping().remove(index);
        unSaved = true;
        setChanged();
        notifyObservers(UPDATE_MAPPING_LIST);
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
     * Returns the currently used MIDI device.
     *
     * @return Current MIDI device, may be null
     */
    public MidiDevice getMidiDevice() {
        return midiDevice;
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
