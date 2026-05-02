package com.internship.tool.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {

    private final EmailService emailService;

    public ReminderService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void sendDailyReminder() {

        String to = "team@example.com";
        String subject = "Daily Incident Review Reminder";

        String body = """
                <h2>Incident Review Reminder</h2>
                <p>Hello Team,</p>
                <p>This is a daily reminder to review pending incidents.</p>
                <p>Please check the dashboard and complete the review before the deadline.</p>
                <br>
                <p>Thank you.</p>
                """;

        emailService.sendEmail(to, subject, body);
    }

    @Scheduled(cron = "0 0 17 * * *")
    public void sendDeadlineAlert() {

        String to = "team@example.com";
        String subject = "Deadline Alert - Pending Incident Review";

        String body = """
                <h2>Deadline Alert</h2>
                <p>Hello Team,</p>
                <p>Some incident reviews may be near the deadline.</p>
                <p>Please review and complete them as soon as possible.</p>
                <br>
                <p>Thank you.</p>
                """;

        emailService.sendEmail(to, subject, body);
    }
}