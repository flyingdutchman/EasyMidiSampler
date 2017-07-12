package ch.flyingdutchman.view;

import ch.flyingdutchman.model.CustomReceiver;
import ch.flyingdutchman.model.MidiMap;
import ch.flyingdutchman.model.State;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * View part of the MVC
 */
public class MainView extends JFrame implements Observer, ActionListener, ItemListener{

    private static final String ACTION_EXIT = "Exit";
    private static final String ACTION_UPDATE_MIDI_DEVICE = "Select Midi Device";
    private static final String ACTION_NEW_MAPPING = "New Mapping";
    private static final String ACTION_EDIT_MAPPING = "Edit Mapping";
    private static final String ACTION_DELETE_MAPPING = "Delete Mapping";

    private State state;
    private JMenuBar menuBar;
    private JMenuItem editMappingItem;
    private JMenuItem deleteMappingItem;
    private JTable mappings;
    private String[] columnNames = {"Name","Key Number","Audio file", "Behaviour", "Activated"};

    /**
     * Constructs a new MainView
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
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnNames);
        mappings = new JTable(model);
        mappings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mappings.setShowGrid(false);
        mappings.setDragEnabled(false);
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

        //Delete Mapping
        deleteMappingItem = new JMenuItem(ACTION_DELETE_MAPPING);
        deleteMappingItem.addActionListener(this);
        editMenu.add(deleteMappingItem);
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

        //TODO au lieu de changer les boutons ici, faire un action listener qui détecte une sélection
        if(state.getMapping().isEmpty()) {
            editMappingItem.setEnabled(false);
            deleteMappingItem.setEnabled(false);
        } else {
            editMappingItem.setEnabled(true);
            deleteMappingItem.setEnabled(true);
        }

        Vector<MidiMap> maps = state.getMapping();
        Object[][] data = new Object[maps.size()][columnNames.length];
        for(int i = 0; i < data.length; i++) {
            MidiMap midiMap = maps.elementAt(i);
            Object[] newLine = {midiMap.getName(), midiMap.getKeyNumber(), midiMap.getPath()};
            data[i] = newLine;
        }
        DefaultTableModel model = (DefaultTableModel) mappings.getModel();
        model.setDataVector(data, columnNames);
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
            case MainView.ACTION_DELETE_MAPPING :
                deleteMapping();
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    private void newMapping() {
        MidiMap newMap = CustomDialogs.showMapEditDialog();
        state.addMapping(newMap);
    }

    private void editMapping() {
        //Extract selected mapping
        int selectedRow = mappings.getSelectedRow();
        if(selectedRow == -1) {
            return;
        }
        MidiMap midiMap = state.getMapping().elementAt(selectedRow);
        MidiMap newMap = CustomDialogs.showMapEditDialog(midiMap);
        state.setMapping(selectedRow, newMap);
    }

    private void deleteMapping() {
        //Extract selected mapping
        int selectedRow = mappings.getSelectedRow();
        if(selectedRow == -1) {
            return;
        }
        state.deleteMapping(selectedRow);
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
        if(midiDevice != null) {
            initializeMidiDevice(midiDevice);
        }
        state.setMidiDevice(midiDevice);
    }

    private void initializeMidiDevice(MidiDevice midiDevice) {
        System.out.println("Midi Device Initialization");
        try {
            midiDevice.open();
            Transmitter transmitter = midiDevice.getTransmitter();
            Receiver receiver = new CustomReceiver(state.getMapping());
            transmitter.setReceiver(receiver);

        } catch (MidiUnavailableException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while loading MIDI device. Make sure it is not used by any other application",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
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

        if(state.getMidiDevice() != null) {
            state.getMidiDevice().close(); //All Receiver and Transmitter instances open from this device are closed.
        }
        state.getMapping().forEach((midiMap) -> midiMap.getClip().close());
        System.exit(0);
    }
}
