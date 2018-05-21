package tool;

import interceptor.SessionManageListener;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class ForceLogoutUtils {
    public static void forceUserLogout(String username) {
        if (SessionManageListener.sessionMap.get(username) != null) {
            HttpSession session = SessionManageListener.sessionMap.get(username);

            SessionManageListener.sessionMap.remove(username);

            Enumeration e = session.getAttributeNames();

            while (e.hasMoreElements()) {
                String sessionName = (String) e.nextElement();

                session.removeAttribute(sessionName);
            }
            session.invalidate();
        }
    }
}
