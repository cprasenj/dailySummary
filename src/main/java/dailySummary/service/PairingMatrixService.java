package dailySummary.service;

import dailySummary.contract.Pair;
import dailySummary.contract.PairMatrixRequest;
import dailySummary.dto.Summary;
import dailySummary.model.Member;
import dailySummary.model.PairingMatrixData;
import dailySummary.repository.MemberRepository;
import dailySummary.repository.PairingMatrixOldRepository;
import dailySummary.repository.PairingMatrixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PairingMatrixService {
    @Autowired
    private PairingMatrixRepository pairingMatrixRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PairingMatrixOldRepository pairingMatrixOldRepository;

    public Boolean persistMatrix(PairMatrixRequest pairMatrixRequest) {
        pairMatrixRequest.getPairs().forEach(p -> pairingMatrixRepository.save(p.toDomain(pairMatrixRequest.getTeamEmail())));
        return true;
    }

    public Boolean persistMatrixOld(PairMatrixRequest pairMatrixRequest) {
        pairMatrixRequest.getPairs().forEach(p -> pairingMatrixOldRepository
                .save(new Pair(p.getPair1(), p.getPair2(), p.getDays(), LocalDateTime.now())));
        return true;
    }

    public List<Pair> get(String pair1, String pair2) {
        List<Pair> byPair1AndPair2 = pairingMatrixOldRepository.getByPair1AndPair2(pair1, pair2);
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

    private Integer countPairingDays(List<PairingMatrixData> dataList) {
        return dataList.stream()
                .filter(p -> LocalDateTime.now().getDayOfYear() - p.getDate().getDayOfYear() <= 30)
                .map(PairingMatrixData::getDays)
                .reduce((d1, d2) -> d1 + d2)
                .orElse(0);
    }

    public Summary getV2(String pair1, String pair2) {
        Integer count1 = countPairingDays(pairingMatrixRepository.getByPair1AndPair2(pair1, pair2));
        Integer count2 = countPairingDays(pairingMatrixRepository.getByPair1AndPair2(pair2, pair1));

        long count = Objects.equals(pair1, pair2) ? count1 : count1 + count2;

        return new Summary(pair1, pair2, String.valueOf(count));

    }

    public String getLastUpdatedTime(String teamEmail) {
        PairingMatrixData data = pairingMatrixRepository.findFirstByTeamEmailOrderByDateDesc(teamEmail);
        return data.getDate().toString();
    }

    public List getDump() {
        return pairingMatrixRepository.findAll();
    }
}
