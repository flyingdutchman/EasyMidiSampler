package ch.flyingdutchman.view;

import ch.flyingdutchman.model.MidiMap;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.prefs.Preferences;

public class MapEditPanel extends JPanel {

    private JTextField nameTextField;
    private JTextField pathTextField;
    private JSpinner midiSpinner;
    private JCheckBox typeCheckBox;
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
        browse.addActionListener(e -> {

            Preferences prefs = Preferences.userRoot();

            String path = prefs.get("DEFAULT_PATH","");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(path));
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileFilter audioFilter = new FileNameExtensionFilter("Supported Audio files (.wav, .aiff)", "wav", "aiff");
            fileChooser.resetChoosableFileFilters();
            fileChooser.addChoosableFileFilter(audioFilter);
            int returnValue = fileChooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION) {
               pathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
               prefs.put("DEFAULT_PATH", fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
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

        typeCheckBox = new JCheckBox("Push to stop");
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        add(typeCheckBox,c);

        setVisible(true);
    }

    public MapEditPanel(MidiMap midiMap) {
        this();
        nameTextField.setText(midiMap.getName());
        pathTextField.setText(midiMap.getPath());
        midiSpinner.setValue(midiMap.getKeyNumber());
    }

    /**
     * Returns a new MidiMap based on the user input retrieved in the edited Panel
     *
     * @return a new MidiMap
     */
    public MidiMap getInput() {
        return new MidiMap(nameTextField.getText(), pathTextField.getText(), (int)midiSpinner.getValue(), typeCheckBox.isSelected());
    }

}
