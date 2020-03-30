package com.svop.controllers.HendBookControllers;

import com.svop.View.NomerReysView;
import com.svop.View.ReysViewElement;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.service.handbooks.AircompaniesService;
import com.svop.service.handbooks.NomerReysService;
import com.svop.service.handbooks.ReysyService;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.NomerReys;
import com.svop.tables.Handbooks.Reysy;
import com.svop.tables.Handbooks.ReysyNomerType;
import com.svop.tables.Handbooks.TypeReys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReysyHttpController {
    private static Logger logger= LoggerFactory.getLogger(ReysyHttpController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private ReysyService reysyService;
    @Autowired
    private AircompaniesService aircompaniesService;
    @Autowired
    private RoutesService routesService;
    @Autowired private NomerReysService nomerReysService;


    @RequestMapping(value="svop/reysy")
    public String open_reysy( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("type",TypeReys.Regular);
        model.addAttribute("reysy",reysyService.getReysListByType(TypeReys.Regular));
        return "/html/hendbooks/reysy.html";
    }

    @RequestMapping(value="svop/chartery")
    public String open_chartery( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("type",TypeReys.Charter);
        model.addAttribute("reysy",reysyService.getReysListByType(TypeReys.Charter));
        return "/html/hendbooks/reysy.html";
    }

    //Отображение элемента рейса и его редактирование
    @RequestMapping(value="svop/reysy/edit")
    public String edit(@RequestParam(name="ch[]",required = false) List<Integer> id_list,
                       @RequestParam(name="add",required = false) String addBt,
                       @RequestParam(name="redact",required = false) String redBt,
                       @RequestParam(name="delete",required = false) String delete_bt,
                       @RequestParam(name="type") TypeReys type,
                       Model model) {

        model.addAttribute("type",type);
        model.addAttribute("routs",routesService.getRouts());
        model.addAttribute("aircompanies",aircompaniesService.getAircompanies());

        if (addBt!=null)
        {
            model.addAttribute("reysy",new ReysViewElement());
            model.addAttribute("type_operation","add");
        }
        //Если нужно показать элемент
        else if(redBt!=null)
        {
            if (id_list!=null) {
                //необходимо получить авиакомпанию
                ReysViewElement reysy = reysyService.getReysById(id_list.get(0));

                if (reysy==null)
                {
                    logger.error("Не найден рейс для редактирования");
                    if (type==TypeReys.Regular)
                    return "redirect:/svop/reysy";
                    else  return "redirect:/svop/chartery";
                }

                return fillModelForRedact(reysy,type,model);
            }else {
                logger.error("Список id нулевой");
                if (type==TypeReys.Regular)
                    return "redirect:/svop/reysy";
                else  return "redirect:/svop/chartery";
            }



        }else if(delete_bt!=null)
        {
            reysyService.delete(id_list);
            if (type==TypeReys.Regular)
                return "redirect:/svop/reysy";
            else  return "redirect:/svop/chartery";
        }
        return "/html/hendbooks/reysy_modal.html";
    }




//Операции над отображением
//При нажатии на кнопку. Отображение уже загрузилось шагом ранее. Теперь пойдет обратное преобразование в рейсы
//Основные алгоритмы см в сервисе
    @RequestMapping(value="svop/reysy/save")
    public String save(@Valid @ModelAttribute ReysViewElement reysy, BindingResult bindingResult,
                       @RequestParam(name="save",required = false) String saveBt,
                       @RequestParam(name="exit",required = false) String exitBt, RedirectAttributes redirectAttributes) {

        //Валидация введенной формы
        if (bindingResult.hasErrors())
        {
            for (Object object : bindingResult.getAllErrors()) {
                redirectAttributes.addFlashAttribute("reysy",reysy);
                redirectAttributes.addFlashAttribute("errors",bindingResult.getFieldErrors());
                redirectAttributes.addFlashAttribute("type",reysy.getType());
                return "redirect:/svop/reysy/errors";
            }
        }
        if (saveBt!=null)
        {
           //System.out.println(reysy);
            reysyService.save(reysy);
        }
        if (exitBt!=null)
        {
            return "redirect:/svop/reysy";
        }
        if (reysy.getType()==TypeReys.Regular)
            return "redirect:/svop/reysy";
        else  return "redirect:/svop/chartery";
    }

    // Обработка ошибок
    @RequestMapping(value="svop/reysy/errors")
    public String error(Model model) {
        logger.error("Неверное заполнение формы рейсов!");
        ReysViewElement reysy=(ReysViewElement) model.getAttribute("reysy");
        System.out.println(reysy);
        model.addAttribute("type",reysy.getType());
        List<Integer> prilet_days= Arrays.asList(0,0,0,0,0,0,0);
        for(int i=0;i<reysy.getPrilet_days().size();i++)
        {
            prilet_days.set(reysy.getPrilet_days().get(i)-1,1);
        }
        List<Integer> vilet_days= Arrays.asList(0,0,0,0,0,0,0);
        for(int i=0;i<reysy.getVilet_days().size();i++)
        {
            vilet_days.set(reysy.getVilet_days().get(i)-1,1);
        }
        reysy.setPrilet_days(prilet_days);
        reysy.setVilet_days(vilet_days);
        return fillModelForRedact(reysy,reysy.getType(),model);
    }
    //_____------------------------------------------------------------------
    public String fillModelForRedact(ReysViewElement reysy,TypeReys type, Model model)
    {
        //Получим по одному из номеров рейсов авуакомпанию а затем, ролучим список номеров рейсов
        Integer nomer=null;
        if (reysy.getNomer_prilet_id()!=null) {
            nomer=reysy.getNomer_prilet_id();
        }else if(reysy.getNomer_vilet_id()!=null){
            nomer=reysy.getNomer_vilet_id();
        }
        if (nomer!=null) {
            NomerReys nomerReys = nomerReysService.getByid(nomer).get();

            if (nomerReys==null)
            {
                logger.error("Не найден номер рейса для рейса");
                if (type==TypeReys.Regular)
                    return "redirect:/svop/reysy";
                else  return "redirect:/svop/chartery";
            }

            //Получим номера рейсов на прилет и вылет
            List<NomerReysView> nomerReysViewPrilet = new ArrayList<>();
            List<NomerReysView> nomerReysViewVilet = new ArrayList<>();
            List<NomerReysView> nomerReysViews =
                    nomerReysService.getNomerReysFromAircompany(nomerReys.getAircompany().getId());
            if (nomerReysViews==null)
            {
                logger.error("Список номеров рейсов отсутствует");
                if (type==TypeReys.Regular)
                    return "redirect:/svop/reysy";
                else  return "redirect:/svop/chartery";
            }

            for (NomerReysView item : nomerReysViews) {
                if (item.getType() == ReysyNomerType.Tranzit) {
                    nomerReysViewPrilet.add(item);
                    nomerReysViewVilet.add(item);
                } else if (item.getType() == ReysyNomerType.Prilet) {
                    nomerReysViewPrilet.add(item);
                } else if (item.getType() == ReysyNomerType.Vilet) {
                    nomerReysViewVilet.add(item);
                }
            }
            model.addAttribute("aircompanies_selected",nomerReys.getAircompany().getId());
            model.addAttribute("nomerReysPrilet",nomerReysViewPrilet);
            model.addAttribute("nomerReysVilet",nomerReysViewVilet);
        }
        model.addAttribute("routs",routesService.getRouts());
        model.addAttribute("aircompanies",aircompaniesService.getAircompanies());
        model.addAttribute("reysy",reysy);
        model.addAttribute("type_operation", "redact");
        return "/html/hendbooks/reysy_modal.html";
    }

}
