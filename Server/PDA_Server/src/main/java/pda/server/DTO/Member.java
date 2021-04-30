package pda.server.DTO;

import java.io.Serializable;
import java.util.Date;


public class Member extends User implements Serializable
{
    private static final long serialVersionUID = -62407511042001259L;

    private Integer uId;

    private String id;

    private int isadmin;

    private Date jointime;

    private int usertablenum;

    private String introduction;


    public Integer getUId()
    {
        return uId;
    }

    public void setUId(Integer uId)
    {
        this.uId = uId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getIsadmin()
    {
        return isadmin;
    }

    public void setIsadmin(int isadmin)
    {
        this.isadmin = isadmin;
    }

    public Date getJointime()
    {
        return jointime;
    }

    public void setJointime(Date jointime)
    {
        this.jointime = jointime;
    }

    public int getUsertablenum()
    {
        return usertablenum;
    }

    public void setUsertablenum(int usertablenum)
    {
        this.usertablenum = usertablenum;
    }

    public String getIntroduction()
    {
        return introduction;
    }

    public void setIntroduction(String introduction)
    {
        this.introduction = introduction;
    }

}