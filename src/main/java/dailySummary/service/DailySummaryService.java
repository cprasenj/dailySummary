package dailySummary.service;

import dailySummary.model.DailySummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dailySummary.repository.DailySummaryRepository;

@Component
public class DailySummaryService {
    @Autowired
    private DailySummaryRepository dailySummaryRepository;
    public Boolean persistSummary(DailySummary dailySummary) {
        dailySummaryRepository.save(dailySummary);
        return true;
    }
}
