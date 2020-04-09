package com.svop.exeptions;

import org.springframework.ui.Model;

/**
 * Мы вызываем это сообщение когда ловим исключение, например из бызы данных
 */
public class SvopDataBaseExeption extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public SvopDataBaseExeption()
    {
        super();

    }

    public SvopDataBaseExeption(String message)
    {
        super(message);

    }
}
