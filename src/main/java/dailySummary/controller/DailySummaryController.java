package dailySummary.controller;

import dailySummary.contract.DailySummary;
import dailySummary.dto.DailySummaryContractToModelDTO;
import dailySummary.service.DailySummaryService;
import dailySummary.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DailySummaryController {

    @Autowired
    private DailySummaryService dailySummaryService;

    @Autowired
    private MailService mailService;

    @Autowired
    private DailySummaryContractToModelDTO dailySummaryContractToModelDTO;

    @RequestMapping(value = "/dailySummary/save", method = RequestMethod.POST)
    public ResponseEntity getCountryPage(@RequestBody DailySummary summary) {
        dailySummaryService.persistSummary(dailySummaryContractToModelDTO.toModel(summary));
        return new ResponseEntity(summary, HttpStatus.OK);
    }

}
