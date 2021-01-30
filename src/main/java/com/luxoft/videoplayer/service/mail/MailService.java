package com.luxoft.videoplayer.service.mail;

public interface MailService {

    void sendRegisterMessage(
            String nameFrom,
            String emailFrom,
            String emailTo,
            String message);

}
