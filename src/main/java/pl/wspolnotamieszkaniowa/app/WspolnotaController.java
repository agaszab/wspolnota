package pl.wspolnotamieszkaniowa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wspolnotamieszkaniowa.mod.Mieszkanie;
import pl.wspolnotamieszkaniowa.mod.Wspolnota;
import pl.wspolnotamieszkaniowa.repository.MieszkanieRepository;
import pl.wspolnotamieszkaniowa.repository.WspolnotaRepository;
import java.util.Optional;

@Controller
public class WspolnotaController {

    @Autowired
    private WspolnotaRepository wspolnotaRepository;
    private MieszkanieRepository mieszkanieRepository;

    public WspolnotaController (WspolnotaRepository wspolnotaRepository, MieszkanieRepository mieszkanieRepository) {
        this.wspolnotaRepository = wspolnotaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }


    @GetMapping("/wspolnota")
    public String info (Model model, @RequestParam long id) {

        Optional<Wspolnota> wspolnotaOptional = wspolnotaRepository.findById(id);

        if(wspolnotaOptional.isPresent()) {
            Wspolnota wspolnota = wspolnotaOptional.get();
            Mieszkanie newMieszkanie = new Mieszkanie();
            newMieszkanie.setWspolnota(wspolnota);

            model.addAttribute("wspolnota", wspolnota);
            model.addAttribute("newMieszkanie", newMieszkanie);
        } else {
            return "redirect:/";
        }

        return "wspolnota";
    }


    @GetMapping("/dodajwspolnote")
    public String info (Model model) {
        model.addAttribute("wspolnota", new Wspolnota());
     return "form_wspolnota";
    }
}

