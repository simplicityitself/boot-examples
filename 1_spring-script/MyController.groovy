
@Controller
class MyController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!"
    }
    
    @RequestMapping("/jsondata")
    //note turning the map into a json response via @ResponseBody
    @ResponseBody
    Map jsondata() {
        return [myData:"Is In JSON Format"]
    }
    
    //note no @ResponseBody, so the text goes into the view resolver, which interprets this to issue a redirect
    @RequestMapping("/redir")
    String redirect() {
        return "redirect:";
    }
}
