package com.attendance.groups.Exceptions;

import com.attendance.groups.Service.Groupsservice;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerClass
{
    Logger log= LoggerFactory.getLogger(ExceptionHandlerClass.class);
    @ResponseBody
    @ExceptionHandler({ServletRequestBindingException.class,ConstraintViolationException.class,NullPointerException.class})
    public ResponseEntity handlingg(Exception exception, WebRequest webRequest)
    {

        log.error("INVALID INPUT");
           //return new ResponseDto(null,new ErrorDetails(new Date(), 4000,"Please check your input"));
        return  new ResponseEntity(new ResponseDto(null,new ErrorDetails(new Date(), 4000, exception.getMessage())),HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @ExceptionHandler({UnauthorizedIns.class})
    public ResponseEntity<?> handlinggInvalidins(Exception exception, WebRequest webRequest)
    {
        log.error("INVALID INPUT");
        return  new ResponseEntity(new ResponseDto(null,new ErrorDetails(new Date(), 4001, exception.getMessage())),HttpStatus.BAD_REQUEST);
        //return new ResponseDto(null,new ErrorDetails(new Date(), 4001, exception.getMessage()));
    }
    @ResponseBody
    @ExceptionHandler({ValidationException.class})
    public ResponseDto handlinggVlidation(Exception exception, WebRequest webRequest)
    {
        log.error(exception.getMessage());
        return new ResponseDto(null,new ErrorDetails(new Date(), 2000, exception.getMessage()));
    }


    @ResponseBody
    @ExceptionHandler({Validation2001.class})
    public ResponseDto handling2001(Validation2001 exception, WebRequest webRequest)
    {
        log.error(exception.getMessage());
        return new ResponseDto(null,new ErrorDetails(new Date(), 2001,exception.getMessage()));
    }
    @ResponseBody
    @ExceptionHandler({Validation2002.class})
    public ResponseDto handling2002(Validation2002 exception, WebRequest webRequest)
    {
        log.error(exception.getMessage());
        return new ResponseDto(null,new ErrorDetails(new Date(), 2002,exception.getMessage()));
    }

    @ResponseBody
   @ExceptionHandler({NoHandlerFoundException.class,RuntimeException.class})
    public ResponseEntity<?> handling404(Exception exception)
    {
        return new ResponseEntity(new ErrorDetails(new Date(), 404,"PLEASE CHECK THE URL"),HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handlinggeneralexceptions(Exception exception, WebRequest webRequest)
    {
        return new ResponseEntity(new ErrorDetails(new Date(), 500,exception.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
