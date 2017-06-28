package ch.flyingdutchman.view;

import ch.flyingdutchman.model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * View part of the MVC
 */
public class MainView extends JFrame {

    public static final String ACTION_EXIT = "Exit";
    public static final String ACTION_UPDATE_MIDI_DEVICE = "Select Midi Device";

    private State state;
    private List<JMenuItem> menuItemList;

    /**
     * Constructs a new MainView
     * Must be initialized by MainController to be usable
     *
     * @param state the state parameter
     * @see ch.flyingdutchman.controller.MainController
     */
    public MainView(State state) {
        if(state == null) {
            throw new IllegalArgumentException("Parameter must be non null");
        }
        this.state = state;
        menuItemList = new ArrayList<>();
        setTitle("Easy Midi Sampler");
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        JPanel centerPane = new JPanel(new FlowLayout());
        mainPanel.add(centerPane, BorderLayout.CENTER);

        //Initialisation of Menu Bar
        JMenuBar menuBar = new JMenuBar();

        //Create File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        //Create File MenuItems
        JMenuItem exitItem = new JMenuItem(ACTION_EXIT);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        menuItemList.add(exitItem);
        fileMenu.add(exitItem);

        //Create MIDI Menu
        JMenu midiMenu = new JMenu("MIDI");
        midiMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(midiMenu);

        //Create MIDI MenuItems
        JMenuItem deviceSelectItem = new JMenuItem(ACTION_UPDATE_MIDI_DEVICE);
        deviceSelectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        menuItemList.add(deviceSelectItem);
        midiMenu.add(deviceSelectItem);

        setJMenuBar(menuBar);

        setSize(1000,600);
        setLocationRelativeTo(null);
    }

    /**
     * Initializes the list of current of Objects who needs an actionListener with the
     * one given in parameter.
     *
     * @param actionListener the actionListener parameter
     */
    public void initializeActionListeners(ActionListener actionListener) {
        if(actionListener == null) {
            throw new IllegalArgumentException("Parameter must be non null");
        }
        for(JMenuItem menuItem : menuItemList) {
            menuItem.addActionListener(actionListener);
        }
    }
}
