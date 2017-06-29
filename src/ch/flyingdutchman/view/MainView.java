package ch.flyingdutchman.view;

import ch.flyingdutchman.model.MidiMap;
import ch.flyingdutchman.model.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;


/**
 * View part of the MVC
 */
public class MainView extends JFrame implements Observer{

    public static final String ACTION_EXIT = "Exit";
    public static final String ACTION_UPDATE_MIDI_DEVICE = "Select Midi Device";
    public static final String ACTION_NEW_MAPPING = "New Mapping";

    private State state;
    private List<JMenuItem> menuItemList;
    JMenuBar menuBar;
    JList<MidiMap> mappings;

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


        //Initialization of center panel
        mappings = new JList<>();
        mappings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mappings.setLayoutOrientation(JList.VERTICAL);
        mappings.setListData(state.getMapping());
        mappings.setSelectedIndex(0);
        JScrollPane scrollPane = new JScrollPane(mappings);
        mainPanel.add(scrollPane, BorderLayout.CENTER);



        //Initialisation of Menu Bar
        menuBar = new JMenuBar();
        createFileMenu();
        createEditMenu();
        createMidiMenu();
        setJMenuBar(menuBar);

        //Window visual tweaks
        setSize(1000,600);
        setLocationRelativeTo(null);
    }

    private void createFileMenu() {
        //Create File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        //Create File MenuItems
        JMenuItem exitItem = new JMenuItem(ACTION_EXIT);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        menuItemList.add(exitItem);
        fileMenu.add(exitItem);
    }

    private void createEditMenu() {
        //Create Edit Menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(editMenu);

        //Create Edit MenuItem
        JMenuItem newMappingItem = new JMenuItem(ACTION_NEW_MAPPING);
        menuItemList.add(newMappingItem);
        editMenu.add(newMappingItem);

    }

    private void createMidiMenu() {
        //Create MIDI Menu
        JMenu midiMenu = new JMenu("MIDI");
        midiMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(midiMenu);

        //Create MIDI MenuItems
        JMenuItem deviceSelectItem = new JMenuItem(ACTION_UPDATE_MIDI_DEVICE);
        deviceSelectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        menuItemList.add(deviceSelectItem);
        midiMenu.add(deviceSelectItem);
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

    @Override
    public void update(Observable o, Object arg) {
        int n = (Integer) arg;
        switch (n) {
            case State.UPDATE_MAPPING_LIST :
                updateMappingList();
                break;
        }
    }

    private void updateMappingList() {
        mappings.setListData(state.getMapping());
    }
}
