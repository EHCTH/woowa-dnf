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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.woowa.dnf.character.application.query.dto.base.BaseDto;
import xyz.woowa.dnf.character.application.query.service.CharacterSearchFacade;
import xyz.woowa.dnf.character.domain.base.Server;
import xyz.woowa.dnf.character.interfaces.adapter.inbound.form.CharacterForm;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/search")
public class SearchController {
    private final CharacterSearchFacade characterSearchFacade;
    private final Map<String, SearchStrategy> strategies;

    public SearchController(CharacterSearchFacade characterSearchFacade) {
        this.characterSearchFacade = characterSearchFacade;
        this.strategies = Map.of(
                Server.GUILD.getEnglish(), (server, name) -> characterSearchFacade.guildSearchAll(name),
                Server.ADVENTURE.getEnglish(), (server, name) -> characterSearchFacade.adventureSearchAll(name)
        );
    }

    @FunctionalInterface
    interface SearchStrategy {
        List<BaseDto> search(String server, String name);
    }

    @ModelAttribute("servers")
    public Server[] servers() {
        return Server.values();
    }

    @GetMapping(params = {"server", "name"})
    public String search(@ModelAttribute("characterForm") @Valid CharacterForm characterForm,
                         RedirectAttributes redirectAttributes,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("server", characterForm.server());
            redirectAttributes.addAttribute("name", characterForm.name());
            return "redirect:/";
        }
        String server = characterForm.server();
        String name = characterForm.name();

        List<BaseDto> characters = searchByMode(server, name);

        if (characters.isEmpty()) {
            redirectAttributes.addAttribute("server", server);
            redirectAttributes.addAttribute("name", name);
            return "redirect:/";
        }
        model.addAttribute("characters", characters);
        return "search/detail";
    }

    private List<BaseDto> searchByMode(String server, String name) {
        SearchStrategy searchStrategy = strategies.getOrDefault(server, characterSearchFacade::searchAll);
        List<BaseDto> result = searchStrategy.search(server, name);
        return logWithResultHandler(result, server, name);
    }

    private List<BaseDto> logWithResultHandler(List<BaseDto> result, String server, String name) {
        if (result.isEmpty()) {
            log.info("검색 결과 없음 server={}, name={}", server, name);
            return Collections.emptyList();
        }
        log.info("검색 완료 server={}, name={}", server, name);
        return result;
    }
}
