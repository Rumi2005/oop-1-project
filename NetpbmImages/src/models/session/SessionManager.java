package models.session;

public class SessionManager {
    private static Session currentSession;

    public static Session getCurrentSession() {
        return currentSession;
    }

    public static void setCurrentSession(Session session) {
        currentSession = session;
    }

    public static void closeSession() {
        currentSession = null;
    }

    public static boolean hasSession() {
        return currentSession != null;
    }
}
