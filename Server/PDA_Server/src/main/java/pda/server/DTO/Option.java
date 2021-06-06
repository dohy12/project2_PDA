package pda.server.DTO;

public class Option {
    int O_ID;
    String contents;
    int S_ID;

    public Option(int O_ID, String contents, int S_ID) {
        this.O_ID = O_ID;
        this.contents = contents;
        this.S_ID = S_ID;
    }
}
