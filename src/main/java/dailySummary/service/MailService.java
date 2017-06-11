package dailySummary.service;

import dailySummary.constant.StringConstants;
import dailySummary.model.DailySummary;
import dailySummary.model.MailMessage;
import dailySummary.repository.DailySummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    public Boolean sendMail(String receiver) {
        createEmailBodies()
                .forEach(sendMailToRequestedReceiver(receiver));
        return Boolean.TRUE;
    }

    private Consumer<MailMessage> sendMailToRequestedReceiver(String receiver) {
        return m -> {
            if(Objects.equals(m.getReceiver(), receiver)) {
                sender.send(m.getMessage());
            }
        };
    }

    private List<MailMessage> createEmailBodies() {
        return dailySummaryRepository.findAll()
                .stream()
                .filter(isTodayUpdate())
                .collect(Collectors.groupingBy(DailySummary::getTeamEmail))
                .entrySet().stream()
                .map(createAMail())
                .collect(toList());
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(StringConstants.DATE_PATTERN);
        return formatter.format(date);
    }

    private Predicate<DailySummary> isTodayUpdate() {
        return dailySummary -> {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            if (dailySummary.getDate() == null) {
                return false;
            }
            cal1.setTime(dailySummary.getDate());

            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        };
    }

    private Function<Map.Entry<String, List<DailySummary>>, MailMessage> createAMail() {
        return e -> {
            try {
                String body = draftMailBody(e.getValue());
                String teamName = e.getValue().get(0).getTeamName();
                MimeMessage message = sender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String formattedDate = formatDate(new Date());
                helper.setSubject(String.format(StringConstants.SUBJECT, formattedDate));
                String header = String.format(StringConstants.HEADER, formattedDate);
                helper.setText(String.format(StringConstants.BODY, header, body, teamName), true);
                helper.setTo(e.getKey());
                return MailMessage.builder()
                        .message(message)
                        .receiver(e.getKey())
                        .build();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }
            return null;
        };
    }

    private String draftMailBody(List<DailySummary> summaries) {
        return summaries.stream()
                .map(createSummary())
                .collect(joining(""));
    }

    private Function<DailySummary, String> createSummary() {
        return s -> {
            String categoriesSummary = s.getSummary().stream()
                    .map(summary -> String.format(StringConstants.UPDATE, summary))
                    .collect(joining(""));
            return String.format(StringConstants.UPDATE_SECTION_WITH_HEADER, s.getCategory(), categoriesSummary);
        };
    }
}
