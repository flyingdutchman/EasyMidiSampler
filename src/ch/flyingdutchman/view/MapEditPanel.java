package ch.flyingdutchman.view;

import ch.flyingdutchman.model.MidiMap;

import javax.swing.*;
import java.awt.*;

public class MapEditPanel extends JPanel {

    private MidiMap editedMidiMap;
    private JTextField nameTextField;
    private JTextField pathTextField;
    private JSpinner midiSpinner;
    private final int TEXT_FIELD_WIDTH = 20;

    public MapEditPanel() {

        //Initialization of grid of components
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);

        //Initialization of the Name line
        JLabel nameLabel = new JLabel("Map Name:");
        c.weightx=1.;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(nameLabel, c);

        nameTextField = new JTextField(TEXT_FIELD_WIDTH);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        add(nameTextField, c);

        //Initialization of File search
        JLabel fileLabel = new JLabel("Audio Location:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(fileLabel, c);

        pathTextField = new JTextField(TEXT_FIELD_WIDTH);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        add(pathTextField, c);

        JButton browse = new JButton("Browse");
        browse.setMargin(new Insets(0,0,0,0));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        add(browse, c);

        //Initialization of Midi select
        JLabel midiLabel = new JLabel("Midi Note:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        add(midiLabel,c);

        midiSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 127, 1));
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 2;
        add(midiSpinner,c);

        setVisible(true);
    }

    public MapEditPanel(MidiMap midiMap) {
        this();
        editedMidiMap = midiMap;
        nameTextField.setText(midiMap.getName());
        pathTextField.setText(midiMap.getPath());
        midiSpinner.setValue(midiMap.getKeyNumber());

    }

    public MidiMap getInput() {
        return new MidiMap(nameTextField.getText(), pathTextField.getText(), (int)midiSpinner.getValue());
    }

}
