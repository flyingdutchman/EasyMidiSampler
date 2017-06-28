package ch.flyingdutchman.view;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

/**
 * This class stores the custom dialogs needed throughout the program
 */
public class CustomDialogs {

    /**
     * Prompts the user for selecting a MIDI device in a list of the one who are connected
     *
     * @return the MIDI device selected by the user
     * @throws MidiUnavailableException If the selected MIDI device is not available
     */
    public static MidiDevice showGetDeviceDialog() throws MidiUnavailableException {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for(int i=0;i<infos.length;i++) {
            System.out.println("["+i+"] "+infos[i].getName() + " - " + infos[i].getDescription());
        }
        MidiDevice.Info input = (MidiDevice.Info) JOptionPane.showInputDialog(null,
                "Please select a MIDI device",
                "MIDI Device",
                JOptionPane.QUESTION_MESSAGE,
                null,
                infos,
                infos[0]
        );

        MidiDevice midiDevice = null;
        if(input != null) {
            midiDevice = MidiSystem.getMidiDevice(input);
        }

        return midiDevice;
    }
}
