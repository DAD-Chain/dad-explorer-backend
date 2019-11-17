package com.github.dadchain.service;

import com.github.dadchain.util.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 管理用户的会话
 */
@Slf4j
@Service
public class SessionService {

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    public String getUserId(String sessionId) {
        Session session = sessionRepository.findById(sessionId);
        if (session != null) {
            return session.getAttribute("uid");
        } else {
            log.error("session not found, id " + sessionId);
        }
        return null;
    }

    public List<String> getSessionIds(String uid) {
        Map<String, Session> map = getSessions(uid);

        List<String> list = new LinkedList<>();
        if (map != null) {
            list.addAll(map.keySet());
        }
        return list;
    }

    private Map<String, Session> getSessions(String uid) {
        return sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, uid);
    }

    public void updateUserIdInSession(HttpSession session, String uid) {
        if (session != null) {
            session.setAttribute("uid", uid);
            session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, uid);
        }
    }

    public void updateEmailInSession(HttpSession session, String email) {
        if (session != null) {
            session.setAttribute("email", email);
        }
    }

    public void clearUserId(HttpSession session) {
        if (session != null) {
            session.removeAttribute("uid");
            session.removeAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        }
    }

    public void clearSessions(String uid) {
        List<String> sessionIds = getSessionIds(uid);
        if (sessionIds != null) {
            sessionIds.stream().forEach(sessionId -> {
                Session session = sessionRepository.findById(sessionId);
                if (session != null) {
                    session.removeAttribute("uid");
                    session.removeAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
                    sessionRepository.save(session);
                }
            });
        }
    }

    public String verifyLogin(HttpSession session) {
        String uid = (String) session.getAttribute("uid");
        if (StringUtils.isEmpty(uid)) {
            throw new RuntimeException(ErrorInfo.USER_NOT_LOGGED_IN.name());
        }
        return uid;
    }

    public void updateTokenInSession(HttpSession session, String token) {
        if (session != null) {
            session.setAttribute("token", token);
        }
    }

    // token 部分
    public String ensureTokenExist(HttpSession session) {
        if (session != null) {
            String token = (String) session.getAttribute("token");
            if (StringUtils.isEmpty(token)) {
                throw new RuntimeException(ErrorInfo.TOKEN_NOT_EXIST_IN_SESSION.name());
            }

            return token;

        }
        throw new RuntimeException(ErrorInfo.TOKEN_NOT_EXIST_IN_SESSION.name());
    }
}
