package pda.server.DTO;

public class UserPW {
    private String U_ID;
    private String pass_Hash;
    private String pass_Salt;
    private String profileImg;

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getU_ID() {
        return U_ID;
    }

    public void setU_ID(String u_ID) {
        U_ID = u_ID;
    }

    public String getPass_Hash() {
        return pass_Hash;
    }

    public void setPass_Hash(String pass_Hash) {
        this.pass_Hash = pass_Hash;
    }

    public String getPass_Salt() {
        return pass_Salt;
    }

    public void setPass_Salt(String pass_Salt) {
        this.pass_Salt = pass_Salt;
    }
}
