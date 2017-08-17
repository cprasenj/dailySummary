package dailySummary.contract;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobUpdateRequest {
    private List<DailySummary> dailySummaryList;
}
