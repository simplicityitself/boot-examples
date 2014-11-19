package demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
class ExampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    //note, resoves to the thymeleaf index html in resources/ tempates
    @RequestMapping("/html")
    String html() {
        return "index";
    }

    @RequestMapping("/jsondata")
    //note turning the map into a json response via @ResponseBody
    @ResponseBody
    Map jsondata() {
        Map ret = new HashMap();

        ret.put("My data", "Is in JSON Format!");

        return ret;
    }

    //note no @ResponseBody, so the text goes into the view resolver, which interprets this to issue a redirect
    @RequestMapping("/redir")
    String redirect() {
        return "redirect:";
    }
}
