package demo;


import demo.data.Customer;
import demo.data.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
class ExampleController {

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping("/")
    @ResponseBody
    Iterable home() {
        return customerRepository.findAll();
    }


    @RequestMapping("/search")
    @ResponseBody
    Iterable search(@RequestParam("q") String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @RequestMapping("/searchLike")
    @ResponseBody
    Iterable searchLike(@RequestParam("q") String lastName) {
        return customerRepository.findByLastNameQuery("%" + lastName + "%");
    }

    //note no @ResponseBody, so the text goes into the view resolver, which interprets this to issue a redirect
    @RequestMapping("/create")
    String create(@RequestParam("lastName") String lastName) {
        customerRepository.save(new Customer("Someone", lastName));
        return "redirect:";
    }

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

}
