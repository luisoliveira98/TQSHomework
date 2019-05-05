package ua.tqs.Homework.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.tqs.Homework.Repositories.CityRepository;

@Controller
public class WebController {
    
    @Autowired
    private CityRepository cityRepo;
    
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("cidades", cityRepo.findAll());
        return "index";
    }
    
}
