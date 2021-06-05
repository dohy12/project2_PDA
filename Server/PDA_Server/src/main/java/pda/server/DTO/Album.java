package pda.server.DTO;

import java.time.LocalDate;

public class Album {
    private int a_id;
    private String title;
    private LocalDate date;
    private String location;
    private String album_intro;

    public int getA_id() {
        return a_id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getAlbum_intro() {
        return album_intro;
    }

}
