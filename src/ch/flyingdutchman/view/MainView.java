package ch.flyingdutchman.view;

import ch.flyingdutchman.model.MidiMap;
import ch.flyingdutchman.model.State;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * View part of the MVC
 */
public class MainView extends JFrame implements Observer, ActionListener, ItemListener{

    public static final String ACTION_EXIT = "Exit";
    public static final String ACTION_UPDATE_MIDI_DEVICE = "Select Midi Device";
    public static final String ACTION_NEW_MAPPING = "New Mapping";
    public static final String ACTION_EDIT_MAPPING = "Edit Mapping";

    private State state;
    JMenuBar menuBar;
    JMenuItem editMappingItem;
    JList<MidiMap> mappings;

    /**
     * Constructs a new MainView
     * Must be initialized by MainController to be usable
     *
     * @param state the state parameter
     */
    public MainView(State state) {
        if(state == null) {
            throw new IllegalArgumentException("Parameter must be non null");
        }
        this.state = state;
        setTitle("Easy Midi Sampler");
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        promptMidiDeviceSelection();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });


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


        updateMappingList();

        setVisible(true);
    }

    private void createFileMenu() {
        //Create File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        //Create File MenuItems
        JMenuItem exitItem = new JMenuItem(ACTION_EXIT);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);
    }

    private void createEditMenu() {
        //Create Edit Menu
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(editMenu);

        //Create Edit MenuItems
        //New Mapping
        JMenuItem newMappingItem = new JMenuItem(ACTION_NEW_MAPPING);
        newMappingItem.addActionListener(this);
        editMenu.add(newMappingItem);

        //Edit Mapping
        editMappingItem = new JMenuItem(ACTION_EDIT_MAPPING);
        editMappingItem.addActionListener(this);
        editMenu.add(editMappingItem);
    }

    private void createMidiMenu() {
        //Create MIDI Menu
        JMenu midiMenu = new JMenu("MIDI");
        midiMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(midiMenu);

        //Create MIDI MenuItems
        JMenuItem deviceSelectItem = new JMenuItem(ACTION_UPDATE_MIDI_DEVICE);
        deviceSelectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
        deviceSelectItem.addActionListener(this);
        midiMenu.add(deviceSelectItem);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update");
        int n = (Integer) arg;
        switch (n) {
            case State.UPDATE_MAPPING_LIST :
                updateMappingList();
                break;
        }
    }

    private void updateMappingList() {
        if(state.getMapping().isEmpty()) {
            editMappingItem.setEnabled(false);
        } else {
            editMappingItem.setEnabled(true);
            mappings.setListData(state.getMapping());
        }
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case MainView.ACTION_EXIT :
                exit();
                break;
            case MainView.ACTION_UPDATE_MIDI_DEVICE :
                promptMidiDeviceSelection();
                break;
            case MainView.ACTION_NEW_MAPPING :
                newMapping();
                break;
            case MainView.ACTION_EDIT_MAPPING :
                editMapping();
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    private void editMapping() {

    }

    private void newMapping() {
        state.addMapping(new MidiMap(null,42));
    }

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
