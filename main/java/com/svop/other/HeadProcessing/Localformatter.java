package com.svop.other.HeadProcessing;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Localformatter {
//Locale def_locale= Locale.getDefault();
   public  Map<String,Boolean> getLocallabel( String localeStr)
    {
        Locale locale;
     if(localeStr==null){locale=Locale.getDefault();}
     else
     {
         locale=new Locale(localeStr);
     }

       // Locale locale=new Locale("fr");
       HashMap<String,Boolean> map=new HashMap<>();
        map.put("ru",false);
        map.put("en",false);
        map.put("fr",false);
        map.put("de",false);
        map.put("none",false);
       switch(locale.getLanguage().toLowerCase())
       {
           case "ru":{
          map.put("ru",true);
          break;
       }
       case "en":{
           map.put("en",true);
           break;
       }
           case "fr":{
               map.put("fr",true);
               break;
           }
           case "de":{
               map.put("de",true);
               break;
           }
           default: {  map.put("none",true);
               break;
           }
       }
       return map;
    }
}
