package dailySummary.contract;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMailRequest {
    private String receiver;
    private String adminUserName;
    private String adminPassword;
}
