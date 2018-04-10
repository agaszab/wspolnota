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
import pl.wspolnotamieszkaniowa.repository.WspolnotaRepository;


import java.util.List;
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


    @GetMapping ("/wspolnoty")
    public String wspolnoty (Model model)
    { List<Wspolnota> wspolnoty = wspolnotaRepository.findAll();
        model.addAttribute("wspolnoty", wspolnoty);
        return "wspolnoty";}



    @GetMapping("/wspolnota")
    public String info (Model model, @RequestParam long id) {

        Optional<Wspolnota> wspolnotaOptional = wspolnotaRepository.findById(id);


        if(wspolnotaOptional.isPresent()) {
            Wspolnota wspolnota = wspolnotaOptional.get();
            Mieszkanie newMieszkanie = new Mieszkanie();
            newMieszkanie.setWspolnota(wspolnota);
            List <Mieszkanie>mieszkania=wspolnota.getMieszkania();
            long sumaPowierzchni=0;
            int iluMieszkancow=0;

            for (Mieszkanie m : mieszkania) {
                sumaPowierzchni+=m.getPowierzchnia_mieszkania();
                List<Osoba>lm=m.getMieszkancy();
                iluMieszkancow+=lm.size();
                }
            model.addAttribute("iluMieszkancow",iluMieszkancow);
            model.addAttribute("sumaPowierzchni",sumaPowierzchni);
            model.addAttribute("wspolnota", wspolnota);
            model.addAttribute("newMieszkanie", newMieszkanie);
            return "wspolnota";
            }

            else {
            return "redirect:/";
            }
        }




    @GetMapping("/dodajwspolnote")
    public String dodajwspolnote (Model model) {
        model.addAttribute("wspolnota", new Wspolnota());
     return "form_wspolnota";
    }



    @PostMapping ("/addwspolnota")
    public String addWspolnota (Wspolnota wspolnota){
        wspolnotaRepository.save(wspolnota);
        return "redirect:/index";
    }




    @GetMapping("/edycjawspolnoty")
    public String dodajwspolnote (Model model, @RequestParam long id) {
        Optional<Wspolnota> wspolnotaOptional = wspolnotaRepository.findById(id);
        if(wspolnotaOptional.isPresent()) {
            Wspolnota wspolnota = wspolnotaOptional.get();
            model.addAttribute("wspolnota", wspolnota);
            return "edit_wspolnota";
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
            return "wspolnota";
        }




}

