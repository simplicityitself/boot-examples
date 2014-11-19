
@Controller
class MyController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!"
    }
    
    @RequestMapping("/jsondata")
    //note auto generation of JSON response type
    @ResponseBody
    Map jsondata() {
        return [myData:"Is In JSON Format"]
    }
}

