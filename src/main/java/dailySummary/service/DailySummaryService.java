package dailySummary.service;

import dailySummary.error.NotAMemberError;
import dailySummary.model.DailySummary;
import dailySummary.model.Member;
import dailySummary.model.Team;
import dailySummary.repository.DailySummaryRepository;
import dailySummary.repository.MemberRepository;
import dailySummary.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DailySummaryService {

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Boolean persistSummary(DailySummary dailySummary) {
        dailySummaryRepository.save(dailySummary);
        return true;
    }

    public Boolean addMember(Member member) {
        memberRepository.save(member);
        return true;
    }

    public Boolean createTeam(Team team) {
        teamRepository.save(team);
        return true;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Member getTeamForAUser(String userEmail) throws NotAMemberError {
        Member member = null;
        try{
            member = memberRepository.findAll().stream()
                    .filter(m -> Objects.equals(m.getMemberEmail(), userEmail))
                    .findFirst().get();
        } catch (Exception e) {
            throw new NotAMemberError();
        }
        return member;
    }
}
