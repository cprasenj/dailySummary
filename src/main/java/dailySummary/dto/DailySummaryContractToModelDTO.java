package dailySummary.dto;

import dailySummary.contract.AddMemberRequest;
import dailySummary.contract.AddTeamRequest;
import dailySummary.contract.DailySummary;
import dailySummary.model.Member;
import dailySummary.model.Team;
import org.springframework.stereotype.Component;

import java.time.temporal.TemporalAmount;
import java.util.Date;

@Component
public class DailySummaryContractToModelDTO {

    public dailySummary.model.DailySummary toDailySummaryModel(DailySummary summary) {
        return dailySummary.model.DailySummary.builder()
                .summary(summary.getSummary())
                .teamEmail(summary.getTeamEmail())
                .date(new Date())
                .identity(summary.getIdentity() == null ?
                        String.format("%s%s", Long.toString(System.currentTimeMillis()), new Date().toString()) : summary.getIdentity())
                .category(summary.getCategory())
                .teamName(summary.getTeamName())
                .build();
    }

    public Member toMemberModel(AddMemberRequest addMemberRequest) {
        return Member.builder()
                .memberEmail(addMemberRequest.getMemberEmail())
                .memberName(addMemberRequest.getMemberName())
                .teamName(addMemberRequest.getTeamName())
                .teamEmail(addMemberRequest.getTeamEmail())
                .build();
    }

    public Team toTeamModel(AddTeamRequest addTeamRequest) {
        return Team.builder()
                .teamName(addTeamRequest.getTeamName())
                .teamEmail(addTeamRequest.getTeamEmail())
                .build();

    }
}

