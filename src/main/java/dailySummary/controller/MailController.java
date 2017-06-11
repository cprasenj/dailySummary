package dailySummary.controller;

import dailySummary.contract.SendMailRequest;
import dailySummary.service.MailService;
import dailySummary.validator.AdminUserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private AdminUserValidator validator;

    @RequestMapping(value = "/dailySummary/send", method = RequestMethod.POST)
    public ResponseEntity getCountryPage(@RequestBody SendMailRequest sendMailRequest) {
        Boolean isAdmin = validator.validate(sendMailRequest.getAdminUserName(), sendMailRequest.getAdminPassword());
        if (isAdmin) {
            mailService.sendMail(sendMailRequest.getReceiver());
            return new ResponseEntity(sendMailRequest, OK);
        }
        return new ResponseEntity(sendMailRequest, BAD_REQUEST);
    }
}
