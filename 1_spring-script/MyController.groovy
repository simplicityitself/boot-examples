
@Controller
class MyController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!"
    }
}

