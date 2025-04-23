package com.ksv.hrms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.ksv.hrms.entity.LeaveRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {
	
	private static final String rejectHeader="Leave Request Rejected";
	private static final String rejectResponse="Your leave request has been rejected due to business needs during that timeframe.";
	private static final String approveHeader="Leave Request Approved";
	private static final String approveReaponse="Your requested leave from has been granted. Please ensure all responsibilities are transitioned smoothly and stay in touch in case of any updates.";
	
	
	private MimeMessage mimeMessage;
	private MimeMessageHelper messageHelper;
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendLeaveResponse(LeaveRequest leaveRequest,String toEmail) throws MessagingException {
		
		mimeMessage=javaMailSender.createMimeMessage();
		messageHelper=new MimeMessageHelper(mimeMessage);
		messageHelper.setFrom("dnaveenkumar402@gmail.com");
		messageHelper.setTo(toEmail);
		if(leaveRequest.isHeadapproval()==true && leaveRequest.isHrApproval()==true) {
			messageHelper.setSubject("Leave request approved");
			messageHelper.setText(mailMessage(leaveRequest,approveHeader,approveReaponse), true);
			javaMailSender.send(mimeMessage);
		}
		if(leaveRequest.isHeadapproval()==false && leaveRequest.isHrApproval()==false){
			messageHelper.setSubject("Leave request rejected");
			messageHelper.setText(mailMessage(leaveRequest,rejectHeader,rejectResponse),true);
			javaMailSender.send(mimeMessage);
		}
		

	}

	private String mailMessage(LeaveRequest leaveRequest,String header,String response) {
		return "<div style='font-family: Arial, sans-serif;'>"
                + "<div style='background-color: beige; padding: 15px; text-align: center; font-size: 18px; font-weight: bold;'>"+ header+ "</div>"
                + "<div style='background-color: ivory; padding: 20px; font-size: 14px;'>"
                + "<p>"+response+"</p>"
                + "<p><strong>Leave Reason:</strong> " + leaveRequest.getReason() + "</p>"
                + "<p><strong>From Date:</strong> " + leaveRequest.getFromDate() + "</p>"
                + "<p><strong>To Date:</strong> " + leaveRequest.getToDate() + "</p>"
                + "</div>"
                + "</div>";
	}

}
