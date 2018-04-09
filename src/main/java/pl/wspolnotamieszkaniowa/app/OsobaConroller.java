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

import java.util.List;
import java.util.Optional;

@Controller
public class OsobaConroller {

    private OsobaRepository osobaRepository;
    private MieszkanieRepository mieszkanieRepository;

    public OsobaConroller(OsobaRepository osobaRepository,  MieszkanieRepository mieszkanieRepository) {
        this.osobaRepository = osobaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }

    @GetMapping("/osoby")
    public String osoby (Model model) {
        List<Osoba> osoby = osobaRepository.findAll();
        model.addAttribute("osoby", osoby);
        return "osoby";
    }

    @GetMapping("/osoba")
    public String osoba (Model model, @RequestParam long id) {

        Optional<Osoba> osobaOptional = osobaRepository.findById(id);

        if(osobaOptional.isPresent()) {
            Osoba osoba=osobaOptional.get();
            model.addAttribute("osoba", osoba);

        } else {
            return "redirect:/";
        }

        return "osoba";
    }

    @GetMapping("/dodajosobe")
    public String dodajosobe (Model model) {
        model.addAttribute("newOsoba", new Osoba());
        model.addAttribute("plec", Plec.values());
        return "form_osoba";
    }

    @PostMapping("/addosoba")
    public String addMieszkanie (Osoba osoba){
        osobaRepository.save(osoba);
       // return "redirect:/mieszkanie?id=" + osoba.getId_osoby();
        return "potwierdzenie";
    }

    @GetMapping("/editosoba")
    public String editosoba(@RequestParam Long id, Model model){
        Osoba osoba = osobaRepository.getOne(id);
        List<Mieszkanie> mieszkania = mieszkanieRepository.findAll();
        model.addAttribute("mieszkania", mieszkania);
        model.addAttribute("osoba",osoba);
        return "formedit_osoba";
    }

    @GetMapping("/delosoba")
    public String kasujosobe (@RequestParam Long id) {

        Optional<Osoba> osobaOptional = osobaRepository.findById(id);
        if (osobaOptional.isPresent()) {
            Osoba osoba = osobaOptional.get();
            osobaRepository.delete(osoba);
        } //else {
          //  return "error";
       // }
        return "redirect:/osoby";
    }


}
