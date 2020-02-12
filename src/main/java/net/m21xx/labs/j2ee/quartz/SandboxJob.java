package net.m21xx.labs.j2ee.quartz;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SandboxJob extends JobDetail implements Job {

	private static final long serialVersionUID = 1L;
	private static final String GRUPO_ROTINAS = "GRUPO_ROTINAS";

	public SandboxJob() {
		setName(getClass().getSimpleName());
		setGroup(GRUPO_ROTINAS);
		setJobClass(getClass());
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Hello, world, inside a job!");
		
		try {
			dispatchMail();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void dispatchMail() throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", "localhost");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "false");
		
		Session session = Session.getDefaultInstance(props);
		session.setDebug(false);
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("masicunha@tjba.jus.br"));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress("ardigitalti@tjba.jus.br"));
		msg.setSubject("Assunto teste");
		msg.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
		
		Transport.send(msg);
	}
	

}
