package ch.flyingdutchman;

import ch.flyingdutchman.controller.MainController;
import ch.flyingdutchman.model.Preset;
import ch.flyingdutchman.model.State;
import ch.flyingdutchman.view.MainView;

import java.util.Locale;

/**
 * Main class that launches the hole program
 */
public class EasyMidiSampler {

    public static void main(String[] args) {
        System.out.println("Welcome !");
        System.out.println("Launching program...");

        Locale.setDefault(new Locale("en", "EN"));

        Preset newPreset = new Preset();
        State state = new State(newPreset);
        MainController mainController = new MainController(new MainView(state), state);

        // MIDI INTERACTION TEST
        /*
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for(int i=0;i<infos.length;i++)
        {
            System.out.println("["+i+"] "+infos[i].getName() + " - " + infos[i].getDescription());
        }
        //Select midi device
        System.out.print("\nPlease select Input Midi Device : ");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        try {
            MidiDevice inputDevice = MidiSystem.getMidiDevice(infos[input]);
            System.out.println("Selected Input :"+inputDevice.getDeviceInfo().getName());
            inputDevice.open(); //Lance la détection d'entrée, tant que inputDevice.close n'est pas appelé, le programme s'arrête pas
            Transmitter transmitter = inputDevice.getTransmitter();
            Receiver receiver = new Receiver() {
                @Override
                public void send(MidiMessage message, long timeStamp) {
                   System.out.println("Note stroke : "+ Arrays.toString(message.getMessage()));
                   String sadViolin = "Sad Violin.wav";
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sadViolin));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void close() {
                    System.out.println("Closed");
                }
            };

            transmitter.setReceiver(receiver);

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        */
    }
}
