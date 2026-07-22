package ui;

import javax.swing.SwingUtilities;

// Application entry point.
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        if (args.length > 0 && "--cli".equalsIgnoreCase(args[0])) {
            new AnimeListConsoleUI();
        } else {
            SwingUtilities.invokeLater(AnimeListGUI::new);
        }
    }
}
