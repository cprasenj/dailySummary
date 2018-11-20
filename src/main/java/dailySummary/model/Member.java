package dailySummary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String memberEmail;
    private String memberName;
    private String teamName;
    private String teamEmail;
    private String memberId;
    private String teamId;
    private Boolean active;
}
