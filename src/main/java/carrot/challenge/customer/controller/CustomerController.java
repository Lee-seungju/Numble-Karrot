package carrot.challenge.customer.controller;

import carrot.challenge.customer.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("login")
    public String loginForm() {
        return "customer/login-form";
    }

    @ResponseBody
    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        log.info("email={}, password={}", email, password);

        /*
        Cookie setCookie = new Cookie("id", "2");
        setCookie.setMaxAge(60*60*24*30);
        response.addCookie(setCookie);

        */
        return "post login";
    }

    @GetMapping("add-user")
    public String addUserForm() {
        return "customer/addUser-form";
    }

    @ResponseBody
    @PostMapping("add-user")
    public String addUser(@ModelAttribute Customer customer) {
        log.info("id={}, email={}, password={}, username={}, phone_number={}, nickname={}",
                customer.getId(), customer.getEmail(), customer.getPassword(), customer.getUsername(), customer.getPhone_number(), customer.getNickname());

        return "post users";
    }

    @ResponseBody
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable Long userId) {
        return "update userId=" + userId;
    }
}
