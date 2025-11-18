package xyz.woowa.dnf.character.interfaces.adapter.inbound;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.woowa.dnf.character.application.command.service.CharacterRefreshFacade;
import xyz.woowa.dnf.character.application.query.dto.CharacterDto;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.interfaces.adapter.inbound.form.CharacterForm;


@Slf4j
@Controller
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterRefreshFacade characterRefreshFacade;

    @ModelAttribute("servers")
    public Server[] servers() {
        return Server.values();
    }

    @GetMapping("/{server}/{id}")
    public String detail(@PathVariable("server") String serverId,
                          @PathVariable("id") String id,
                          Model model) {
        CharacterDto character = characterRefreshFacade.character(serverId, id);
        CharacterForm characterForm = new CharacterForm(character.getServerId(), character.getName());

        model.addAttribute("characterForm", characterForm);
        model.addAttribute("character", character);
        return "characters/detail";
    }


    @PostMapping("/{server}/{id}/refresh")
    public String refresh(@PathVariable String server,
                          @PathVariable String id) {

        characterRefreshFacade.refresh(server, id);
        return "redirect:/characters/" + server + "/" + id;
    }
}
