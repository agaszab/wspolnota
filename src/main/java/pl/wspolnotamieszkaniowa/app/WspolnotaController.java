package pl.wspolnotamieszkaniowa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wspolnotamieszkaniowa.mod.Mieszkanie;
import pl.wspolnotamieszkaniowa.mod.Osoba;
import pl.wspolnotamieszkaniowa.mod.Wspolnota;
import pl.wspolnotamieszkaniowa.repository.MieszkanieRepository;
import pl.wspolnotamieszkaniowa.repository.OsobaRepository;
import pl.wspolnotamieszkaniowa.repository.WspolnotaRepository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class WspolnotaController {

    @Autowired
    private WspolnotaRepository wspolnotaRepository;
    private OsobaRepository osobaRepository;
    private MieszkanieRepository mieszkanieRepository;

    public WspolnotaController(OsobaRepository osobaRepository, MieszkanieRepository mieszkanieRepository) {
        this.osobaRepository = osobaRepository;
        this.mieszkanieRepository = mieszkanieRepository;
    }

    @GetMapping ("/wspolnoty")
    public String wspolnoty (Model model)
    { List<Wspolnota> wspolnoty = wspolnotaRepository.findAll();
        model.addAttribute("wspolnoty", wspolnoty);
        return "wspolnota/wspolnoty";}



    @GetMapping("/wspolnota")
    public String info (Model model, @RequestParam long id) {

        Optional<Wspolnota> wspolnotaOptional = wspolnotaRepository.findById(id);

        if(wspolnotaOptional.isPresent()) {
            Wspolnota wspolnota = wspolnotaOptional.get();
            Mieszkanie newMieszkanie = new Mieszkanie();
            newMieszkanie.setWspolnota(wspolnota);
            List<Mieszkanie> mieszkania=wspolnota.getMieszkania();
            List <Osoba>wszyscyMieszkancy=new ArrayList<>();
            List <Osoba>osobyMieszkania=new ArrayList<>();
            List<Mieszkanie> mieszkaniaLuzem = mieszkanieRepository.findAllByPrzypisaneFalse();

            long sumaPowierzchni=0;
            int iluMieszkancow=0;

            for (Mieszkanie m : wspolnota.getMieszkania()) {
                sumaPowierzchni += m.getPowierzchnia_mieszkania();
                osobyMieszkania = m.getMieszkancy();
                iluMieszkancow+=osobyMieszkania.size();
                for (Osoba elem: osobyMieszkania) {
                wszyscyMieszkancy.add(elem);
                }

            }
            model.addAttribute("osobyMieszkania",osobyMieszkania);
            model.addAttribute("wszyscyMieszkancy",wszyscyMieszkancy);
            model.addAttribute("mieszkania",mieszkania);
            model.addAttribute("iluMieszkancow",iluMieszkancow);
            model.addAttribute("sumaPowierzchni",sumaPowierzchni);
            model.addAttribute("wspolnota", wspolnota);
            model.addAttribute("newMieszkanie", newMieszkanie);
            model.addAttribute("mieszkaniaLuzem", mieszkaniaLuzem);
            return "wspolnota/wspolnota";
            }

            else {
            return "redirect:/wspolnota/wspolnoty";
            }
        }




    @GetMapping("/dodajwspolnote")
    public String dodajwspolnote (Model model) {
        model.addAttribute("wspolnota", new Wspolnota());
     return "wspolnota/form_wspolnota";
    }



    @PostMapping ("/addwspolnota")
    public String addWspolnota (Wspolnota wspolnota){
        if (!wspolnota.getAdres_wspolnoty().equals("")) {
            if (wspolnota.getNazwa_wspolnoty().equals(""))
            {wspolnota.setNazwa_wspolnoty(wspolnota.getAdres_wspolnoty());} else {wspolnota.setNazwa_wspolnoty(wspolnota.getNazwa_wspolnoty());}
            wspolnotaRepository.save(wspolnota);
            return "redirect:/wspolnoty";
        } else return "brakdanych";
    }




    @GetMapping("/edycjawspolnoty")
    public String dodajwspolnote (Model model, @RequestParam long id) {
        Optional<Wspolnota> wspolnotaOptional = wspolnotaRepository.findById(id);
        if(wspolnotaOptional.isPresent()) {
            Wspolnota wspolnota = wspolnotaOptional.get();
            model.addAttribute("wspolnota", wspolnota);
            return "wspolnota/edit_wspolnota";
        } else return "error";
    }


    @PostMapping ("/editwspolnota")
    public String editWspolnota (Wspolnota wspolnota, @RequestParam long id) {

        Optional<Wspolnota> wspolnotaOptiona = wspolnotaRepository.findById(id);

        if (wspolnotaOptiona.isPresent()) {
            Wspolnota newWspolnota = wspolnotaOptiona.get();
            if (!wspolnota.getNazwa_wspolnoty().equals("")) { newWspolnota.setNazwa_wspolnoty(wspolnota.getNazwa_wspolnoty()); }
            if (!wspolnota.getAdres_wspolnoty().equals("")) { newWspolnota.setAdres_wspolnoty(wspolnota.getAdres_wspolnoty()); }
            if (!wspolnota.getBudynek().equals("")) { newWspolnota.setBudynek(wspolnota.getBudynek()); }
            wspolnotaRepository.save(newWspolnota);
        }
            return "redirect:/wspolnoty";
        }


    @GetMapping("/delwspolnota")
    public String delwspolnota(Wspolnota wspolnota, @RequestParam long id) {

        Optional<Wspolnota> wspolnotaOptiona = wspolnotaRepository.findById(id);

        if (wspolnotaOptiona.isPresent()) {
            Wspolnota newWspolnota = wspolnotaOptiona.get();
            if (newWspolnota.getMieszkania().size()>0)
                return "wspolnota/nodel";
            else
                wspolnotaRepository.delete(newWspolnota);
        }

        return "redirect:/wspolnoty";
    }

}

