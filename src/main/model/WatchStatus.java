package model;

import exception.StatusException;

// Represents the watch status of an anime.
public enum WatchStatus {
    Watching("Watching"),
    Completed("Completed"),
    Plan_to_watch("Plan to Watch");

    private final String displayName;

    WatchStatus(String displayName) {
        this.displayName = displayName;
    }

    public static WatchStatus fromInput(String input) throws StatusException {
        if (input == null) {
            throw new StatusException("null");
        }

        String normalized = input.trim().replace("_", " ").toLowerCase();
        switch (normalized) {
            case "watching":
                return Watching;
            case "completed":
                return Completed;
            case "plan to watch":
                return Plan_to_watch;
            default:
                throw new StatusException(input);
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
