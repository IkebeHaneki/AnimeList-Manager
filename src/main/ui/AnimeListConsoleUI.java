package ui;

import model.AnimeList;
import model.Anime;
import model.AnimeType;
import model.WatchStatus;
import exception.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

//EFFECTS:represent a user interface for the Anime List Manager

public class AnimeListConsoleUI {
    private Scanner scanner;
    private AnimeList animeList;

    // EFFECTS: construct the ui and start the users menu
    public AnimeListConsoleUI() {
        // stub
        scanner = new Scanner(System.in);
        animeList = new AnimeList();
        scanner.useDelimiter("\r?\n|\r");
        runApp();

    }
    // EFFECTS:run the main menu loop until users exit.

    private void runApp() {
        // stub
        boolean keepGoing = true;
        while (keepGoing) {
            displayMenu();
            String choice = scanner.nextLine().trim();
            keepGoing = handleUserChoice(choice);
        }
        System.out.println("You want exit? OK, byebye!!");
    }

    // EFFECTS: handle users choice to do functions on the application
    private boolean handleUserChoice(String choice) {
        // return true; //stub
        switch (choice) {
            case "1":
                addAnime();
                break;
            case "2":
                viewAllAnime();
                break;
            case "3":
                searchAnime();
                break;
            case "4":
                deleteAnime();
                break;
            case "5":
                updateWatchStatus();
                break;
            case "0":
                return false;
            default:
                System.out.println("What you just typed in? Choose a valid number pls!!");
        }
        return true;
    }

    // EFFECTS: display the users menu loop

    private void displayMenu() {
        // stub
        System.out.println("\n This is Anime List Manager, Ciallo~(∠・ω< )⌒☆");
        System.out.println("1. Add Anime");
        System.out.println("2. View All Animes");
        System.out.println("3. Search Anime");
        System.out.println("4. Delete Anime");
        System.out.println("5. Update Watch Status");
        System.out.println("0. Exit");
        System.out.print("Tell me Your Choice: ");
    }

    // EFFECTS: add a new anime(name,types, release time, status) to the anime list
    // MODIFIES: this
    private void addAnime() {
        // stub
        System.out.println("Please enter the Anime Name ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Anime's name should not be empty!!!! The Nameless");
            addAnime();
        }

        List<AnimeType> types = selectMultipleTypes();
        YearMonth ym = promptYearMonth();
        Anime anime = new Anime(name, types, ym, null);
        while (true) {
            String statusStr = promptStatusString();
            boolean validStatus = setAnimeStatus(anime, statusStr);
            if (validStatus) {
                break;
            }
        }
        animeList.addAnime(anime);
        System.out.println("Anime Added Successful!!!");
    }

    // EFFECTS: represent all animes in the list

    private void viewAllAnime() {
        // stub
        List<Anime> animes = animeList.getAnimes();
        if (animes.isEmpty()) {
            System.out.println("No anime in the list.");
        } else {
            System.out.println("\n=== All Anime ===");
            for (int i = 0; i < animes.size(); i++) {
                System.out.println((i + 1) + ". " + animes.get(i));
            }
        }
    }

    // EFFECTS: Let users to choose the way to search anime from the list.

    private void searchAnime() {
        // stub
        if (animeList.getAnimes().isEmpty()) {
            System.out.println("If you want search, there must have things right? Add Animes First!!!");
            return;
        }

        System.out.println("Choose the method you want to search!");
        System.out.println("Enter 1 to search By Types");
        System.out.println("Enter 2 to search By Time");
        System.out.println("Tell me Your Choice: ");
        String choice = scanner.nextLine().trim();
        if (choice.equals("1")) {
            searchByMultipleTypes();
        } else if (choice.equals("2")) {
            searchByTime();
        } else {
            System.out.println("Invalid Choice!!! Please Try Again");
            searchAnime();
        }
    }

    // EFFECTS: delete the anime that users want to remove from the list
    // MODEFIES:this
    private void deleteAnime() {
        // stub
        viewAllAnime();
        List<Anime> animes = animeList.getAnimes();
        if (animes.isEmpty()) {
            System.out.println("List is empty, please add animes first!");
            return;
        }
        System.out.print("Number to delete: ");
        int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        if (index >= 0 && index < animes.size()) {
            animeList.removeAnime(animes.get(index));
            System.out.println("Anime Deleted!");
        } else {
            System.out.println("Invalid index.");
        }
    }

    // EFFECTS: change the watch status of the anime that users want to change.
    // MODIFIES:this
    private void updateWatchStatus() {
        viewAllAnime();
        List<Anime> animes = animeList.getAnimes();
        if (animes.isEmpty()) {
            System.out.println("List is empty, please add animes first!");
            return;
        }
    
        System.out.print("Enter the number of the anime to update: ");
        String input = scanner.nextLine().trim();
    
        int idx = Integer.parseInt(input) - 1;
        if (idx >= 0 && idx < animes.size()) {
            String statusStr = promptStatusString(); 
            setAnimeStatus(animes.get(idx), statusStr);
        } else {
            System.out.println("Invalid index, can't find the anime!");
            updateWatchStatus();
        }
    }

    // EFFECTS: help users to choose multiple types for creating new anime or search
    // anime.
    private List<AnimeType> selectMultipleTypes() {
        // return null; //stub
        List<AnimeType> chosen = new ArrayList<>();
        while (true) {
            printTypeOptions();
            int input = Integer.parseInt(scanner.nextLine().trim());
            if (input == 0) {
                break;
            }
            addSelectedType(chosen, input);
        }
        if (chosen.isEmpty()) {
            System.out.println("Anime should have least one type!!!");
            return selectMultipleTypes();
        }
        return chosen;
    }

    // EFFECT:represent all anime types that users can choose
    private void printTypeOptions() {
        // stub
        System.out.println("\n0 to finish selecting types.");
        // List<AnimeType> vals = new ArrayList<AnimeType>();
        AnimeType[] vals = AnimeType.values();
        for (int i = 0; i < vals.length; i++) {
            System.out.println((i + 1) + ". " + vals[i]);
        }
        System.out.print("Choice: ");
    }

    // EFFECTS: help users to add the types they selected to type list.
    // MODIFIES: chosen
    private void addSelectedType(List<AnimeType> chosen, int input) {
        // stub
        int idx = input - 1;
        AnimeType[] vals = AnimeType.values();
        if (idx >= 0 && idx < vals.length) {
            if (!chosen.contains(vals[idx])) {
                chosen.add(vals[idx]);
            } else {
                System.out.println("You already selected " + vals[idx]);
            }
        } else {
            System.out.println("Invalid choice.");
        }

    }

    // EFFECTS: help users for a valid release time of anime in yyyy-MM format.
    private YearMonth promptYearMonth() {
        // return null; //stub
        final DateTimeFormatter YM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
        while (true) {
            System.out.print("Enter the release date of the anime (yyyy-MM): ");
            String in = scanner.nextLine().trim();
            try {
                return YearMonth.parse(in, YM_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please try again");
            }
        }
    }

    // EFFECTS: a helper for users to a string watch status
    private String promptStatusString() {
        // return null; //stub
        System.out.println("Enter the Watch status (watching, completed, plan to watch): ");
        return scanner.nextLine().trim();
    }

    // EFFECTS: set anime's watching status from string that users entered.
    // MODIFIES: anime
    private boolean setAnimeStatus(Anime anime, String newStatusString) {
        WatchStatus oldStatus = anime.getStatus();

        try {
            anime.setStatus(newStatusString);

            if (anime.getStatus() == oldStatus) {
                System.out.println("You just entered the same status!!!! No Change Made.");
                return false;
            } else {
                System.out.println("Anime Status Updated!");
                return true;
            }
        } catch (StatusException e) {
            System.out.println("Invalid status. No change made.");
            return false;
        }
    }

    // EFFECTS: return anime by filtering the anime types user choosed.
    private void searchByMultipleTypes() {
        // stub
        List<AnimeType> chosenTypes = selectMultipleTypes();

        if (chosenTypes.isEmpty()) {
            System.out.println("No types selected.");
            return;
        }

        List<Anime> results = animeList.searchByTypes(chosenTypes);

        if (results.isEmpty()) {
            System.out.println("No anime found with all of these types: " + chosenTypes);
        } else {
            System.out.println("=== Search Results ===");
            for (Anime anime : results) {
                System.out.println(anime);
            }
        }
    }

    // EFFECTS:return animes by searching the release time of anime
    private void searchByTime() {
        // stub
        YearMonth ym = promptYearMonth();
        List<Anime> res = animeList.searchByTime(ym);
        if (res.isEmpty()) {
            System.out.println("No results.");
        } else {
            System.out.println("\n=== Search Results ===");
            for (Anime a : res) {
                System.out.println(a);
            }
        }
    }

}
