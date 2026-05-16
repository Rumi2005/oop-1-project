package models.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Управлява всички активни сесии.
 * Следи текущата активна сесия.
 */
public class SessionManager {
    /**
     * Текущата активна сесия.
     */
    private static Session currentSession;
    /**
     * Карта с всички сесии.
     */
    private static final Map<Integer, Session> sessions = new HashMap<>();

    /**
     * Връща текущата активна сесия.
     *
     * @return текущата сесия
     */
    public static Session getCurrentSession() {
        return currentSession;
    }

    /**
     * Задава текущата активна сесия.
     *
     * @param session новата активна сесия
     */
    public static void setCurrentSession(Session session) {
        currentSession = session;
        sessions.put(session.getId(), session);
    }

    /**
     * Проверява дали има активна сесия.
     *
     * @return true ако съществува активна сесия
     */
    public static boolean hasSession() {
        return currentSession != null;
    }

    /**
     * Затваря текущата сесия.
     * Премахва я от активните сесии.
     */
    public static void closeSession() {
        currentSession = null;
    }

    /**
     * Превключва към избрана сесия.
     *
     * @param id идентификатор на сесията
     * @return true при успешно превключване
     */
    public static boolean switchSession(int id) {
        Session session = sessions.get(id);
        if (session == null)
            return false;
        currentSession = session;
        return true;
    }
}
