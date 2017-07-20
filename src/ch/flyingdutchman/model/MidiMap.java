package ch.flyingdutchman.model;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Representation of a mapping between a clip and a given MIDI Key Number
 */
public class MidiMap {

    private String name;
    private String path;
    private Clip clip;
    private int keyNumber;
    private boolean pushToStop;

    /**
     * Constructs a new MidiMap that will associate a clip and a name to a key number
     *
     * @param name the name of the map
     * @param path the path of the audio clip to map
     * @param keyNumber the MIDI key number you want to bind [0-128]
     */
    public MidiMap(String name, String path, int keyNumber, boolean pushToStop) {
        if(name == null || path == null || keyNumber < 0 || keyNumber > 128) {
            throw new IllegalArgumentException();
        }

        AudioInputStream audioInputStream = null;
        Clip clip = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while reading audio file. Make sure the given path exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            throw new IllegalArgumentException();
        } catch (UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(null,
                    "Error while reading audio file. Make sure the file is compatible.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            throw new IllegalArgumentException();
        }

        System.out.println("passed");
        this.name = name;
        this.path = path;
        this.clip = clip;
        this.keyNumber = keyNumber;
        this.pushToStop = pushToStop;
    }

    /**
     *
     * @return
     */
    public boolean isPushToStop() {
        return pushToStop;
    }

    /**
     * Returns the key number of the MidiMap
     *
     * @return the key number
     */
    public int getKeyNumber() {
        return keyNumber;
    }

    /**
     * Returns the clip of the MiniMap
     *
     * @return the clip
     */
    public Clip getClip() {
        return clip;
    }

    /**
     * Returns the name of the MiniMap in the form of a String
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String newPath) {
        path = newPath;
    }

    /**
     * Returns a string representation of the different values that compose a MidiMap such as
     * its name and key numbers
     *
     * @return a string representation of the MidiMap
     */
    public String toString() {
        return "Name : "+name+" | Key Number : "+ keyNumber;
    }
}
