package com.svop.message;

import com.svop.exeptions.response.SvopMessage;
import com.svop.service.secutity.SvopAuthenticationSuccessHandler;

public class Success extends SvopDefaultMessage implements SvopMessage {
    private Integer kod;
    @Override
    public Integer getKod() { return new Integer(0);
    }
public Success(String msg)
{
    super(msg);
}
    public Success()
    {

    }
}
