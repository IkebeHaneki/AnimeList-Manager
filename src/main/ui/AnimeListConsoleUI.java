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
    

    // EFFECTS: construct the ui and start the users menu
    public AnimeListConsoleUI() throws NumberException {
        //stub
    }
    // EFFECTS:run the main menu loop until users exit.

    private void runApp() throws NumberException {
        //stub
    }

    // EFFECTS: handle users choice to do functions on the application
    private boolean handleUserChoice(String choice) throws NumberException {
        return true; //stub
    }

    // EFFECTS: display the users menu loop

    private void displayMenu() {
        //stub
    }

    // EFFECTS: add a new anime(name,types, release time, status) to the anime list
    // MODIFIES: this
    private void addAnime() {
        //stub
    }

    // EFFECTS: represent all animes in the list

    private void viewAllAnime() {
        //stub
    }

    // EFFECTS: Let users to choose the way to search anime from the list.

    private void searchAnime() {
        //stub
    }

    // EFFECTS: delete the anime that users want to remove from the list
    // MODEFIES:this
    private void deleteAnime() throws NumberException {
        //stub
    }

    // EFFECTS: change the watch status of the anime that users want to change.
    // MODIFIES:this
    private void updateWatchStatus() {
        //stub
    }

    // EFFECTS: help users to choose multiple types for creating new anime or search
    // anime.
    private List<AnimeType> selectMultipleTypes() {
        return null; //stub
    }

    // EFFECT:represent all anime types that users can choose
    private void printTypeOptions() {
        //stub
    }

    // EFFECTS: help users to add the types they selected to type list.
    // MODIFIES: chosen
    private void addSelectedType() {
        //stub

    }

    // EFFECTS: help users for a valid release time of anime in yyyy-MM format.
    private YearMonth promptYearMonth() {
        return null; //stub
    }

    // EFFECTS: a helper for users to a string watch status
    private String promptStatusString() {
        return null; //stub
    }

    // EFFECTS: set anime's watching status from string that users entered.
    // MODIFIES: anime
    private boolean setAnimeStatus(Anime anime, String status) {
        return true; //stub
    }

    // EFFECTS: return anime by filtering the anime types user choosed.
    private void searchByMultipleTypes() {
        //stub
    }

    // EFFECTS:return animes by searching the release time of anime
    private void searchByTime() {
        //stub
    }

}
