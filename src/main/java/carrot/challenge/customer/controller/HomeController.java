package carrot.challenge.customer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class HomeController {

    @GetMapping
    public String home(HttpServletRequest request, HttpSession session) {
        Long getId = getIdFromCookie(request);
        log.info("cookies={}", getId);
        return "index";
    }

    Long getIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    return Long.parseLong(cookie.getValue());
                }
            }
        }
        return null;
    }
}
