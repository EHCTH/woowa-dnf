package xyz.woowa.dnf.character.interfaces.adapter.inbound;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.interfaces.adapter.inbound.form.CharacterForm;

@Controller
public class HomeController {
    @ModelAttribute("servers")
    public Server[] servers() {
        return Server.values();
    }

    @GetMapping("/")
    public String home(@ModelAttribute CharacterForm characterForm) {
        return "home";
    }
}
