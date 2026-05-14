package models.session;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static Session currentSession;
    private static final Map<Integer, Session> sessions = new HashMap<>();

    public static Session getCurrentSession() {
        return currentSession;
    }

    public static void setCurrentSession(Session session) {
        currentSession = session;
        sessions.put(session.getId(), session);
    }

    public static boolean hasSession() {
        return currentSession != null;
    }

    public static void closeSession() {
        currentSession = null;
    }

    public static boolean switchSession(int id) {
        Session session = sessions.get(id);
        if (session == null)
            return false;
        currentSession = session;
        return true;
    }
}
