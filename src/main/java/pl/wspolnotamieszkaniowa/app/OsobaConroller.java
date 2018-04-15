package pl.wspolnotamieszkaniowa.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wspolnotamieszkaniowa.mod.Osoba;
import pl.wspolnotamieszkaniowa.mod.Plec;
import pl.wspolnotamieszkaniowa.repository.MieszkanieRepository;
import pl.wspolnotamieszkaniowa.repository.OsobaRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class OsobaConroller {

    private OsobaRepository osobaRepository;
    private MieszkanieRepository mieszkanieRepository;

    public OsobaConroller(OsobaRepository osobaRepository, MieszkanieRepository mieszkanieRepository) {
        this.osobaRepository = osobaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }

    @GetMapping("/osoby")
    public String osoby(Model model) {
        List<Osoba> osoby = osobaRepository.findAll();
        model.addAttribute("osoby", osoby);
        return "osoba/osoby";
    }

    @GetMapping("/osoba")
    public String osoba(Model model, @RequestParam long id) {

        Optional<Osoba> osobaOptional = osobaRepository.findById(id);

        if (osobaOptional.isPresent()) {
            Osoba osoba = osobaOptional.get();
            model.addAttribute("osoba", osoba);

        } else {
            return "redirect:/";
        }

        return "osoba/osoba";
    }

    @GetMapping("/dodajosobe")
    public String dodajosobe(Model model) {
        model.addAttribute("newOsoba", new Osoba());
        model.addAttribute("plec", Plec.values());
        return "osoba/form_osoba";
    }

    @PostMapping("/addosoba")
    public String addOsoba(Osoba osoba) {
        if (!osoba.getNazwisko().equals("") && !osoba.getImie().equals("")) {
            osobaRepository.save(osoba);
            return "redirect:/osoby";
        } else return "brakdanych";
    }

    @GetMapping("/edycjaosoby")
    public String edytujosoba(@RequestParam Long id, Model model) {
        Osoba osoba = osobaRepository.getOne(id);
        model.addAttribute("osoba", osoba);
        model.addAttribute("plec", Plec.values());
        return "osoba/edit_osoba";
    }

    @PostMapping("/editosoba")
    public String editosoba(Osoba osoba, @RequestParam long id) {
        Optional<Osoba> osobaOptional = osobaRepository.findById(id);
        if (osobaOptional.isPresent()) {
            Osoba newOsoba = osobaOptional.get();
            if (!osoba.getImie().equals("")) {
                newOsoba.setImie(osoba.getImie());
            }
            if (!osoba.getNazwisko().equals("")) {
                newOsoba.setNazwisko(osoba.getNazwisko());
            }
            if (!osoba.getPlec().equals("")) {
                newOsoba.setPlec(osoba.getPlec());
            }
            osobaRepository.save(newOsoba);
        }
        return "redirect:/osoby";
    }


    @GetMapping("/delosobaMieszkania")
    public String kasujosobemieszkania(Osoba osoba, @RequestParam Long id) {

        Optional<Osoba> osobaOptional = osobaRepository.findById(id);
        if (osobaOptional.isPresent()) {
            Osoba newOsoba = osobaOptional.get();
            newOsoba.setMieszkanie(null);
            osobaRepository.save(newOsoba);
        }
        return "redirect:/osoby";
    }

    @GetMapping("/delosoba")
    public String kasujosobe(@RequestParam Long id) {

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
