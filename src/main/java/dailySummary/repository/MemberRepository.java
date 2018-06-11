package dailySummary.repository;


import dailySummary.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MemberRepository extends MongoRepository<Member, String> {
    List<Member> findAllMembersByTeamEmail(String teamEmail);
    void deleteMemberByTeamEmail(String teamEmail);
}
