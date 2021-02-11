package com.luxoft.videoplayer.controller;

import com.luxoft.videoplayer.service.mail.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.luxoft.videoplayer.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import com.luxoft.videoplayer.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class MainController {

    private UserService userService;
    private MailService mailService;

    private String sendMailNameFrom;
    private String sendMailEmailFrom;

    public MainController(UserService userService,
                               MailService mailService,
                               @Value("${sending.mail.name.from}")String sendMailNameFrom,
                               @Value("${sending.mail.email.from}")String sendMailEmailFrom) {
        this.userService = userService;
        this.mailService = mailService;
        this.sendMailNameFrom = sendMailNameFrom;
        this.sendMailEmailFrom = sendMailEmailFrom;
    }

    @GetMapping(value = "/user")
    public String userPage(@ModelAttribute("user") User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().getClass() == UserDetails.class) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addAttribute("user", userDetail);
        } else if (auth.getPrincipal().getClass() == DefaultOidcUser.class) {
            DefaultOidcUser defaultOidcUser = (DefaultOidcUser) auth.getPrincipal();
            model.addAttribute("user", defaultOidcUser);
        }
        return "userPage";
    }

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping(value = "/admin/user")
    public String adminUserPage(@ModelAttribute("user") User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().getClass() == UserDetails.class) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addAttribute("user", userDetail);
        } else if (auth.getPrincipal().getClass() == DefaultOidcUser.class) {
            DefaultOidcUser defaultOidcUser = (DefaultOidcUser) auth.getPrincipal();
            model.addAttribute("user", defaultOidcUser);
        }
        return "adminUserPage";
    }

    @GetMapping(value = "/admin/videos")
    public String adminPageVideos() {
        return "adminPageVideos";
    }

    @RequestMapping(value = { "/", "/login" }, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginPost(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if(principal != null) {
            modelAndView.setViewName("redirect:/user");
            return modelAndView;
        }

        if (error != null) {
            modelAndView.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        }

        if (logout != null) {
            modelAndView.addObject("msg", "You've been logged out successfully.");
        }

        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid login and password!";
        } else if (exception instanceof LockedException) {
            error = "Invalid login and password!";
        } else {
            error = "Invalid login and password!";
        }
        return error;
    }

    @GetMapping(value = "/403")
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                System.out.println(userDetail);
                model.addObject("login", userDetail.getUsername());
            }
        } catch (ClassCastException e) {
            String name = auth.getName();
            System.out.println(name);
            model.addObject("login", name);
        }
        model.setViewName("errorPage");
        return model;
    }
}
