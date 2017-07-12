package ch.flyingdutchman.view;

import ch.flyingdutchman.model.MidiMap;

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

    /**
     *
     * @return new map or null if nothing changed
     */
    public static MidiMap showMapEditDialog() {

        MidiMap result;
        MapEditPanel editView = new MapEditPanel();
        int input = JOptionPane.showConfirmDialog(null, editView, "New Mapping", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        result = (input == JOptionPane.OK_OPTION) ? editView.getInput() : null;

        return result;
    }

    /**
     *
     * @param midiMap
     * @return new map or original
     */
    public static MidiMap showMapEditDialog(MidiMap midiMap) {

        MidiMap result;
        MapEditPanel editView = new MapEditPanel(midiMap);
        int input = JOptionPane.showConfirmDialog(null, editView, "Edit Mapping", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        result = (input == JOptionPane.OK_OPTION) ? editView.getInput() : midiMap;

        return result;
    }
}
