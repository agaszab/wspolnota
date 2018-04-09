package pl.wspolnotamieszkaniowa.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.wspolnotamieszkaniowa.mod.Wspolnota;
import pl.wspolnotamieszkaniowa.repository.WspolnotaRepository;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private WspolnotaRepository wspolnotaRepository;

    public HomeController (WspolnotaRepository wspolnotaRepository) {
        this.wspolnotaRepository = wspolnotaRepository;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<Wspolnota> wspolnoty = wspolnotaRepository.findAll();
        model.addAttribute("wspolnoty", wspolnoty);
        return "szkielet";
    }


}
