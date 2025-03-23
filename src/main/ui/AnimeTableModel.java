package ui;

import model.Anime;
import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

//Construct a table for displaying Anime, showing columns for Name, Release Date, Status and Types
public class AnimeTableModel extends AbstractTableModel {
    private static final String[] COLS = {
            "Name", "Release Date", "Watch Status", "Types"
    };
    private List<Anime> animes;
    private final DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM");

    //MODIFIES:this
    //EFFECTS:Constructs a new table with give anime list
    public AnimeTableModel(List<Anime> animes) {
        this.animes = animes;
    }

    //EFFECTS:Returns the number of rows (size of animes list).
    @Override
    public int getRowCount() {
        return animes.size();
    }

    //EFFECTS:Returns the number of columns
    @Override
    public int getColumnCount() {
        return COLS.length;
    }


     //REQUIRES: 0<= int <= 3
    //EFFECTS:Returns the name of the column at the given index
    @Override
    public String getColumnName(int col) {
        return COLS[col];
    }

    //EFFECTS:Returns the value to display
    @Override
    public Object getValueAt(int row, int col) {
        Anime a = animes.get(row);
        switch (col) {
            case 0:
                return a.getName();
            case 1:
                return a.getTime().format(fm);
            case 2:
                return a.getStatus();
            case 3:
                return String.join(
                        ", ",
                        a.getTypes().stream().map(Enum::name).toArray(String[]::new));
            default:
                return "";
        }
    }

    //REQUIRES: list != null
    //EFFECTS:Replaces the current list of animes with the given list and notifies the table that data changed.
    public void setAnimes(List<Anime> list) {
        animes = list;
        fireTableDataChanged();
    }

    //REQUIRES: 0 <= row < 4
    //EFFECTS:Returns the Anime object at the given row index.
    public Anime getAnimeAt(int row) {
        return animes.get(row);
    }

}
