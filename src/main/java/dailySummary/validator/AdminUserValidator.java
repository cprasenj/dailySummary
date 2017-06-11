package dailySummary.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AdminUserValidator {
    @Autowired
    private Environment env;

    public Boolean validate(String adminUserName, String adminPassword) {
        return Objects.equals(env.getProperty("admin.usre.name"), adminUserName)
                && Objects.equals(env.getProperty("admin.user.password"), adminPassword);
    }
}
