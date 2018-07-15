package dailySummary.controller;

import dailySummary.contract.PairMatrixRequest;
import dailySummary.service.PairingMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PairingMatrixController {
    @Autowired
    private PairingMatrixService pairingMatrixService;

    @RequestMapping(value = "/pairingMatrix/save", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity savePairingMatrix(@RequestBody PairMatrixRequest pairMatrixRequest) {
        pairingMatrixService.persistMatrix(pairMatrixRequest);
        return new ResponseEntity(pairMatrixRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/pairingMatrix/save_old", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity savePairingMatrixOld(@RequestBody PairMatrixRequest pairMatrixRequest) {
        pairingMatrixService.persistMatrixOld(pairMatrixRequest);
        return new ResponseEntity(pairMatrixRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/pairingMatrix/get/{pair1}/{pair2}")
    @CrossOrigin
    public ResponseEntity getPairingMatrix(@PathVariable("pair1") String pair1,
                                           @PathVariable("pair2") String pair2
                                           ) {
        return new ResponseEntity(pairingMatrixService.get(pair1, pair2), HttpStatus.OK);
    }

    @RequestMapping(value = "/pairingMatrix/v2/get")
    @CrossOrigin
    public ResponseEntity getPairingMatrixForaTeam(@RequestParam("teamEmail") String teamEmail) {

        return new ResponseEntity(pairingMatrixService.getByTeamId(teamEmail), HttpStatus.OK);
    }

    @RequestMapping(value = "/pairingMatrix/v2/get/{pair1}/{pair2}")
    @CrossOrigin
    public ResponseEntity getPairingMatrixV2(@PathVariable("pair1") String pair1,
                                           @PathVariable("pair2") String pair2
    ) {
        return new ResponseEntity(pairingMatrixService.getV2(pair1, pair2), HttpStatus.OK);
    }

    @RequestMapping(value = "/pairingMatrix/v2/get/last-updated-time")
    @CrossOrigin
    public ResponseEntity getLastUpdatedTime(@RequestParam("teamEmail") String teamEmail) {
        return new ResponseEntity(pairingMatrixService.getLastUpdatedTime(teamEmail), HttpStatus.OK);
    }

    @RequestMapping("/getAll")
    @CrossOrigin
    public ResponseEntity getDBDDump() {
       return new ResponseEntity(pairingMatrixService.getDump(), HttpStatus.OK);
    }
}
