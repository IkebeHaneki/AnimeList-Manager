# My Personal Project: Anime List Manager

## What Will the Application do?
- This application is a Anime List Manager that designed to help users to manage their anime watch list.
- Users can add animes by its name,type,release date, and watch status (Watching, Completed, Plan to Watch)
- Users can save their anime list data and reload.
- Users can search the anime they want in their anime list by its type, release date, watch status.


## Who Will Use It?
- Anime lovers who want to organize and track their watched or planned anime.
- Students who develop for a educational purpose.

## Why this project interests me?
- Develop a project can practice my skills to java programming.
- This application can be useful for a practical purpose.
- This can be improved furthur by adding sharing, recommendation in the future development (not in project).


## User Stories
- As a user, I want to add my anime in my list.
- As a user, I want to view my anime list.
- As a user, I want to search the anime I want by filtering.
- As a user, I want to delete the anime I don't want save in my list.
- As a user, I want to update the anime's watch status that keep my list accurate and up-to-date.
- As a user, I want to save my animelist when I quit.
- As a user, I want to reload my saved animelist when I want.
- As a user, I want to save multiple animelists when I need.
- As a user, I want to choose which animelist to be reload when I need.
- As a user, I want to see stat about my anime list by the types.



# Instructions for End User
- You can click **Add Anime** button and enter the anime's name,time,choose the types and watch status, then it will be in the list.
- You can click **Search** button to search anime in your list by time or types(Your choice!), then click **OK** to see the result in new dialog.
- You can click **Update Status** button to update your chosed anime's watch status in your list.
- You can click **Save** and enter the file name that you want to save. Your list will saved as Json document. ATTENTION: Your list must **NOT BE** empty!!
- You can click **Reload** to reload your list by entering the file name that you want to reload.
- You can click **Remove Anime** to remove the anime from you list. Just click the anime you want remove, then click the button below.
- You can click **Show Stats** to see a barchart about how many animes you have by each type.



## Phase 4: Task 2
EVENT LOG:
Sat Mar 29 17:27:16 PDT 2025
Added anime: Your Name

Sat Mar 29 17:27:16 PDT 2025
Added anime: 123

Sat Mar 29 17:27:16 PDT 2025
Added anime: anime

Sat Mar 29 17:27:16 PDT 2025
Added anime: Arknights S1

Sat Mar 29 17:27:16 PDT 2025
Added anime: JOJO

Sat Mar 29 17:27:16 PDT 2025
Added anime: Hajimi

Sat Mar 29 17:27:16 PDT 2025
Added anime: Mujica

Sat Mar 29 17:27:23 PDT 2025
Updated status of Mujica to Completed

Sat Mar 29 17:27:27 PDT 2025
Updated status of Mujica to Plan_to_watch

Sat Mar 29 17:27:32 PDT 2025
Removed anime: Mujica

## Phase 4: Task 3
- One refactoring I would consider is decoupling the event logging from the core model logic. Currently, methods like addAnime and updateStatus directly call EventLog.getInstance().logEvent(...), which ties the model to a specific logging mechanism. Using an Observer which shown in Alarm file in Edx would allow the model to notify listeners of changes without directly coupling to a logging system, making the code more modular and easier to test or extend.
- Another improvement would be to separate UI concerns. Right now, the AnimeListGUI handles both presentation and business logic. Introducing a controller layer to mediate between the view and the model would simplify maintenance and make it easier to update the UI without affecting the model.

## Build and Run

Requirements:

- Java 17 or newer
- Maven 3.9 or newer

Run all tests and create the application JAR:

\`\`\`bash
mvn verify
\`\`\`

Start the graphical interface:

\`\`\`bash
mvn -q exec:java -Dexec.mainClass=ui.Main
\`\`\`

Start the console interface:

\`\`\`bash
mvn -q exec:java -Dexec.mainClass=ui.Main -Dexec.args="--cli"
\`\`\`

Saved lists are written to the local \`data\` directory. File names may contain letters,
numbers, spaces, hyphens, and underscores.

## Continuous Integration

Every push and pull request runs the Maven test suite on GitHub Actions.
