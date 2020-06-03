package com.svop.controllers.API;

import com.svop.View.NomerReysView;
import com.svop.View.NomerReysViewRequest;
import com.svop.exeptions.SvopDataBaseExeption;
import com.svop.exeptions.response.SvopMessage;
import com.svop.message.Success;
import com.svop.service.handbooks.NomerReysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/svop/api/nomer_reys",headers = {"Content-type=application/json"})
public class NomerReysRestController {
    @Autowired
    NomerReysService nomerReysService;

    @ResponseBody
    @RequestMapping(value="/get_from_aircompanies")
    public List<NomerReysView> get(@RequestBody Integer aircompany_id) {
        // System.out.println(aircompany_id);
        if (aircompany_id!=null)
            try {
                return nomerReysService.getNomerReysFromAircompany(aircompany_id);
            }
        catch (DataIntegrityViolationException ex)
        {

        }
        return null;
    }
    @ResponseBody
    @RequestMapping(value="/")
    public ResponseEntity<SvopMessage> save(@RequestBody NomerReysViewRequest nomerReysView) {
       try{
            nomerReysService.save(nomerReysView) ;
       }
        catch (DataIntegrityViolationException  ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
       return new ResponseEntity<SvopMessage>(new Success("Success"),HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value="/")
    public ResponseEntity delete(@RequestBody List<Integer> id) {
        try {
            nomerReysService.delete(id);
            return new ResponseEntity("Success",HttpStatus.OK);
        }catch (Exception ex)
        {
            throw new SvopDataBaseExeption(ex.getLocalizedMessage());
        }
    }
}
