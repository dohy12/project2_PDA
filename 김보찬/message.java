public class message{
    private String sender_name;
    private String reciever_name;
    private String sender_ID;
    private String reciever_ID;
    private String read_info;
    private String contents;

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getReciever_name() {
        return reciever_name;
    }

    public void setReciever_name(String reciever_name) {
        this.reciever_name = reciever_name;
    }

    public String getSender_ID() {
        return sender_ID;
    }

    public void setSender_ID(String sender_ID) {
        this.sender_ID = sender_ID;
    }

    public String getReciever_ID() {
        return reciever_ID;
    }

    public void setReciever_ID(String reciever_ID) {
        this.reciever_ID = reciever_ID;
    }

    public String getRead_info() {
        return read_info;
    }

    public void setRead_info(String read_info) {
        this.read_info = read_info;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}