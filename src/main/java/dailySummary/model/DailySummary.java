package dailySummary.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailySummary {
    private String teamEmail;
    private List<String> summary;
    private Date date;
    private String category;
    private String teamName;
    private String identity;
}
