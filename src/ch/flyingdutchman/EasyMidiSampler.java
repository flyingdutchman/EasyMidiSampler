package ch.flyingdutchman;

import ch.flyingdutchman.model.Preset;
import ch.flyingdutchman.model.State;
import ch.flyingdutchman.view.MainView;

import java.util.Locale;
import java.util.prefs.Preferences;

/**
 * Main class that launches the hole program
 */
public class EasyMidiSampler {

    public static void main(String[] args) {

        System.out.println("Welcome !");
        System.out.println("Launching program...");

        Locale.setDefault(new Locale("en", "EN"));

        State state = new State(new Preset());
        MainView mainView = new MainView(state);
        state.addObserver(mainView);

    }
}
