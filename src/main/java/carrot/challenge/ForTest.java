package carrot.challenge;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForTest {

    @GetMapping()
    public String test() {
        return "index";
    }
}
