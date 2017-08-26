package dailySummary.service;

import dailySummary.contract.Pair;
import dailySummary.contract.PairMatrixRequest;
import dailySummary.repository.PairingMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PairingMatrixService {
    @Autowired
    private PairingMatrixRepository pairingMatrixRepository;

    public Boolean persistMatrix(PairMatrixRequest pairMatrixRequest) {
        pairMatrixRequest.getPairs().forEach(p -> pairingMatrixRepository.save(p));
        return true;
    }

    public List<Pair> get(String pair1, String pair2) {
        List<Pair> byPair1AndPair2 = pairingMatrixRepository.getByPair1AndPair2(pair1, pair2);
        return byPair1AndPair2.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 10)
                .collect(toList());

    }
}
