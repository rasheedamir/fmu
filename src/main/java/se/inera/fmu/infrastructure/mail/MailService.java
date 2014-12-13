package se.inera.fmu.infrastructure.mail;

import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.validator.routines.EmailValidator;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import se.inera.fmu.application.util.StringUtils;
import se.inera.fmu.domain.model.eavrop.ArendeId;
import se.inera.fmu.domain.model.eavrop.EavropId;
import se.inera.fmu.domain.model.eavrop.UtredningType;
import se.inera.fmu.domain.model.landsting.LandstingCode;
import se.inera.fmu.domain.model.landsting.Landstingssamordnare;
import se.inera.fmu.domain.model.landsting.LandstingssamordnareRepository;

/**
 * Infrastructure Service for sending e-mails.
 * <p/>
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

	private static final Logger log = LoggerFactory.getLogger(MailService.class);

	@Inject
	private Environment env;

	@Inject
	private LandstingssamordnareRepository landstingssamordnareRepository;

	@Inject
	private JavaMailSenderImpl javaMailSender;

	@Inject
	private MessageSource messageSource;

	@Inject
	private TemplateEngine templateEngine;

	/**
	 * System default email address that sends the e-mails.
	 */
	private String from;

	@PostConstruct
	public void init() {
		this.from = env.getProperty("spring.mail.from");
	}

	/**
	 *
	 * @param to
	 * @param subject
	 * @param content
	 * @param isMultipart
	 * @param isHtml
	 */
	@Async
	public void sendEmail(String to, String subject, String content,
			boolean isMultipart, boolean isHtml) {
		log.debug(
				"Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
				isMultipart, isHtml, to, subject, content);

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
					isMultipart, CharEncoding.UTF_8);
			message.setTo(to);
			message.setFrom(from);
			message.setSubject(subject);
			message.setText(content, isHtml);
			javaMailSender.send(mimeMessage);
			log.debug("Sent e-mail to User '{}'!", to);
			log.debug(mimeMessage.toString());
		} catch (Exception e) {
			log.warn("E-mail could not be sent to user '{}', exception is: {}",
					to, e.getMessage());
		}
	}

	/**
	 *
	 * @param email
	 * @param content
	 * @param locale
	 */
	@Async
	public void sendActivationEmail(final String email, String content,
			Locale locale) {
		log.debug("Sending activation e-mail to '{}'", email);
		String subject = messageSource.getMessage("email.activation.title",
				null, locale);
		sendEmail(email, subject, content, false, true);
	}


	/**
	 * Sends notification email that eavrop have been created.
	 *
	 * @param eavropId, id of the eavrop to send email about
	 */
	@Async
	public void sendEavropCreatedEmail(final EavropId eavropId, final ArendeId arendeId, final UtredningType utredningType, final LocalDate lastAcceptanceDay, final LandstingCode landstingCode) {
		Assert.notNull(eavropId, "EavropId can't be Null!");
		Assert.notNull(arendeId, "ArendeId can't be Null!");
		Assert.notNull(landstingCode, "LandstingCode can't be Null!");
		
		final String emailAddress = getLandstingsamordnareEmailAddresses(landstingCode);
		
		if(StringUtils.isBlankOrNull(emailAddress)){
			log.warn(String.format("No email addresses found for landstingssamordnare on Eavrop with ÄrendeId %s", arendeId.toString()));
			return;
		}
		
		Locale locale = LocaleContextHolder.getLocale();
		log.debug("Sending eavrop created mail to '{}' in locale '{}'",
				emailAddress, locale.toString());

		// Prepare the evaluation context
		String utredningTypeStr = (utredningType!=null)?utredningType.name():"försäkringsmedicinsk utredning";
		final Context ctx = new Context(locale);
		ctx.setVariable("arendeId", arendeId.toString());
		ctx.setVariable("utredningType", utredningTypeStr);
		if(lastAcceptanceDay!=null){
			DateTimeFormatter formatter = DateTimeFormat.forPattern("d MMMM, yyyy");
			String dayString = lastAcceptanceDay.toString(formatter);
			ctx.setVariable("dayStr", dayString);
			log.debug("dayStr: "+dayString);
		}
		
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process(	"eavropCreatedEmail", ctx);
		log.debug("htmlContent: "+htmlContent);
		// Get subject according to locale
		
		Object[] args = {utredningTypeStr,arendeId.toString()};
		
		String subject = messageSource.getMessage("email.eavrop.order.subject", args, locale);
		
		log.debug("Subject "+subject);
		
		sendEmail(emailAddress, subject, htmlContent, false, true);
	}
	
	private String getLandstingsamordnareEmailAddresses(LandstingCode landstingCode) {
		String result = null;
		List<Landstingssamordnare> landstingssamordnare =  landstingssamordnareRepository.findByLandstingCode(landstingCode);
		
		if (landstingssamordnare != null && !landstingssamordnare.isEmpty()) {
			StringBuilder sb = null;
			for (Landstingssamordnare samordnare : landstingssamordnare) {
				if (samordnare.getEmail() != null
						&& EmailValidator.getInstance().isValid(samordnare.getEmail())) {
					if (sb != null) {
						sb.append("," + samordnare.getEmail());
					} else {
						sb = new StringBuilder(samordnare.getEmail());
					}
				}
			}
			result = (sb != null)?sb.toString():null;
		}

		return result;
	}
}
