package pl.wspolnotamieszkaniowa.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wspolnotamieszkaniowa.mod.Mieszkanie;
import pl.wspolnotamieszkaniowa.mod.Osoba;
import pl.wspolnotamieszkaniowa.mod.Plec;
import pl.wspolnotamieszkaniowa.mod.Wspolnota;
import pl.wspolnotamieszkaniowa.repository.MieszkanieRepository;
import pl.wspolnotamieszkaniowa.repository.OsobaRepository;
import pl.wspolnotamieszkaniowa.repository.WspolnotaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MieszkanieController {

    private MieszkanieRepository mieszkanieRepository;
    private WspolnotaRepository wspolnotaRepository;
    private OsobaRepository osobaRepository;

    public MieszkanieController (WspolnotaRepository wspolnotaRepository, OsobaRepository osobaRepository, MieszkanieRepository mieszkanieRepository) {
        this.wspolnotaRepository = wspolnotaRepository;
        this.osobaRepository = osobaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }


    @GetMapping("/mieszkania")
    public String mieszkania (Model model)
    { List<Mieszkanie> mieszkania = mieszkanieRepository.findAll();
        model.addAttribute("mieszkania", mieszkania);
        return "mieszkania";}

    @GetMapping("/dodajmieszkanie")
    public String dodajosobe (Model model) {

        List<Wspolnota>wspolnoty=new ArrayList<>();
        wspolnoty=wspolnotaRepository.findAll();
        model.addAttribute("newMieszkanie", new Mieszkanie());
        model.addAttribute("wspolnoty", wspolnoty);
        return "form_mieszkanie";
    }

    @PostMapping("/addmieszkanie")
    public String addMieszkanie (Mieszkanie mieszkanie){
        mieszkanieRepository.save(mieszkanie);
        return "potwierdzenie";
    }

    @GetMapping("/mieszkanie")
    public String mieszkanie (Model model, @RequestParam long id) {

        Optional<Mieszkanie> mieszkanieOptional = mieszkanieRepository.findById(id);

        if(mieszkanieOptional.isPresent()) {
            Mieszkanie mieszkanie = mieszkanieOptional.get();
            List <Osoba> osoby= new ArrayList<>();
            osoby=mieszkanie.getMieszkancy();
            Osoba newOsoba = new Osoba();
            newOsoba.setMieszkanie(mieszkanie);

            model.addAttribute("mieszkanie", mieszkanie);
            model.addAttribute("newOsoba", newOsoba);
            model.addAttribute("osoby", osoby);
        } else {
            return "redirect:/";
        }

        return "mieszkanie";
    }
}
