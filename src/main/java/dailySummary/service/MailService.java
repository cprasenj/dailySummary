package dailySummary.service;

import dailySummary.contract.Preview;
import dailySummary.model.DailySummary;
import dailySummary.model.MailMessage;
import dailySummary.model.Member;
import dailySummary.repository.DailySummaryRepository;
import dailySummary.repository.MemberRepository;
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

import static dailySummary.constant.StringConstants.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Boolean sendMail(String receiver) {
        createEmailBodies()
                .forEach(sendMailToRequestedReceiver(receiver));
        return Boolean.TRUE;
    }

    public Preview preview(String receiver) {
        List<DailySummary> toDaySummaryForATeam = getDailySummaries(receiver);

        String body = draftMailBody(toDaySummaryForATeam);
        String formattedDate = formatDate(new Date());
        String header = String.format(HEADER, formattedDate);
        return Preview.builder()
                .emailBody(String.format(PREVIEW_BODY, header, body, toDaySummaryForATeam.get(0).getTeamName()))
                .build();
    }

    private List<DailySummary> getDailySummaries(String receiver) {
        return dailySummaryRepository.findAll()
                .stream()
                .filter(isTodayUpdate())
                .filter(d -> Objects.equals(d.getTeamEmail(), receiver))
                .collect(Collectors.toList());
    }

    private Consumer<MailMessage> sendMailToRequestedReceiver(String receiver) {
        return m -> {
            if (Objects.equals(m.getReceiver(), receiver)) {
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
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
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
                helper.setSubject(String.format(SUBJECT, formattedDate));
                String header = String.format(HEADER, formattedDate);
                helper.setText(String.format(BODY, header, body, teamName), true);
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

    private List<String> getAllMemberEmail() {
        return memberRepository.findAll().stream()
                .map(Member::getMemberEmail)
                .collect(Collectors.toList());

    }

    private String draftMailBody(List<DailySummary> summaries) {
        String summary = String.format(UPDATE_SECTION_WITH_HEADER, "Story Updates", summaries.stream()
                .map(createSummary())
                .filter(s -> !Objects.equals(s, ""))
                .collect(joining("")));
        String openQuestions = summaries.stream()
                .map(createQuestionSection())
                .filter(s -> !Objects.equals(s, ""))
                .collect(joining(""));

        String otherUpdates = summaries.stream()
                .map(createUpdateSection())
                .filter(s -> !Objects.equals(s, ""))
                .collect(joining(""));
        String questions = section("Open Questions", openQuestions);
        String updates = section("Other Updates", otherUpdates);

        return String.format("%s%s%s", summary, questions, updates);
    }

    private Function<DailySummary, String> createSummary() {
        return s -> {
            if (isEmpty(s.getSummary())) {
                return "";
            }
            return String.format(UPDATE_SECTION_WITH_HEADER, s.getCategory(),
                    String.format(UPDATE, s.getSummary()));
        };
    }

    private String section(String header, String section) {
        if (isEmpty(section)) {
            return "";
        }
        return String.format(UPDATE_SECTION_WITH_HEADER, header, section);
    }

    private Function<DailySummary, String> createQuestionSection() {
        return s -> {
            if (isEmpty(s.getOpenQuestion())) {
                return "";
            }
            return String.format(UPDATE_SECTION_WITH_HEADER, s.getCategory(),
                    String.format(UPDATE, s.getOpenQuestion()));
        };
    }

    private Function<DailySummary, String> createUpdateSection() {
        return s -> {
            if (isEmpty(s.getOtherUpdate())) {
                return "";
            }
            return String.format(UPDATE_SECTION_WITH_HEADER, s.getCategory(),
                    String.format(UPDATE, s.getOtherUpdate()));
        };
    }

    public List<DailySummary> getAllJobForATeam(String emailId) {
        return getDailySummaries(emailId);
    }
}
