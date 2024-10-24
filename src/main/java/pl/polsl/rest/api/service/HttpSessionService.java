package pl.polsl.rest.api.service;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.session.Session;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import static java.util.Objects.nonNull;

@Service
public class HttpSessionService {

    private final static String USER_ID_ATTR = "USER_ID";
    private final static String USER_NAME_ATTR = "USER_NAME";
    private final static String LOGGED_IN_ATTR = "LOGGED_IN";
    private static final String SESSION_HEADER = "x-auth-token";

    private final JdbcIndexedSessionRepository sessionRepository;

    public HttpSessionService(final JdbcIndexedSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void bindSessionToUser(final HttpSession session, final Integer userId, final String userName) {
        session.setAttribute(USER_ID_ATTR, userId);
        session.setAttribute(USER_NAME_ATTR, userName);
        session.setAttribute(LOGGED_IN_ATTR, true);
    }

    public boolean isLoggedIn(final HttpSession session) {
        final Object loggedInAttribute = session.getAttribute(LOGGED_IN_ATTR);
        return checkIfLogged(loggedInAttribute);
    }

    private boolean checkIfLogged(final Object loggedInAttribute) {
        return nonNull(loggedInAttribute)
                && loggedInAttribute instanceof Boolean isLogged
                && isLogged.equals(Boolean.TRUE);
    }

    public void invalidateSession(final HttpSession session) {
        session.removeAttribute(USER_ID_ATTR);
        session.removeAttribute(LOGGED_IN_ATTR);
        session.removeAttribute(USER_NAME_ATTR);
        session.invalidate();
    }

    public Session findSession(final String xAuthToken) {
        return sessionRepository.findById(xAuthToken);
    }

    public boolean isLoggedIn(final String sessionId) {
        return isLoggedIn(findSession(sessionId));
    }

    public boolean isLoggedIn(final Session session) {
        if (nonNull(session)) {
            final Boolean loggedInAttribute = session.getAttribute(LOGGED_IN_ATTR);
            return checkIfLogged(loggedInAttribute);
        }
        return false;
    }

    public Integer getUserId(final HttpSession session) {
        return nonNull(session)
                ? (Integer) session.getAttribute(USER_ID_ATTR)
                : null;
    }

    public String getUserName(final HttpSession session) {
        return nonNull(session)
                ? (String) session.getAttribute(USER_NAME_ATTR)
                : null;
    }



}
