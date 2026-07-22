package ui;

import exception.StatusException;
import model.Anime;
import model.AnimeList;
import model.AnimeType;
import model.WatchStatus;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.SafeFilePaths;

import java.io.IOException;
import java.nio.file.Path;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Console interface for the Anime List Manager.
public class AnimeListConsoleUI {
    private final Scanner scanner;
    private AnimeList animeList;
    private final Map<String, Runnable> menuCommands;

    public AnimeListConsoleUI() {
        scanner = new Scanner(System.in);
        animeList = new AnimeList();
        menuCommands = new HashMap<>();
        initializeCommands();
        runApp();
    }

    private void initializeCommands() {
        menuCommands.put("1", this::addAnime);
        menuCommands.put("2", this::viewAllAnime);
        menuCommands.put("3", this::searchAnime);
        menuCommands.put("4", this::deleteAnime);
        menuCommands.put("5", this::updateWatchStatus);
        menuCommands.put("6", this::saveAnimeList);
        menuCommands.put("7", this::loadAnimeList);
    }

    private void runApp() {
        boolean keepGoing = true;
        while (keepGoing) {
            displayMenu();
            keepGoing = handleUserChoice(scanner.nextLine().trim());
        }
        System.out.println("Goodbye!");
    }

    private boolean handleUserChoice(String choice) {
        if ("0".equals(choice) || "x".equalsIgnoreCase(choice) || "exit".equalsIgnoreCase(choice)) {
            return false;
        }

        Runnable command = menuCommands.get(choice);
        if (command == null) {
            System.out.println("Choose a valid menu number.");
        } else {
            command.run();
        }
        return true;
    }

    private void displayMenu() {
        System.out.println("\nAnime List Manager");
        System.out.println("1. Add Anime");
        System.out.println("2. View All Anime");
        System.out.println("3. Search Anime");
        System.out.println("4. Delete Anime");
        System.out.println("5. Update Watch Status");
        System.out.println("6. Save Anime List");
        System.out.println("7. Load Anime List");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    private void addAnime() {
        String name = readNonBlank("Anime name: ");
        List<AnimeType> types = selectMultipleTypes();
        YearMonth releaseDate = promptYearMonth();
        WatchStatus status = promptWatchStatus();

        animeList.addAnime(new Anime(name, types, releaseDate, status));
        System.out.println("Anime added.");
    }

    private String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Value cannot be empty.");
        }
    }

    private void viewAllAnime() {
        List<Anime> animes = animeList.getAnimes();
        if (animes.isEmpty()) {
            System.out.println("No anime in the list.");
            return;
        }

        System.out.println("\n=== All Anime ===");
        for (int i = 0; i < animes.size(); i++) {
            System.out.println((i + 1) + ". " + animes.get(i));
        }
    }

    private void searchAnime() {
        if (animeList.getAnimes().isEmpty()) {
            System.out.println("Add anime before searching.");
            return;
        }

        System.out.println("1. Search by types");
        System.out.println("2. Search by release date");
        int choice = readInt("Choice: ", 1, 2);
        if (choice == 1) {
            searchByMultipleTypes();
        } else {
            searchByTime();
        }
    }

    private void deleteAnime() {
        if (animeList.getAnimes().isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        viewAllAnime();
        int index = readInt("Number to delete: ", 1, animeList.getAnimes().size()) - 1;
        Anime selected = animeList.getAnimes().get(index);
        if (animeList.removeAnime(selected)) {
            System.out.println("Anime deleted.");
        }
    }

    private void updateWatchStatus() {
        if (animeList.getAnimes().isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        viewAllAnime();
        int index = readInt("Number to update: ", 1, animeList.getAnimes().size()) - 1;
        Anime selected = animeList.getAnimes().get(index);
        WatchStatus status = promptWatchStatus();

        if (animeList.updateStatus(selected, status)) {
            System.out.println("Anime status updated.");
        } else {
            System.out.println("The selected status is already set.");
        }
    }

    private List<AnimeType> selectMultipleTypes() {
        List<AnimeType> chosen = new ArrayList<>();
        AnimeType[] values = AnimeType.values();

        while (true) {
            System.out.println("\nSelect one or more types. Enter 0 when finished.");
            for (int i = 0; i < values.length; i++) {
                System.out.println((i + 1) + ". " + values[i]);
            }

            int input = readInt("Choice: ", 0, values.length);
            if (input == 0) {
                if (!chosen.isEmpty()) {
                    return chosen;
                }
                System.out.println("Select at least one type.");
                continue;
            }

            AnimeType selected = values[input - 1];
            if (!chosen.contains(selected)) {
                chosen.add(selected);
            } else {
                System.out.println(selected + " is already selected.");
            }
        }
    }

    private YearMonth promptYearMonth() {
        while (true) {
            System.out.print("Release date (yyyy-MM): ");
            try {
                return YearMonth.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException exception) {
                System.out.println("Invalid date. Use yyyy-MM.");
            }
        }
    }

    private WatchStatus promptWatchStatus() {
        while (true) {
            System.out.print("Status (watching, completed, plan to watch): ");
            try {
                return WatchStatus.fromInput(scanner.nextLine());
            } catch (StatusException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private int readInt(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= minimum && value <= maximum) {
                    return value;
                }
            } catch (NumberFormatException exception) {
                // The shared message below explains the accepted range.
            }
            System.out.println("Enter a number from " + minimum + " to " + maximum + ".");
        }
    }

    private void searchByMultipleTypes() {
        List<AnimeType> chosenTypes = selectMultipleTypes();
        showResults(animeList.searchByTypes(chosenTypes));
    }

    private void searchByTime() {
        showResults(animeList.searchByTime(promptYearMonth()));
    }

    private void showResults(List<Anime> results) {
        if (results.isEmpty()) {
            System.out.println("No matching anime found.");
            return;
        }

        System.out.println("\n=== Search Results ===");
        for (Anime anime : results) {
            System.out.println(anime);
        }
    }

    private void saveAnimeList() {
        if (animeList.getAnimes().isEmpty()) {
            System.out.println("Add anime before saving.");
            return;
        }

        System.out.print("File name: ");
        try {
            Path file = SafeFilePaths.resolveDataFile(scanner.nextLine());
            new JsonWriter(file.toString()).write(animeList);
            System.out.println("Saved to " + file);
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Unable to save: " + exception.getMessage());
        }
    }

    private void loadAnimeList() {
        System.out.print("File name: ");
        try {
            Path file = SafeFilePaths.resolveDataFile(scanner.nextLine());
            animeList = new JsonReader(file.toString()).read();
            System.out.println("Loaded from " + file);
        } catch (IOException | IllegalArgumentException exception) {
            System.out.println("Unable to load: " + exception.getMessage());
        }
    }
}
