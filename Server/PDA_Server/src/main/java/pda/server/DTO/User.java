package pda.server.DTO;

import java.io.Serializable;


public class User implements Serializable
{
    private static final long serialVersionUID = -66009210435352030L;

    private Integer uId;

    private String name;

    private String id;

    private String passHash;

    private String passSalt;

    private Integer age;

    private Object birth;

    private String email;

    private String kakao;

    private String phone;

    private String profileimg;

    private String introduction;

    private String joinedgroups;

    private String awaitingcertification;


    public Integer getUId()
    {
        return uId;
    }

    public void setUId(Integer uId)
    {
        this.uId = uId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPassHash()
    {
        return passHash;
    }

    public void setPassHash(String passHash)
    {
        this.passHash = passHash;
    }

    public String getPassSalt()
    {
        return passSalt;
    }

    public void setPassSalt(String passSalt)
    {
        this.passSalt = passSalt;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Object getBirth()
    {
        return birth;
    }

    public void setBirth(Object birth)
    {
        this.birth = birth;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getKakao()
    {
        return kakao;
    }

    public void setKakao(String kakao)
    {
        this.kakao = kakao;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getProfileimg()
    {
        return profileimg;
    }

    public void setProfileimg(String profileimg)
    {
        this.profileimg = profileimg;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

    public String getJoinedgroups()
    {
        return joinedgroups;
    }

    public void setJoinedgroups(String joinedgroups)
    {
        this.joinedgroups = joinedgroups;
    }

    public String getAwaitingcertification()
    {
        return awaitingcertification;
    }

    public void setAwaitingcertification(String awaitingcertification)
    {
        this.awaitingcertification = awaitingcertification;
    }

}