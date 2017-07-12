package ch.flyingdutchman.model;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.sampled.Clip;
import java.util.Vector;

/**
 *
 */
public class CustomReceiver implements Receiver {

    Vector<MidiMap> maps;

    public CustomReceiver(Vector<MidiMap> maps) {
        super();
        if(maps == null) {
            throw new IllegalArgumentException();
        }
        this.maps = maps;
    }

    @Override
    public void send(MidiMessage midiMessage, long timeStamp) {

        printMidiMessage(midiMessage);

        byte[] message = midiMessage.getMessage();
        int status = midiMessage.getStatus() & 0xFF;
        int type = status & 0xF0;

        if(type == ShortMessage.NOTE_ON) {
            for(MidiMap m : maps) {
                if(message[1] == m.getKeyNumber()) {
                    Clip clip = m.getClip();
                    clip.setMicrosecondPosition(0);
                    System.out.println(clip.getMicrosecondPosition()+" / "+clip.getMicrosecondLength());
                    clip.start();
                    System.out.println("Horn");
                }
            }
        }
    }

    @Override
    public void close() {
        System.out.println("CustomReceiver closed");
    }

    /**
     * Prints in the console in a readable way the midi message given in parameter
     *
     * @param midiMessage the printed message
     */
    private void printMidiMessage(MidiMessage midiMessage) {
        if(midiMessage == null) {
            throw new IllegalArgumentException();
        }
        byte[] message = midiMessage.getMessage();
        int status = midiMessage.getStatus() & 0xFF;
        int type = status & 0xF0;
        int channel = status & 0xF;

        switch (type) {
            case ShortMessage.NOTE_OFF : System.out.println("Note off,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Key Number : "+message[1]
                    +" Velocity : "+message[2]);
            break;
            case ShortMessage.NOTE_ON : System.out.println("Note on,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Key Number : "+message[1]
                    +" Velocity : "+message[2]);
            break;
            case ShortMessage.POLY_PRESSURE : System.out.println("Polyphonic Key Pressure,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Key Number : "+message[1]
                    +" Amount of pressure : "+message[2]);
            break;
            case ShortMessage.CONTROL_CHANGE : System.out.println("Control Change,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Controller number : "+message[1]
                    +" Controller value : "+message[2]);
            break;
            case ShortMessage.PROGRAM_CHANGE : System.out.println("Program Change,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Program number : "+message[1]);
            break;
            case ShortMessage.CHANNEL_PRESSURE : System.out.println("Channel Pressure,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Pitch Bend : "+message[1]);
            break;
            case ShortMessage.PITCH_BEND : System.out.println("Pitch Bend,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" MSB : "+message[1]
                    +" LSB : "+message[2]);
            break;
        }
    }
}
