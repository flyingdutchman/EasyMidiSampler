package ch.flyingdutchman.model;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 * Voice Message           Status Byte      Data Byte1          Data Byte2
 * -------------           -----------   -----------------   -----------------
 * Note off                      8x      Key number          Note Off velocity
 * Note on                       9x      Key number          Note on velocity
 * Polyphonic Key Pressure       Ax      Key number          Amount of pressure
 * Control Change                Bx      Controller number   Controller value
 * Program Change                Cx      Program number      None
 * Channel Pressure              Dx      Pressure value      None
 * Pitch Bend                    Ex      MSB                 LSB
 *
 * Notes: `x' in status byte hex value stands for a channel number.
 */
public class CustomReceiver implements Receiver {
    @Override
    public void send(MidiMessage message, long timeStamp) {
        printMidiMessage(message);
    }

    @Override
    public void close() {
        System.out.println("Receiver closed");
    }

    private void printMidiMessage(MidiMessage midiMessage) {
        byte[] message = midiMessage.getMessage();
        int status = (int) (midiMessage.getStatus() & 0xFF);
        int type = (status & 0xF0) >> 4;
        int channel = status & 0xF;

        switch (type) {
            case 0x8 : System.out.println("Note off,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Key Number : "+message[1]
                    +" Velocity : "+message[2]);
            break;
            case 0x9 : System.out.println("Note on,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Key Number : "+message[1]
                    +" Velocity : "+message[2]);
            break;
            case 0xA : System.out.println("Polyphonic Key Pressure,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Key Number : "+message[1]
                    +" Amount of pressure : "+message[2]);
            break;
            case 0xB : System.out.println("Control Change,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Controller number : "+message[1]
                    +" Controller value : "+message[2]);
            break;
            case 0xC : System.out.println("Program Change,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Program number : "+message[1]);
            break;
            case 0xD : System.out.println("Channel Pressure,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" Pitch Bend : "+message[1]);
            break;
            case 0xE : System.out.println("Pitch Bend,"
                    +" Channel : " + Integer.toHexString(channel)
                    +" MSB : "+message[1]
                    +" LSB : "+message[2]);
            break;
        }
    }
}
