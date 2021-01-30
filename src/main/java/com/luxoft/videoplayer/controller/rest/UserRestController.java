package com.luxoft.videoplayer.controller.rest;

import com.luxoft.videoplayer.model.User;
import com.luxoft.videoplayer.service.UserService;
import com.luxoft.videoplayer.service.mail.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserRestController {

    private UserService userService;
    private MailService mailService;

    private String sendMailNameFrom;
    private String sendMailEmailFrom;
    private String sendMailEmailMessage;

    public UserRestController(UserService userService,
                              MailService mailService,
                              @Value("${sending.mail.name.from}")String sendMailNameFrom,
                              @Value("${sending.mail.email.from}")String sendMailEmailFrom,
                              @Value("${sending.mail.email.message}")String sendMailEmailMessage) {
        this.userService = userService;
        this.mailService = mailService;
        this.sendMailNameFrom = sendMailNameFrom;
        this.sendMailEmailFrom = sendMailEmailFrom;
        this.sendMailEmailMessage = sendMailEmailMessage;
    }

    @GetMapping("/rest/admin/users")
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/rest/admin/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/rest/admin/users/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/rest/admin/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/rest/user/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        userService.add(user);
        mailService.sendRegisterMessage(sendMailNameFrom, sendMailEmailFrom, user.getEmail(), sendMailEmailMessage);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/rest/admin/users")
    public ResponseEntity<User> editUser(@Valid @RequestBody User user) {
        userService.edit(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
