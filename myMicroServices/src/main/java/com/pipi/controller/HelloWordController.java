package com.pipi.controller;

import com.pipi.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@ResponseBody
public class HelloWordController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("hello")
    public String helloWorld(){
        return "hello pipi";
    }

    @GetMapping("hello-bean")
    public Message helloBean(){
        return new Message("hi pipi");
    }

    @GetMapping("hello/user/{name}")
    public Message helloPathVariable(@PathVariable String name){
        return new Message(String.format("Hello %s", name));
    }


    // Internalization =================================================



    @GetMapping("/hello-internalization1")
    public String heollInternalization1(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
//        return "good morning";
         return messageSource.getMessage("good.morning.message", null, locale);
    }

    /** According to this we have to add locale in every request. Since it is is painful to use, we can use
     * LocalContextHolder instead of this.
     * */
    @GetMapping("/hello-internalization2")
    public String heollInternalization() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }

}
