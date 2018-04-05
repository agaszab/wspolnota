package pl.wspolnotamieszkaniowa.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wspolnotamieszkaniowa.mod.Mieszkanie;
import pl.wspolnotamieszkaniowa.repository.MieszkanieRepository;
import pl.wspolnotamieszkaniowa.repository.OsobaRepository;
import pl.wspolnotamieszkaniowa.repository.WspolnotaRepository;

@Controller
public class MieszkanieController {

    private MieszkanieRepository mieszkanieRepository;
    private WspolnotaRepository wspolnotaRepository;
    private OsobaRepository osobaRepository;

    public MieszkanieController (WspolnotaRepository wspolnotaRepository, OsobaRepository osobaRepository) {
        this.wspolnotaRepository = wspolnotaRepository;
        this.osobaRepository = osobaRepository;
    }

    @PostMapping("/addmieszkanie")
    public String addMieszkanie (Mieszkanie mieszkanie){
        mieszkanieRepository.save(mieszkanie);
        return "redirect:/wspolnota?id=" + mieszkanie.getWspolnota();
    }


}
