package pda.server.Handler;

public class UserTableMapping
{
    public static int UIDConversion(int U_ID)
    {
        return U_ID / (2147483647 / 10);
    }
}
