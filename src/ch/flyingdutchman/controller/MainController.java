package ch.flyingdutchman.controller;

import ch.flyingdutchman.model.*;
import ch.flyingdutchman.view.*;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.event.*;

/**
 * Controller part of the MVC
 */
public class MainController implements ActionListener, ItemListener{

    MainView mainView;
    State state;

    /**
     * The constructor of MainController takes in charge the correct initialization of both
     * its parameters and assures the connection of the actionListeners with the given mainView.
     *
     * @param mainView the mainView parameter
     * @param state    the state parameter
     */
    public MainController(MainView mainView, State state) {
        if(mainView == null || state == null) {
            throw new IllegalArgumentException("One of the parameters is null.");
        }
        this.mainView = mainView;
        this.state = state;

        //Set the MIDI Device
        promptMidiDeviceSelection();

        //Setup the mainWindow behaviour
        mainView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        mainView.initializeActionListeners(this);
        mainView.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case MainView.ACTION_EXIT : exit();
                break;
            case MainView.ACTION_UPDATE_MIDI_DEVICE : promptMidiDeviceSelection();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    /**
     * Call this function if you want to exit the program.
     * It checks whether some changes were made to the current State
     * and warns the user about it with a Dialog.
     */
    private void exit() {
        if(state.isUnSaved()) {
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Warning! You have some unsaved changes.\n"
                            + "Are you sure you want to leave ?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION
            );

            //If the answer was "No"
            if(n == 1) {return;}
        }
        System.exit(0);
    }

    /**
     * Prompts the user to select a MIDI device and updates the State
     * with this new device.
     */
    private void promptMidiDeviceSelection() {
        MidiDevice midiDevice = null;
        try {
            midiDevice = CustomDialogs.showGetDeviceDialog();
        } catch (MidiUnavailableException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while loading MIDI device. Make sure it is not used by any other application",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        state.setMidiDevice(midiDevice);
    }
}
