package com.svop.exeptions.httpResponse;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class DeleteFromDBExeption {
    public DeleteFromDBExeption(RedirectAttributes redirectAttributes,String message)
    {
        redirectAttributes.addFlashAttribute("error",message);
    }
}
