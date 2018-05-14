package dailySummary.service;

import dailySummary.contract.Pair;
import dailySummary.contract.PairMatrixRequest;
import dailySummary.dto.Summary;
import dailySummary.repository.PairingMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .collect(toList());

    }

    public Summary getV2(String pair1, String pair2) {
        List<Pair> byPair1AndPair2 = pairingMatrixRepository.getByPair1AndPair2(pair1, pair2);
        List<Pair> byPair2AndPair1 = pairingMatrixRepository.getByPair1AndPair2(pair2, pair1);

        List<Pair> p12 = byPair1AndPair2.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .collect(toList());

        List<Pair> p21 = byPair2AndPair1.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .collect(toList());

        long count = Stream.concat(p12.stream(), p21.stream())
                .count();

        return new Summary(pair1, pair2, String.valueOf(count));

    }

    public List getDump() {
        return pairingMatrixRepository.findAll();
    }
}
