package com.svop.controllers.API.control;

import com.svop.View.SeazonScheduleViews.SeazonScheduleLanguageView;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.message.tabloSeazon.TabloControleMessage;
import com.svop.message.tabloSeazon.TabloInitResponse;
import com.svop.service.SeazonSchedule.SeazonScheduleService;
import com.svop.service.control.SeazonTabloControl;
import com.svop.tables.Handbooks.Airporty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/svop/api/tablo",headers = {"Content-type=application/json"})
public class TabloSeazonRestController {
    private static Logger logger= LoggerFactory.getLogger(TabloSeazonRestController.class);
    @Autowired private SeazonTabloControl seazonTabloControl;
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations; //Упаравление брокером сообщений
    @ResponseBody
    @RequestMapping(value="/control")
    public ResponseEntity<SvopMessage> open(@RequestBody TabloControleMessage message) {

        logger.info("Табло перешло в состояние "+message);
        seazonTabloControl.active(message.isActive());
        if (seazonTabloControl.isActive())
            simpMessageSendingOperations.convertAndSend("/topic/seazonTablo", "on");
        else  simpMessageSendingOperations.convertAndSend("/topic/seazonTablo", "off");
        return new ResponseEntity<>(new Success("Успех"), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value="/control/status")
    public ResponseEntity<TabloInitResponse> getStatus() {
        logger.info("Получение статуса " + seazonTabloControl.isActive());
        TabloInitResponse tabloInitResponse = new TabloInitResponse();
        if (seazonTabloControl.isActive())
        {
            tabloInitResponse.setMessage("on");
        tabloInitResponse.setList(seazonTabloControl.getSeazonScheduleLanguageViews());
        tabloInitResponse.setHeaders(seazonTabloControl.getHeader());
    }
        else  {
            tabloInitResponse.setMessage("off");
        }
        return new ResponseEntity<>(tabloInitResponse,HttpStatus.OK);
    }


}
