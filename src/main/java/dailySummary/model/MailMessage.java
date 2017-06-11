package dailySummary.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.mail.internet.MimeMessage;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailMessage {
    private MimeMessage message;
    private String receiver;
}
