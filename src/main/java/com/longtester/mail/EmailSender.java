package com.longtester.mail;

import com.longtester.helpers.PropertiesHelper;
import com.longtester.helpers.SystemHelper;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class EmailSender {
    public static void sendMail() {
        final String fromEmail = EmailConfig.FROM;
        final String password = EmailConfig.PASSWORD;
        final String toEmail = EmailConfig.TO;

        Properties props = new Properties();
        props.put("mail.smtp.host", EmailConfig.SERVER);
        props.put("mail.smtp.port", EmailConfig.PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("📌 Selenium Test Screenshots");

            // Nội dung text
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Chào bạn,\n\nĐính kèm tất cả ảnh screenshot sau khi chạy Selenium Test.");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            // 📂 Lấy tất cả file trong thư mục screenshots
            File folder = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("EXPORT_CAPTURE_PATH"));
            File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

            if (listOfFiles != null && listOfFiles.length > 0) {
                for (File file : listOfFiles) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                }
            } else {
                System.out.println("⚠ Không tìm thấy file ảnh trong thư mục screenshots.");
            }

            // set multipart vào email
            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

