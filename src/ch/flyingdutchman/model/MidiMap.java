package ch.flyingdutchman.model;


import javax.sound.sampled.Clip;

public class MidiMap {

    Clip sound;
    int midiData;

    public MidiMap(Clip sound, int midiData) {
        /*if(sound == null || midiData < 0 || midiData > 128) {
            throw new IllegalArgumentException();
        }*/

        this.sound = sound;
        this.midiData = midiData;
    }

    public String toString() {
        return "Cold one wih the bois";
    }
}
