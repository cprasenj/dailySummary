package dailySummary.controller;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword("HelloWorld");
        String encrypted = bte.encrypt("prasenjitchk123@gmail.com");
        System.out.println("Encrypted = " + encrypted);
        return "dailySummary";
    }

    @RequestMapping("/sendMail")
    public String sendMail() {
        return "sendMail";
    }

}
