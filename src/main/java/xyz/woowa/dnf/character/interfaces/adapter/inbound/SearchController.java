package xyz.woowa.dnf.character.interfaces.adapter.inbound;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.service.CharacterSearchFacade;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.interfaces.adapter.inbound.form.CharacterForm;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final CharacterSearchFacade characterSearchFacade;

    @ModelAttribute("servers")
    public Server[] servers() {
        return Server.values();
    }


    @GetMapping(params = {"server", "name"})
    public String search(@ModelAttribute("characterForm") @Valid CharacterForm characterForm,
                         BindingResult bindingResult,

                         Model model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }

        String server = characterForm.server();
        String name   = characterForm.name();

        List<BaseDto> characters = searchByMode(server, name);

        if (characters.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("characters", characters);
        return "search/detail";
    }

    private List<BaseDto> searchByMode(String server, String name) {
        if (server.equals("guild")) {
            List<BaseDto> result = characterSearchFacade.guildSearchAll(name);
            log.info("길드 검색 완료 guild={}", name);
            return result;
        }

        if (server.equals("adventure")) {
            List<BaseDto> result = characterSearchFacade.adventureSearchAll(name);
            log.info("모험단 검색 완료 adventure={}", name);
            return result;
        }

        List<BaseDto> result = characterSearchFacade.searchAll(server, name);
        log.info("서버 검색 완료 server={}, name={}", server, name);
        return result;
    }
}
