package dailySummary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/dailySummary")
    public String summaryForm() {
        return "dailySummary";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/sendMail")
    public String sendMail() {
        return "sendMail";
    }

    @RequestMapping("/addMember")
    public String addMember() {
        return "addMember";
    }

    @RequestMapping("/createTeam")
    public String createTeam() {
        return "createTeam";
    }

    @RequestMapping("/pairingMatrix")
    public String pairingMatrix() {
        return "pairingMatrix";
    }


}
