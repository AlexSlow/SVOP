package com.svop;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Localformatter {

   public  String getLocallabel( )
    {

       Locale locale=Locale.getDefault();
       switch(locale.getCountry().toLowerCase())
       {
           case "ru":{
           return "ru";
       }
       case "en":{
           return "en";
       }
           case "fr":{
               return "fr";
           }
           case "de":{
               return "fr";
           }
           default: return "none";
       }
    }

}
