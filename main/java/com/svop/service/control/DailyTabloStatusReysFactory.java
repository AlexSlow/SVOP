package com.svop.service.control;

import java.util.Locale;

public class DailyTabloStatusReysFactory implements StatusFactory {
    @Override
    public StatusReysFormater getFormater(Locale locale) {
        switch (locale.getLanguage().toLowerCase())
        {
            case "en":{
                return new StatusReysEnFormater();
            }
            default:{ return new StatusReysRuFormaterImpl();}
        }
    }
}
