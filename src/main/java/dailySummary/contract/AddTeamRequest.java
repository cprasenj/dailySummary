package dailySummary.contract;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTeamRequest {
    private String teamName;
    private String teamEmail;
    private String adminUserName;
    private String adminPassword;
}
