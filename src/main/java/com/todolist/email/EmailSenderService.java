package com.todolist.email;


import com.todolist.task.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmailService emailService;

    private void sendGenericEmail(String toEmail,
                          String subject,
                          String emailBody){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("no-reply@todolistapp.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(emailBody);

        javaMailSender.send(message);
    }

    public void sendEmailToUsersWithExpiredPendingTasks(List<Task> tasks){
        //I prefer to use this for so the mail montage logic is more legible

        tasks.forEach(task -> sendGenericEmail(task.getUser().getEmail(),
                "Task :" + task.getName() + " expired!",
                        "Hi " + task.getUser().getLogin() + " today one of yours tasks was expired witouting beeing done!" +
                                "\nCongratulations from TodoList App"));

    }

}
