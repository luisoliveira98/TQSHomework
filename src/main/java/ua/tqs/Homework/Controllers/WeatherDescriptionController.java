package ua.tqs.Homework.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.tqs.Homework.Entities.City;
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
    
    @GetMapping(path = "/{id}/pt")
    public @ResponseBody String descriptionPT(@PathVariable("id") int id) {
        WeatherDescription description = null;
        description = weatherDescRepo.getOne(id);
        return description.getDescIdWeatherTypePT();
    }
    
    @GetMapping(path = "/{id}/en")
    public @ResponseBody String descriptionEN(@PathVariable("id") int id) {
        WeatherDescription description = null;
        description = weatherDescRepo.getOne(id);
        return description.getDescIdWeatherTypeEN();
    }
}
