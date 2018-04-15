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

    public MieszkanieController(WspolnotaRepository wspolnotaRepository, OsobaRepository osobaRepository, MieszkanieRepository mieszkanieRepository) {
        this.wspolnotaRepository = wspolnotaRepository;
        this.osobaRepository = osobaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }


    @GetMapping("/mieszkania")
    public String mieszkania(Model model) {
        List<Mieszkanie> mieszkania = mieszkanieRepository.findAll();
        model.addAttribute("mieszkania", mieszkania);
        return "mieszkanie/mieszkania";
    }

    @GetMapping("/dodajmieszkanie")
    public String dodajosobe(Model model) {

        List<Wspolnota> wspolnoty = new ArrayList<>();
        wspolnoty = wspolnotaRepository.findAll();
        model.addAttribute("newMieszkanie", new Mieszkanie());
        model.addAttribute("wspolnoty", wspolnoty);
        return "mieszkanie/form_mieszkanie";
    }

    @PostMapping("/addmieszkanie")
    public String addMieszkanie(Mieszkanie mieszkanie) {
        if ( !mieszkanie.getUlica().equals("") && !mieszkanie.getNumer_mieszkania().equals("")) {
            mieszkanieRepository.save(mieszkanie);
            return "redirect:/mieszkania";
        }

        return "brakdanych";
    }

    @PostMapping("/addmieszkanieluz")
    public String addMieszkanie(Mieszkanie mieszkanie, Wspolnota wspolnota, @RequestParam long id) {
        Optional<Mieszkanie> mieszkanieOptiona = mieszkanieRepository.findById(id);
        if (mieszkanieOptiona.isPresent()) {
            Mieszkanie newMieszkanie = mieszkanieOptiona.get();
            newMieszkanie.setPrzypisane(true);
            newMieszkanie.setWspolnota(wspolnota);
            mieszkanieRepository.save(newMieszkanie);
            return "potwierdzenie";
        }
        return "error";
    }

    @GetMapping("/mieszkanie")
    public String mieszkanie(Model model, @RequestParam long id) {

        Optional<Mieszkanie> mieszkanieOptional = mieszkanieRepository.findById(id);

        if (mieszkanieOptional.isPresent()) {
            Mieszkanie mieszkanie = mieszkanieOptional.get();
            List<Osoba> osoby = new ArrayList<>();
            osoby = mieszkanie.getMieszkancy();
            Osoba newOsoba = new Osoba();
            newOsoba.setMieszkanie(mieszkanie);

            model.addAttribute("mieszkanie", mieszkanie);
            model.addAttribute("newOsoba", newOsoba);
            model.addAttribute("osoby", osoby);
            model.addAttribute("plec", Plec.values());
        } else {
            return "redirect:/mieszkania";
        }

        return "mieszkanie/mieszkanie";
    }


    @GetMapping("/delmieszkanieWspolnota")
    public String mieszkaniedel(Mieszkanie mieszkanie, @RequestParam long id) {

        Optional<Mieszkanie> mieszkanieOptiona = mieszkanieRepository.findById(id);

        if (mieszkanieOptiona.isPresent()) {
            Mieszkanie newMieszkanie = mieszkanieOptiona.get();
            newMieszkanie.setWspolnota(null);
            newMieszkanie.setPrzypisane(false);
            mieszkanieRepository.save(newMieszkanie);
        }

        return "wspolnota/wspolnota";
    }

    @GetMapping("/delmieszkanie")
    public String del(Mieszkanie mieszkanie, @RequestParam long id) {

        Optional<Mieszkanie> mieszkanieOptiona = mieszkanieRepository.findById(id);

        if (mieszkanieOptiona.isPresent()) {
          Mieszkanie newMieszkanie = mieszkanieOptiona.get();
          if (newMieszkanie.getMieszkancy().size()>0)
              return "mieszkanie/nodel";
          else
            mieszkanieRepository.delete(newMieszkanie);
        }

        return "redirect:/mieszkania";
    }


    @GetMapping("/edycjamieszkania")
    public String edycjaMieszkania (Model model, @RequestParam long id) {
        Optional<Mieszkanie> mieszkanieOptional = mieszkanieRepository.findById(id);
        if (mieszkanieOptional.isPresent()) {
            Mieszkanie newMieszkanie = mieszkanieOptional.get();
            model.addAttribute("newMieszkanie", newMieszkanie);
            return "mieszkanie/edit_mieszkanie";
        } else return "error";
    }

    @PostMapping("/editmieszkanie")
    public String editMieszkanie(Mieszkanie mieszkanie, @RequestParam long id_mieszkania) {

        Optional<Mieszkanie> mieszkanieOptiona = mieszkanieRepository.findById(id_mieszkania);

        if (mieszkanieOptiona.isPresent()) {
            Mieszkanie newMieszkanie = mieszkanieOptiona.get();
            if (!mieszkanie.getUlica().equals("")) {
                newMieszkanie.setUlica(mieszkanie.getUlica());
            }
            if (!mieszkanie.getNumer_mieszkania().equals("")) {
                newMieszkanie.setNumer_mieszkania(mieszkanie.getNumer_mieszkania());
            }
            newMieszkanie.setPowierzchnia_mieszkania(mieszkanie.getPowierzchnia_mieszkania());
            mieszkanieRepository.save(newMieszkanie);

        }
        return "redirect:/mieszkania";
    }
}


