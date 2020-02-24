package com.svop.controllers.HendBookControllers;

import com.svop.HeadProcessing.Head_parser;
import com.svop.tables.Handbooks.Airporty;
import com.svop.Repository.Handbooks.AirportyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AirportyController {
    @Autowired
    private AirportyRepo AirportsRepositiry;
    @RequestMapping(value="svop/airports")
    public String open( Model model) {


        Head_parser.setModel(model);
        Iterable<Airporty> Airports=AirportsRepositiry.findAll();
        model.addAttribute("airports",Airports);
        return "content/html/hendbooks/airports_opening.html";
    }
    @RequestMapping(value="svop/airports/delete")
    public String delete(@RequestParam(name="ch[]",required = false) List<String> id_list, Model model) {

        if (id_list!=null) {
            //  System.out.println("there" + id_list);
            List<Integer> ids = new ArrayList<>();
            for (String id_str : id_list) {
                ids.add(Integer.valueOf(id_str));
            }
            AirportsRepositiry.deleteByIdIn(ids);

        }
        return "redirect:/svop/airports";
    }
    @RequestMapping(value="svop/airports/add")
    public String add(@RequestParam(name="NameRu",required = true)String NameRu,
                      @RequestParam(name="NameEng",required = true)String NameEng,
                      @RequestParam(name="NameCh",required = false)String NameCh,
                      @RequestParam(name="GMT",required = false)String GMT,
                      @RequestParam(name="ICAO",required = false)String ICAO,
                      @RequestParam(name="IATA",required = false)String IATA,
                      Model model) {
        Airporty airporty=new Airporty(NameRu,NameEng,NameCh,GMT,ICAO,IATA);
        AirportsRepositiry.save(airporty);
        return "redirect:/svop/airports";
    }
    @RequestMapping(value="svop/airports/update")
    public String update(@RequestParam(name="id",required = true)String id,
                         @RequestParam(name="NameRu",required = true)String NameRu,
                         @RequestParam(name="NameEng",required = false)String NameEng,
                         @RequestParam(name="NameCh",required = false)String NameCh,
                         @RequestParam(name="GMT",required = false)String GMT,
                         @RequestParam(name="ICAO",required = false)String ICAO,
                         @RequestParam(name="IATA",required = false)String IATA,
                         Model model) {
        //Airporty airporty=new Airporty(NameRu,NameEng,NameCh,GMT,ICAO,IATA);
        System.out.println("there");
        AirportsRepositiry.updateAirport(Integer.parseInt(id),NameRu,NameEng,NameCh,GMT,ICAO,IATA);
        return "redirect:/svop/airports";
    }


}