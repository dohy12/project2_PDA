package pda.server.DTO;

import java.util.Date;

public class Message {
    private int M_ID;
    private int receive_UID;
    private Date send_Date;
    private Date read_Date;
    private String title;
    private String contents;
    private int send_UID;

    public int getM_ID() {
        return M_ID;
    }

    public void setM_ID(int m_ID) {
        M_ID = m_ID;
    }

    public int getReceive_UID() {
        return receive_UID;
    }

    public void setReceive_UID(int receive_UID) {
        this.receive_UID = receive_UID;
    }

    public Date getSend_Date() {
        return send_Date;
    }

    public void setSend_Date(Date send_Date) {
        this.send_Date = send_Date;
    }

    public Date getRead_Date() {
        return read_Date;
    }

    public void setRead_Date(Date read_Date) {
        this.read_Date = read_Date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getSend_UID() {
        return send_UID;
    }

    public void setSend_UID(int send_UID) {
        this.send_UID = send_UID;
    }
}
