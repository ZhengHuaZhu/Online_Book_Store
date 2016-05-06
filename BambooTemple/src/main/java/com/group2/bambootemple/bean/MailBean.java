
package com.group2.bambootemple.bean;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jodd.mail.EmailAttachment;

/**
 * This class is a basic email data bean. 
 * 
 * @author Marjorie Morales
 *
 */
public class MailBean {

	private final Logger log = LoggerFactory.getLogger(getClass().getName());

	// The address or addresses that this email is being sent to
	private ArrayList<String> toField;
	
	// The sender of the email
	private String fromField;

	// The subject line of the email
	private String subject;

	// Plain text part of the email
	private String textMessageField;
	
	// Plain html part of the email
	private String htmlMessageField;
	
	//email Attachment
	private ArrayList<EmailAttachment> attachment;
	
	private ArrayList<EmailAttachment> embed;
	
	/**
	 * Default constructor for a new mail message waiting to be sent
	 */
	public MailBean() {
		super();
		this.toField = new ArrayList<>();
		this.fromField = "";
		this.subject = "";
		this.textMessageField = "";
		this.htmlMessageField = "";
		this.attachment = new ArrayList<>();
		this.embed = new ArrayList<>();
	}

	/**
	 * Constructor for creating messages from either a form or a database record
	 * 
	 * @param toAddress
	 * @param fromField
	 * @param subjectField
	 * @param textMessageField
	 * @param htmlMessageField
	 */
	public MailBean(final ArrayList<String> toAddress, final String fromField, final String subjectField,
			final String textMessageField, final String htmlMessageField, final ArrayList<EmailAttachment> attachment, final ArrayList<EmailAttachment> embed) {
		super();
		this.toField = toAddress;
		this.fromField = fromField;
		this.subject = subjectField;
		this.textMessageField = textMessageField;
		this.htmlMessageField = htmlMessageField;
		this.attachment = attachment;
		this.embed = embed;
	}
	
	/**
	 * This methods returns the attachment.
	 * @return attachment
	 */
	public final ArrayList<EmailAttachment> getAttachment(){
		return attachment;
	}
	
	/**
	 * This method returns the email address of the sender.
	 * @return the fromField
	 */
	public final String getFrom() {
		return fromField;
	}

	/**
	 * This method set the email address of the recipient.
	 * @param fromAddress
	 *            the fromField to set
	 */
	public final void setFrom(final String fromAddress) {
		this.fromField = fromAddress;
	}

	/**
	 * This method returns the subject of the mail that was sent.
	 * @return the subjectField
	 */
	public final String getSubject() {
		return subject;
	}

	/**
	 * This method sets the subject of the mail that will be send.
	 * @param subjectField
	 *            the subjectField to set
	 */
	public final void setSubject(final String subjectField) {
		this.subject = subjectField;
	}

	/** 
	 * This method returns the text message that was sent.
	 * @return the textMessageField
	 */
	public final String getTextMessageField() {
		return textMessageField;
	}

	/**
	 * This method set the text message that will be send.
	 * @param textMessageField
	 *            the textMessageField to set
	 */
	public final void setTextMessageField(final String textMessageField) {
		this.textMessageField = textMessageField;
	}
	
	/**
	 * This method returns the html message  that was sent.
	 * @return the htmlMessageField
	 */
	public final String getHtmlMessageField() {
		return htmlMessageField;
	}
	
	/**
	 * This message set the html message that will be send.
	 * @param htmlMessageField
	 *            the htmlMessageField to set
	 */
	public final void setHtmlMessageField(final String htmlMessageField) {
		this.htmlMessageField = htmlMessageField;
	}
	
	public final ArrayList<EmailAttachment> getEmbed(){
		return embed;		
	}

	/**
	 * There is no set when working with collections. When you get the ArrayList
	 * you can add elements to it. A set method implies changing the current
	 * ArrayList for another ArrayList and this is something we rarely do with
	 * collections.
	 * 
	 * @return the toField --> An array String of email addresses of the person or persons who receives the mail. 
	 */
	public final ArrayList<String> getTo() {
		return toField;
	}
        

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result + ((embed == null) ? 0 : embed.hashCode());
		result = prime * result + ((fromField == null) ? 0 : fromField.hashCode());
		result = prime * result + ((htmlMessageField == null) ? 0 : htmlMessageField.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + ((textMessageField == null) ? 0 : textMessageField.hashCode());
		result = prime * result + ((toField == null) ? 0 : toField.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
			if (this == obj)	return true;
			if (obj == null)	return false;	
			
			ArrayList<MailBean> other = (ArrayList<MailBean>) obj;
			
			if (this.getSubject().equals("") && this.subject == null) {
				log.info("The sending subjectField is null");
				if (!other.get(0).subject.equals("") && other.get(0).subject != null){
					log.info("The subjectField that was received is not null or empty.");
						return false;
					}
			} else {
					if (!this.subject.trim().equals(other.get(0).subject.trim())){
						log.info("      Subject:\t"		+ this.subject			+ "\t\t result:\t"		+ other.get(0).subject);
						return false;
					}
			}
			
			if (this.fromField == null) {
				log.info("The sending fromField is null");
				if (other.get(0).fromField != null){
					log.info("The From field that was received is not null or empty.");
					return false;
				}
			} else{
				if (!this.fromField.trim().equals(other.get(0).fromField.trim())){
					log.info("      From:\t"		+ this.fromField			+ "\t\t result:\t"		+ other.get(0).fromField);
						return false;
					}
			}
			
			if (this.toField == null) {
				log.info("The sending toField is null");
				if (other.get(0).toField != null){
					log.info("The to field that was received is not null or empty.");
					return false;
				}
			} else {
				for(int i=0; i < this.toField.size(); i++ )
					if (!this.getTo().get(i).equals(other.get(0).getTo().get(i))){
						log.info("        To:\t"	+ this.getTo().get(i)		+ "\t result:\t"		+ other.get(0).getTo().get(i));
						return false;	
					}
			}
			
			if (this.textMessageField.equals("") && this.textMessageField == null) 
			{
				log.info("The sending text is null or empty");
				if (!other.get(0).textMessageField.equals("") && other.get(0).textMessageField != null)
				{
					log.info("The text message that was received is not null or empty.");
					return false;
				}
			} else {
				if (!this.textMessageField.trim().equals(other.get(0).textMessageField.trim())){
					log.info("      Text:\t"		+ this.textMessageField.trim() 	+ 	"="					+ this.textMessageField.trim().length()
							+ "\t result:\t" 		+ other.get(0).textMessageField.trim()
							+ "="					+ other.get(0).textMessageField.trim().length());	
					return false;
				}
			}
			
			if (this.htmlMessageField.equals("") && this.htmlMessageField.equals(null)) 
			{
				log.info("The sending text is null or empty");
				if (!(other.get(0).htmlMessageField.equals("")) && !(other.get(0).htmlMessageField.equals(null)))
				{		
					log.info("The sending HTML TEXT is NOT null or empty");
					return false;
				}
			} else {
				if (!this.htmlMessageField.trim().equals(other.get(0).htmlMessageField.trim()))
				{
					log.info("      Html:\t"		+ this.htmlMessageField.trim() 	+ 	"="					+ this.htmlMessageField.trim()
							+ "\t result:\t" 		+ other.get(0).htmlMessageField.trim()	
							+ "="					+ other.get(0).htmlMessageField.trim().length());			
					return false;
				}
			}
			
			if (this.attachment == null){
				log.info("The sending attachment is null or empty");
				if (other.get(0).attachment != null){
					log.info("The sending text is not null or empty");
					return false;
				}
			}else{
				for(int i=0; i < this.attachment.size(); i++){
					byte[] sentAttach = this.getAttachment().get(i).toByteArray();
					byte[] receiveAttach = other.get(0).getAttachment().get(i).toByteArray();
					if(sentAttach.equals(receiveAttach)){
						log.info("The sent attachment is not the same as received attachment.");
						return false;
					}
				}
			}
			if (this.embed == null){
				log.info("The sending embed is null or empty");
				if (other.get(0).embed != null){
					log.info("The sending attachment is not null or empty");
					return false;
				}
			}else{
				for(int i=0; i < this.embed.size(); i++){
					byte[] sentEmbed = this.getEmbed().get(i).toByteArray();
					byte[] receiveEmbed = other.get(0).getEmbed().get(i).toByteArray();
						if(sentEmbed.equals(receiveEmbed)){
							log.info("The sent embedded image is not the same as received embedded image.");
							return false;
						}
					}	
				}
		return true;
	}
}