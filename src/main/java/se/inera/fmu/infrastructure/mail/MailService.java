package se.inera.fmu.infrastructure.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.validator.routines.EmailValidator;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
@Slf4j
@Service
public class MailService {

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
	public void sendEmail(InternetAddress[] to, String subject, String content,
			boolean isMultipart, boolean isHtml) {
		log.debug(
				"Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
				isMultipart, isHtml, to, subject, content);

		// Prepare message using a Spring helper
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		log.debug("mimeMessage created:  ");
		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
					isMultipart, CharEncoding.UTF_8);
			log.debug("mimeMessageHelper created:  ");
			messageHelper.setTo(to);
			messageHelper.setFrom(from);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, isHtml);
			log.debug("sending message:  ");
			javaMailSender.send(mimeMessage);
			log.debug("Sent e-mail to User '{}'!", (Object[])to);
		} catch (Exception e) {
			log.warn("E-mail could not be sent to user '{}', exception is: {}",
					to, e.getMessage());
		}
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
		
		//Get the send to email addresses
		final InternetAddress[] emailAddress = getLandstingsamordnareEmailAddresses(landstingCode);
		if(emailAddress == null || emailAddress.length == 0){
			log.warn(String.format("No email addresses found for landstingssamordnare on Eavrop with ÄrendeId %s", arendeId.toString()));
			return;
		}

		Locale locale = LocaleContextHolder.getLocale();
		log.debug("Sending eavrop created mail to '{}' in locale '{}'",
				emailAddress, locale.toString());

		//Retrive all context variables
		String utredningTypeStr = (utredningType!=null)?utredningType.name():"försäkringsmedicinsk utredning";
		final Context ctx = new Context(locale);
		ctx.setVariable("arendeId", arendeId.toString());
		ctx.setVariable("utredningType", utredningTypeStr);
		if(lastAcceptanceDay!=null){
			DateTimeFormatter formatter = DateTimeFormat.forPattern("d MMMM, yyyy");
			String dayString = lastAcceptanceDay.toString(formatter);
			ctx.setVariable("dayStr", dayString);
		}
		
		// Create the HTML body using Thymeleaf
		final String htmlContent = this.templateEngine.process(	"eavropCreatedEmail", ctx);

		// Get subject according to locale
		Object[] args = {utredningTypeStr,arendeId.toString()};
		String subject = messageSource.getMessage("email.eavrop.order.subject", args, locale);
		
		//Send the email
		sendEmail(emailAddress, subject, htmlContent, false, true);
	}
	
	
	/*
	 * Method for retrieving valid email addresses from the landstingssmaordnare 
	 */
	private InternetAddress[] getLandstingsamordnareEmailAddresses(LandstingCode landstingCode) {
		List<InternetAddress> result = new ArrayList<InternetAddress>();
		
		List<Landstingssamordnare> landstingssamordnare =  landstingssamordnareRepository.findByLandstingCode(landstingCode);
		
		if (landstingssamordnare != null) {
			for (Landstingssamordnare samordnare : landstingssamordnare) {
				if (samordnare.getEmail() != null && EmailValidator.getInstance().isValid(samordnare.getEmail())) {
					InternetAddress address;
					try {
						address = new InternetAddress(samordnare.getEmail());
						result.add(address);
					} catch (AddressException e) {
						log.error(" {} not a valid email address", samordnare.getEmail());
					}
				}
			}
		}

		return result.toArray(new InternetAddress[result.size()]);
	}
}
