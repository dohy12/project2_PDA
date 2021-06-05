package pda.server.DTO;

public class group {
    private String Name;
    private String GroupId;
    private String GroupImg;

    public String getGroupImg() {
        return GroupImg;
    }

    public void setGroupImg(String groupImg) {
        GroupImg = groupImg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
}
