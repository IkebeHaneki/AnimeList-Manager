package ui;

import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the main GUI for the Anime List Manager application.
 * <p>
 * This class uses Java Swing to provide a window where users can:
 * <li>Add new Anime entries
 * <li>Remove selected Anime entries
 * <li>Search for Anime by release date or multiple types
 * <li>Save the current list to a user-specified JSON file
 * <li>Load a previously saved list from a user-specified JSON file
 * Additionally, it attempts to load a custom icon for the window.
 */

public class AnimeListGUI extends JFrame {
    private AnimeList animeList;
    private AnimeTableModel tableModel;
    private JTable table;
    private JTextField nameField;
    private JTextField dateField;
    private JComboBox<WatchStatus> statusBox;
    private JCheckBox actionCB;
    private JCheckBox schoolCB;
    private JCheckBox comedyCB;
    private JCheckBox fantasyCB;
    private JCheckBox romanceCB;
    private JCheckBox tragicCB;
    private JCheckBox adventureCB;
    private JCheckBox sportsCB;
    private JCheckBox searchActionCB;
    private JCheckBox searchSchoolCB;
    private JCheckBox searchComedyCB;
    private JCheckBox searchFantasyCB;
    private JCheckBox searchRomanceCB;
    private JCheckBox searchTragicCB;
    private JCheckBox searchAdventureCB;
    private JCheckBox searchSportsCB;

    // EFFECTS:Main entry
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AnimeListGUI::new);
    }

    // EFFECTS: Constructs the main window, sets up the UI components, and shows the
    // frame.
    // MODIFIES: this
    public AnimeListGUI() {
        super("Anime List Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        animeList = new AnimeList();
        tableModel = new AnimeTableModel(animeList.getAnimes());
        table = new JTable(tableModel);
        initUI();
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS:Sets up the layout and buttons in the main frame.
    private void initUI() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    // EFFECTS:Creates a panel containing all the buttons;
    private JPanel createButtonPanel() {
        JPanel bp = new JPanel();
        JButton addBtn = new JButton("Add Anime");
        JButton remBtn = new JButton("Remove Anime");
        JButton updBtn = new JButton("Update Status");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        JButton srchBtn = new JButton("Search");
        JButton statsBtn = new JButton("Show Stats");

        addBtn.addActionListener(e -> showAddAnimeDialog());
        remBtn.addActionListener(e -> removeSelectedAnime());
        updBtn.addActionListener(e -> updateSelectedAnimeStatus());
        saveBtn.addActionListener(e -> saveAnimeList());
        loadBtn.addActionListener(e -> loadAnimeList());
        srchBtn.addActionListener(e -> showSearchDialog());
        statsBtn.addActionListener(e -> showTypeDistributionChart());

        bp.add(addBtn);
        bp.add(remBtn);
        bp.add(updBtn);
        bp.add(saveBtn);
        bp.add(loadBtn);
        bp.add(srchBtn);
        bp.add(statsBtn);

        return bp;
    }

    // EFFECTS:Displays a dialog for adding a new Anime to the list.
    // MODIFIES: this
    private void showAddAnimeDialog() {
        JPanel p = createAddAnimePanel();
        int r = JOptionPane.showConfirmDialog(
                this,
                p,
                "Add Anime",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (r == JOptionPane.OK_OPTION) {
            handleAddAnime();
        }
    }

    // EFFECTS:Builds the form (text fields, checkboxes, combo box) for adding a
    // Anime.
    // MODIFIES: this
    private JPanel createAddAnimePanel() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(createNameRow());
        main.add(createReleaseRow());
        main.add(createWatchStatusRow());
        main.add(new JLabel("Types:"));
        main.add(createTypesGrid());
        return main;
    }

    // MODIFIES:this
    // EFFECTS:builds the text fields for Name
    private JPanel createNameRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel("Name: "));
        nameField = new JTextField(15);
        row.add(nameField);
        return row;
    }

    // MODIFIES:this
    // EFFECTS:builds the text fields for Time
    private JPanel createReleaseRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel("Release (yyyy-MM): "));
        dateField = new JTextField("yyyy-MM", 10);
        row.add(dateField);
        return row;
    }

    // MODIFIES:this
    // EFFECTS:builds the text fields for Watch Status
    private JPanel createWatchStatusRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.add(new JLabel("Watch Status: "));
        statusBox = new JComboBox<>(WatchStatus.values());
        row.add(statusBox);
        return row;
    }

    // MODIFIES:this
    // EFFECTS:Creates a 2x4 grid of checkboxes for anime types
    private JPanel createTypesGrid() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 5, 5));
        actionCB = new JCheckBox("Action");
        schoolCB = new JCheckBox("School");
        comedyCB = new JCheckBox("Comedy");
        fantasyCB = new JCheckBox("Fantasy");
        romanceCB = new JCheckBox("Romance");
        tragicCB = new JCheckBox("Tragic");
        adventureCB = new JCheckBox("Adventure");
        sportsCB = new JCheckBox("Sports");
        panel.add(actionCB);
        panel.add(schoolCB);
        panel.add(comedyCB);
        panel.add(fantasyCB);
        panel.add(romanceCB);
        panel.add(tragicCB);
        panel.add(adventureCB);
        panel.add(sportsCB);
        return panel;
    }

    // EFFETCTS:Gathers user input from fields, creates an Anime, and adds it to
    // list.
    // MODIFIES:this.animelist
    private void handleAddAnime() {
        String n = nameField.getText().trim();
        String d = dateField.getText().trim();
        WatchStatus st = (WatchStatus) statusBox.getSelectedItem();
        if (n.isEmpty()) {
            JOptionPane.showMessageDialog(this, "(ﾒ ﾟ皿ﾟ)ﾒ Name cannot be empty!");
            return;
        }
        List<AnimeType> chosen = gatherAddTypes();
        try {
            YearMonth ym = YearMonth.parse(d);
            animeList.addAnime(new Anime(n, chosen, ym, st));
            tableModel.setAnimes(animeList.getAnimes());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date (yyyy-MM).");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    // EFFECTS:Returns a list of AnimeType corresponding to the checkboxes that
    // selected
    private List<AnimeType> gatherAddTypes() {
        List<AnimeType> selectedTypes = new ArrayList<>();
        JCheckBox[] boxes = {
                actionCB, schoolCB, comedyCB, fantasyCB,
                romanceCB, tragicCB, adventureCB, sportsCB
        };
        AnimeType[] types = {
                AnimeType.Action, AnimeType.School, AnimeType.Comedy, AnimeType.Fantasy,
                AnimeType.Romance, AnimeType.Tragic, AnimeType.Adventure, AnimeType.Sports
        };
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i].isSelected()) {
                selectedTypes.add(types[i]);
            }
        }
        return selectedTypes;
    }

    // MODIFIES:this
    // EFFECTS:Removes the currently selected Anime from the table/list.
    private void removeSelectedAnime() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            Anime a = tableModel.getAnimeAt(row);
            animeList.removeAnime(a);
            tableModel.setAnimes(animeList.getAnimes());
        } else {
            JOptionPane.showMessageDialog(this, "(#`皿´) No anime selected!");
        }
    }

    // MODIFIES:this
    // EFFECTS:Update the anime's watch status choose from list
    private void updateSelectedAnimeStatus() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showNoAnimeSelected();
            return;
        }
        Anime a = tableModel.getAnimeAt(row);
        WatchStatus newSt = promptWatchStatus(a.getStatus());
        if (newSt == null) {
            return; // user canceled
        }
        if (newSt == a.getStatus()) {
            JOptionPane.showMessageDialog(this, "You chose the same status. No change made!");
            return;
        }
        try {
            a.setStatus(newSt.name());
            tableModel.setAnimes(animeList.getAnimes());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    //MODIFIES:this
    //EFFECTS:Shows an error dialog that no anime is selected
    private void showNoAnimeSelected() {
        JOptionPane.showMessageDialog(this, "No anime selected!");
    }

    //REQUIRES: current != null
    //EFFECTS:Opens an input dialog for selecting a new WatchStatus
    private WatchStatus promptWatchStatus(WatchStatus current) {
        return (WatchStatus) JOptionPane.showInputDialog(
            this,
            "Select new watch status:",
            "Update Status",
            JOptionPane.PLAIN_MESSAGE,
            null,
            WatchStatus.values(),
            current
        );
    }

    // EFFECTS:Saves the current list to ./data/<filename>.json.
    private void saveAnimeList() {
        if (animeList.getAnimes().isEmpty()) {
            JOptionPane.showMessageDialog(this, "(｡í _ ì｡) No anime to save!");
            return;
        }
        String in = JOptionPane.showInputDialog(this, "Enter file name:", "Save", JOptionPane.PLAIN_MESSAGE);
        if (in == null || in.trim().isEmpty()) {
            return;
        }
        String fp = "./data/" + in.trim() + ".json";
        try {
            JsonWriter w = new JsonWriter(fp);
            w.open();
            w.write(animeList);
            w.close();
            JOptionPane.showMessageDialog(this, "Saved to " + fp);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Cannot write to: " + fp);
        }
    }

    // MODIFIES:this
    // EFFECTS:Loads an AnimeList from ./data/<filename>.json.
    private void loadAnimeList() {
        String in = JOptionPane.showInputDialog(this, "Enter file name:", "Load", JOptionPane.PLAIN_MESSAGE);
        if (in == null || in.trim().isEmpty()) {
            return;
        }
        String fp = "./data/" + in.trim() + ".json";
        try {
            animeList = new JsonReader(fp).read();
            tableModel.setAnimes(animeList.getAnimes());
            JOptionPane.showMessageDialog(this, "Loaded from " + fp);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Cannot read from: " + fp);
        }
    }

    // MODIFIES:this
    // EFFETCS:Opens a dialog to search by release date or multiple types.
    private void showSearchDialog() {
        String[] options = { "Search by Release Date", "Search by Types" };
        int choice = JOptionPane.showOptionDialog(
                this,
                "(ﾟ3ﾟ)～♪ How to search?",
                "Search",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            searchByTimeDialog();
        } else if (choice == 1) {
            searchByTypesDialog();
        }
    }

    // EFFECTS:Prompts the user for a yyyy-MM date and displays matches;
    private void searchByTimeDialog() {
        String ds = JOptionPane.showInputDialog(this, "Enter date(yyyy-MM):");
        if (ds != null && !ds.trim().isEmpty()) {
            try {
                YearMonth ym = YearMonth.parse(ds.trim());
                List<Anime> res = animeList.searchByTime(ym);
                showSearchResults(res, "Search by Time: " + ym);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Invalid date format!");
            }
        }
    }

    // EFFECTS:Opens a dialog to pick multiple types, then shows matches
    private void searchByTypesDialog() {
        JPanel p = createTypesPanel();
        int r = JOptionPane.showConfirmDialog(
                this,
                p,
                "Select Types",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (r == JOptionPane.OK_OPTION) {
            List<AnimeType> chosen = gatherSearchTypes();
            if (chosen.isEmpty()) {
                JOptionPane.showMessageDialog(this, "(`3´) No types selected!");
            } else {
                List<Anime> res = animeList.searchByTypes(chosen);
                showSearchResults(res, "Search by Types: " + chosen);
            }
        }
    }

    // EFFECTS:Builds the panel with checkboxes for searching by multiple types.
    private JPanel createTypesPanel() {
        JPanel p = new JPanel(new GridLayout(0, 2));
        initSearchCheckBoxes();
        addSearchCheckBoxes(p);
        return p;
    }

    // EFFECTS:Initializes the search-type checkboxes with descriptive names.
    private void initSearchCheckBoxes() {
        searchActionCB = new JCheckBox("Action");
        searchSchoolCB = new JCheckBox("School");
        searchComedyCB = new JCheckBox("Comedy");
        searchFantasyCB = new JCheckBox("Fantasy");
        searchRomanceCB = new JCheckBox("Romance");
        searchTragicCB = new JCheckBox("Tragic");
        searchAdventureCB = new JCheckBox("Adventure");
        searchSportsCB = new JCheckBox("Sports");
    }

    // EFFECTS:Adds the search-type checkboxes to the given panel
    private void addSearchCheckBoxes(JPanel p) {
        p.add(searchActionCB);
        p.add(searchSchoolCB);
        p.add(searchComedyCB);
        p.add(searchFantasyCB);
        p.add(searchRomanceCB);
        p.add(searchTragicCB);
        p.add(searchAdventureCB);
        p.add(searchSportsCB);
    }

    // EFFECTS:Gathers which "search" checkboxes are selected into a List of
    // AnimeType
    private List<AnimeType> gatherSearchTypes() {
        List<AnimeType> selected = new ArrayList<>();
        JCheckBox[] boxes = {
                searchActionCB, searchSchoolCB, searchComedyCB, searchFantasyCB,
                searchRomanceCB, searchTragicCB, searchAdventureCB, searchSportsCB
        };
        AnimeType[] types = {
                AnimeType.Action, AnimeType.School, AnimeType.Comedy, AnimeType.Fantasy,
                AnimeType.Romance, AnimeType.Tragic, AnimeType.Adventure, AnimeType.Sports
        };
        for (int i = 0; i < boxes.length; i++) {
            if (boxes[i].isSelected()) {
                selected.add(types[i]);
            }
        }
        return selected;
    }

    // EFFECTS:Displays the results of a search in a simple message dialog
    private void showSearchResults(List<Anime> res, String title) {
        if (res.isEmpty()) {
            JOptionPane.showMessageDialog(this, "(◞‸◟) No results found for " + title);
        } else {
            StringBuilder sb = new StringBuilder("Results:\n");
            for (Anime a : res) {
                sb.append(a).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        }
    }

    // EFFECTS:Gathers type counts from animeList, displays them in a bar chart
    private void showTypeDistributionChart() {
        Map<AnimeType, Integer> data = getTypeCounts();
        ChartPanel chart = new ChartPanel(data);
        JDialog dialog = new JDialog(this, "Type Distribution", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(chart);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // EFFECTS:Counts how many times each AnimeType appears in the anime list.
    private Map<AnimeType, Integer> getTypeCounts() {
        Map<AnimeType, Integer> map = new EnumMap<>(AnimeType.class);
        for (AnimeType t : AnimeType.values()) {
            map.put(t, 0);
        }
        for (Anime a : animeList.getAnimes()) {
            for (AnimeType t : a.getTypes()) {
                map.put(t, map.get(t) + 1);
            }
        }
        return map;
    }

}
