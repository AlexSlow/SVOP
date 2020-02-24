package com.svop.HeadProcessing;

import org.springframework.ui.Model;

public class Head_parser {
    public static void setModel(Model model)
    {
        Localformatter localformatter=new Localformatter();
       //Тут должен быть запрос из БД


        model.addAttribute("language",localformatter.getLocallabel());
    }

}
