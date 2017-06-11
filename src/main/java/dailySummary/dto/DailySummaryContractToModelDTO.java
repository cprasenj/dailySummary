package dailySummary.dto;

import dailySummary.contract.DailySummary;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DailySummaryContractToModelDTO {

    public dailySummary.model.DailySummary toModel(DailySummary summary) {
        return dailySummary.model.DailySummary.builder()
                .summary(summary.getSummary())
                .teamEmail(summary.getTeamEmail())
                .date(new Date())
                .category(summary.getCategory())
                .teamName(summary.getTeamName())
                .build();
    }
}

