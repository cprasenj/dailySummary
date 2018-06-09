package dailySummary.contract;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMemberRequest {
    private String memberEmail;
    private String memberName;
    private String memberId;
    private String teamName;
    private String teamId;
    private String teamEmail;
    private String adminUserName;
    private String adminPassword;
}
