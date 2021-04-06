package pda.server;

import org.springframework.util.Assert;

public class RoutingDatabaseContextHolder {

    private static ThreadLocal<String> CONTEXT = new ThreadLocal<>();


    public static void set(String clientDatabase) {

        Assert.notNull(clientDatabase, "RoutingDatabase cannot be null");
        CONTEXT.set(clientDatabase);
    }


    public static String getClientDatabase() {
        return CONTEXT.get();
    }


    public static void clear() {
        CONTEXT.remove();
    }
}
