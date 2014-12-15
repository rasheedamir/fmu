package se.inera.fmu.config.metrics;

import javax.mail.MessagingException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.Assert;

/**
 * SpringBoot Actuator HealthIndicator check for JavaMail.
 */
@Slf4j
public class JavaMailHealthIndicator extends AbstractHealthIndicator {

    private JavaMailSenderImpl javaMailSender;

    public JavaMailHealthIndicator(JavaMailSenderImpl javaMailSender) {
        Assert.notNull(javaMailSender, "javaMailSender must not be null");
        this.javaMailSender = javaMailSender;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        log.debug("Initializing JavaMail health indicator");
        try {
            javaMailSender.getSession().getTransport().connect(javaMailSender.getHost(),
                    javaMailSender.getPort(),
                    javaMailSender.getUsername(),
                    javaMailSender.getPassword());

            builder.up();

        } catch (MessagingException e) {
            log.debug("Cannot connect to e-mail server.", e);
            builder.down(e);
        }
    }
}
