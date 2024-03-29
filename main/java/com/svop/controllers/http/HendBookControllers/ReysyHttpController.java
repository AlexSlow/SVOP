package com.svop.controllers.http.HendBookControllers;

import com.svop.View.NomerReysView;
import com.svop.View.ReysViewElement;
import com.svop.exeptions.httpResponse.DeleteFromDBExeption;
import com.svop.other.HeadProcessing.Head_parser;
import com.svop.other.HeadProcessing.PageFormatter;
import com.svop.service.handbooks.AircompaniesService;
import com.svop.service.handbooks.NomerReysService;
import com.svop.service.handbooks.ReysyService;
import com.svop.service.handbooks.RoutesService;
import com.svop.service.secutity.UserService;
import com.svop.tables.Handbooks.*;
import com.svop.validator.ReysViewElementValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired private ReysViewElementValidator reysViewElementValidator;
    @Autowired private SezonRepository sezonRepository;

    private void fillSezons(Model modal)
    {
        modal.addAttribute("sezons",sezonRepository.findAll());
        return;
    }
    @RequestMapping(value="/svop/reysy")
    public String open_reysy( Model model,@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable page) {
        //id выбор сезона
        Integer sezon_selected=(Integer) model.getAttribute("sezon_selected");
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        //Определить тип рейса
        model.addAttribute("type",TypeReys.Регулярный);
        //Если есть отбор по периоду
        PageFormatter pageFormatter=new PageFormatter();
        model.addAttribute("reysy",reysyService.getReysPageByType(TypeReys.Регулярный,sezon_selected,page,pageFormatter));
        pageFormatter.fillModel(model,page.getPageNumber());
        fillSezons(model);
        return "/html/hendbooks/reysy.html";
    }

    @RequestMapping(value="/svop/chartery")
    public String open_chartery( Model model) {
        Head_parser head_parser=new Head_parser();
        head_parser.setModel(userService,model);
        model.addAttribute("type",TypeReys.Чартерный);
        Integer sezon_selected=(Integer) model.getAttribute("sezon_selected");
        model.addAttribute("reysy",reysyService.getReysListByType(TypeReys.Чартерный,sezon_selected));
        fillSezons(model);
        return "/html/hendbooks/reysy.html";
    }

    //Отображение элемента рейса и его редактирование

    /**
     *
     * @param id_list Списко с id
     * @param addBt Кнопка добавить
     * @param redBt Кнопка редактировать
     * @param delete_bt Кнопка удалить
     * @param sezon_selected Сезон для сохранения
     * @param type Тип рейса
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value="svop/reysy/edit")
    public String edit(@RequestParam(name="ch[]",required = false) List<Integer> id_list,
                       @RequestParam(name="add",required = false) String addBt,
                       @RequestParam(name="redact",required = false) String redBt,
                       @RequestParam(name="delete",required = false) String delete_bt,
                       @RequestParam(name="sezon_selected",required = false) Integer sezon_selected,
                       @RequestParam(name="type") TypeReys type,
                       Model model,RedirectAttributes redirectAttributes) {
        //Это выборанный отбор на прд странице
        //Нужно положить и в модель и в атрибуты, так как атрибуты редиректа поместятся в модель только при редиректе
        model.addAttribute("sezon_selected",sezon_selected);
        redirectAttributes.addFlashAttribute("sezon_selected",sezon_selected);
        model.addAttribute("routs",routesService.getRouts());
        model.addAttribute("aircompanies",aircompaniesService.getAircompanies());

        if (addBt!=null)
        {
            ReysViewElement reysViewElement=new ReysViewElement();
            reysViewElement.setType(type);
            model.addAttribute("reysy",reysViewElement);
            model.addAttribute("type_operation","add");
        }
        //Если нужно показать элемент
        else if(redBt!=null)
        {
            if (id_list!=null) {
                //необходимо получить рейс
                ReysViewElement reysy;
                try {
                    reysy = reysyService.getReysById(id_list.get(0));
                }catch (Exception ex)
                {
                    new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
                    ex.printStackTrace();
                    logger.error("Не найден рейс для редактирования");
                    if (type==TypeReys.Регулярный)
                        return "redirect:/svop/reysy";
                    else  return "redirect:/svop/chartery";
                }
                return fillModelForRedact(reysy,model,redirectAttributes);
            }else {
                logger.error("Список id нулевой");
                if (type==TypeReys.Регулярный)
                    return "redirect:/svop/reysy";
                else  return "redirect:/svop/chartery";
            }
        }else if(delete_bt!=null)
        {
            if (id_list!=null)
                try {
                    reysyService.delete(id_list);
                }catch (Exception ex)
                {
                    new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
                    ex.printStackTrace();
                }

            if (type==TypeReys.Регулярный)
                return "redirect:/svop/reysy";
            else  return "redirect:/svop/chartery";
        }
        return "/html/hendbooks/reysy_modal.html";
    }




//Операции над отображением
//При нажатии на кнопку. Отображение уже загрузилось шагом ранее. Теперь пойдет обратное преобразование в рейсы
//Основные алгоритмы см в сервисе

    /**
     *
     * @param reysy Представление элемента-Данные формы
     * @param bindingResult Для валидации
     * @param saveBt Сохранить?
     * @param exitBt Выйти?
     * @param sezon_selected Сохраненное значение выбранного периода
     * @param redirectAttributes Атрибуты редиректа
     * @return
     */
    @RequestMapping(value="/svop/reysy/save")
    public String save(
            //@Valid
            @ModelAttribute ReysViewElement reysy,
           BindingResult bindingResult,
            @RequestParam(name="save",required = false) String saveBt,
            @RequestParam(name="exit",required = false) String exitBt,
            @RequestParam(name="sezon_selected",required = false) Integer sezon_selected,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("sezon_selected",sezon_selected);
        if (exitBt!=null)
        {

            if (reysy.getType()==TypeReys.Регулярный)
                return "redirect:/svop/reysy";
            else  return "redirect:/svop/chartery";
        }
        //Валидация введенной формы
        reysViewElementValidator.validate(reysy,bindingResult);
        if (bindingResult.hasErrors())
        {
            redirectAttributes.addFlashAttribute("reysy",reysy);
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    redirectAttributes.addFlashAttribute(fieldError.getCode(), true);
                }
            }
            return "redirect:/svop/reysy/errors";
        }


        if (saveBt!=null)
        {
           //System.out.println(reysy);
            try {
                reysyService.save(reysy);
            }
            catch (DataIntegrityViolationException ex)
            {
                ex.printStackTrace();
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            }

        }

        if (reysy.getType()==TypeReys.Регулярный)
            return "redirect:/svop/reysy";
        else  return "redirect:/svop/chartery";
    }

    // Обработка ошибок
    @RequestMapping(value="/svop/reysy/errors")
    public String error(Model model,RedirectAttributes redirectAttributes) {
        logger.error("Неверное заполнение формы рейсов!");
        Integer sezon_selected=(Integer)model.getAttribute("sezon_selected");
        redirectAttributes.addFlashAttribute("sezon_selected",sezon_selected);
        ReysViewElement reysy=(ReysViewElement) model.getAttribute("reysy");
        //Из списка представления к выводу на экран
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
        return fillModelForRedact(reysy,model,redirectAttributes);
    }
    //_____------------------------------------------------------------------
    //Заполнить страницу по имеющемуся reysView
    public String fillModelForRedact(ReysViewElement reysy, Model model,RedirectAttributes redirectAttributes)
    {
        //Получим по одному из номеров рейсов авуакомпанию а затем, ролучим список номеров рейсов
        Integer nomer=null;
        if (reysy.getNomer_prilet_id()!=null) {
            nomer=reysy.getNomer_prilet_id();
        }else if(reysy.getNomer_vilet_id()!=null){
            nomer=reysy.getNomer_vilet_id();
        }
        if (nomer!=null) {
            NomerReys nomerReys=null;
            try {
                nomerReys = nomerReysService.getByid(nomer).get();
            }catch (Exception ex)
            {
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
                ex.printStackTrace();
                logger.error("Не могу получить Номер рейса");
            }


            if (nomerReys==null)
            {
                logger.error("Не найден номер рейса для рейса");
                if (reysy.getType()==TypeReys.Регулярный)
                    return "redirect:/svop/reysy";
                else  return "redirect:/svop/chartery";
            }

            //Получим номера рейсов на прилет и вылет
            List<NomerReysView> nomerReysViewPrilet = new ArrayList<>();
            List<NomerReysView> nomerReysViewVilet = new ArrayList<>();
            List<NomerReysView> nomerReysViews;
            try
            {
                 nomerReysViews =
                        nomerReysService.getNomerReysFromAircompany(nomerReys.getAircompany().getId());
            }catch (Exception ex)
            {
                new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
                ex.printStackTrace();
                logger.error("Список номеров рейсов отсутствует");
                if (reysy.getType()==TypeReys.Регулярный)
                    return "redirect:/svop/reysy";
                else  return "redirect:/svop/chartery";
            }

            //Распределить номера рейсов по коллекциям
            for (NomerReysView item : nomerReysViews) {
                if (item.getType() == ReysyNomerType.Прилет) {
                    nomerReysViewPrilet.add(item);
                    nomerReysViewVilet.add(item);
                } else if (item.getType() == ReysyNomerType.Вылет) {
                    nomerReysViewPrilet.add(item);
                } else if (item.getType() == ReysyNomerType.Транзит) {
                    nomerReysViewVilet.add(item);
                }
            }
            model.addAttribute("aircompanies_selected",nomerReys.getAircompany().getId());
            model.addAttribute("nomerReysPrilet",nomerReysViewPrilet);
            model.addAttribute("nomerReysVilet",nomerReysViewVilet);
        }
        try {
            model.addAttribute("routs",routesService.getRouts());
        }catch (Exception ex)
        {
            new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            ex.printStackTrace();
            logger.error("Маршурты не получены");
        }
        try {

            model.addAttribute("aircompanies",aircompaniesService.getAircompanies());
        }catch (Exception ex)
        {
            new DeleteFromDBExeption(redirectAttributes,ex.getLocalizedMessage());
            ex.printStackTrace();
            logger.error("Авиакомпании  не получены");
        }

        model.addAttribute("reysy",reysy);
        model.addAttribute("type_operation", "redact");
        return "/html/hendbooks/reysy_modal.html";
    }

}
