package ua.tqs.Homework.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.tqs.Homework.Entities.WeatherDescription;
import ua.tqs.Homework.Repositories.WeatherDescriptionRepository;

@Controller
@RequestMapping(path = "/weatherdescription")
public class WeatherDescriptionController {
    
    @Autowired
    private WeatherDescriptionRepository weatherDescRepo;
    
    @CrossOrigin(origins = "*")
    @GetMapping
    public @ResponseBody Iterable<WeatherDescription> findAll() {
        return weatherDescRepo.findAll();
    }
}
