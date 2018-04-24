package dailySummary.controller;

import dailySummary.contract.Pair;
import dailySummary.contract.PairMatrixRequest;
import dailySummary.service.PairingMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PairingMatrixController {
    @Autowired
    private PairingMatrixService pairingMatrixService;

    @RequestMapping(value = "/pairingMatrix/save", method = RequestMethod.POST)
    public ResponseEntity savePairingMatrix(@RequestBody PairMatrixRequest pairMatrixRequest) {
        pairingMatrixService.persistMatrix(pairMatrixRequest);
        return new ResponseEntity(pairMatrixRequest, HttpStatus.OK);
    }

    @RequestMapping(value = "/pairingMatrix/get/{pair1}/{pair2}")
    public ResponseEntity getPairingMatrix(@PathVariable("pair1") String pair1,
                                           @PathVariable("pair2") String pair2
                                           ) {
        List<Pair> pairs = pairingMatrixService.get(pair1, pair2);
        pairs.addAll(pairingMatrixService.get(pair2, pair1));
        return new ResponseEntity(pairs, HttpStatus.OK);
    }

    @RequestMapping("/getAll")
    public ResponseEntity getDBDDump() {
       return new ResponseEntity(pairingMatrixService.getDump(), HttpStatus.OK);
    }
}
