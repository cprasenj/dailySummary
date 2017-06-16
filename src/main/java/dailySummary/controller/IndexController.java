package dailySummary.controller;

import dailySummary.contract.RequestByEmail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

}
