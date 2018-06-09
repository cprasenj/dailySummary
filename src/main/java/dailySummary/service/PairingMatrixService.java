package dailySummary.service;

import dailySummary.contract.Pair;
import dailySummary.contract.PairMatrixRequest;
import dailySummary.dto.Summary;
import dailySummary.model.Member;
import dailySummary.model.PairingMatrixData;
import dailySummary.repository.MemberRepository;
import dailySummary.repository.PairingMatrixRepository;
import dailySummary.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class PairingMatrixService {
    @Autowired
    private PairingMatrixRepository pairingMatrixRepository;
    @Autowired
    private MemberRepository memberRepository;

    public Boolean persistMatrix(PairMatrixRequest pairMatrixRequest) {
        pairMatrixRequest.getPairs().forEach(p -> pairingMatrixRepository.save(p.toDomain(pairMatrixRequest.getTeamEmail())));
        return true;
    }

    public List<PairingMatrixData> get(String pair1, String pair2) {
        List<PairingMatrixData> byPair1AndPair2 = pairingMatrixRepository.getByPair1AndPair2(pair1, pair2);
        return byPair1AndPair2.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .collect(toList());

    }

    public List<Summary> getByTeamId(String teamEmail) {
        List<Member> members = memberRepository.findAllMembersByTeamEmail(teamEmail);

        return members.stream()
                .map(Member::getMemberId)
                .map(x -> members.stream()
                        .map(y -> new Pair(x, y.getMemberId(), null, null))
                ).flatMap(x -> Arrays.stream(x.toArray()))
                .map(x -> (Pair) x)
                .distinct()
                .map(p -> getV2(p.getPair1(), p.getPair2()))
                .collect(Collectors.toList());

    }

    public Summary getV2(String pair1, String pair2) {
        List<PairingMatrixData> byPair1AndPair2 = pairingMatrixRepository.getByPair1AndPair2(pair1, pair2);
        List<PairingMatrixData> byPair2AndPair1 = pairingMatrixRepository.getByPair1AndPair2(pair2, pair1);

        Integer count1 = byPair1AndPair2.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .map(PairingMatrixData::getDays)
                .reduce((d1, d2) -> d1 + d2)
                .orElse(0);

        Integer count2 = byPair2AndPair1.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .map(PairingMatrixData::getDays)
                .reduce((d1, d2) -> d1 + d2)
                .orElse(0);

        long count = Objects.equals(pair1, pair2) ? count1 : count1 + count2;

        return new Summary(pair1, pair2, String.valueOf(count));

    }

    public List getDump() {
        return pairingMatrixRepository.findAll();
    }
}
