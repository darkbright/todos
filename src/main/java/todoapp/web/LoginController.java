package todoapp.web;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.application.UserRegistration;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

@Controller
public class LoginController {
    
    private final UserPasswordVerifier verifier;
    private final UserRegistration registration;
    private final UserSessionRepository userSessionRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public LoginController(UserPasswordVerifier verifier, UserRegistration registration, UserSessionRepository userSessionRepository) {
        this.verifier = Objects.requireNonNull(verifier);
        this.registration = Objects.requireNonNull(registration);
        this.userSessionRepository = Objects.requireNonNull(userSessionRepository);
    }

    @GetMapping("/login")
    public String loginForm() {
        if (Objects.nonNull(userSessionRepository.get())) {
            return "redirect:/todos"; 
        }
        return "login";
    }
    
    @PostMapping("/login")    
    public String loginProcess(@Valid LoginCommand command, Model model) {
        log.debug("login command: {}", command);
        
        User user;
        try {
            // 1. ????????? ???????????? ???????????? ?????? ??????: ???????????? ?????? ??? ????????? ??????
            user = verifier.verify(command.getUsername(), command.getPassword());
        } catch (UserEntityNotFoundException error) {
            // 2. ???????????? ?????? ??????: ???????????? ?????? ??? ????????? ??????
            user = registration.join(command.getUsername(), command.getPassword());
        } catch (UserPasswordNotMatchedException error) {
            // 3. ??????????????? ?????? ??????: login ???????????? ???????????????, ?????? ????????? ??????
            model.addAttribute("message", error.getMessage());
            return "login";
        }
        userSessionRepository.set(new UserSession(user));
        
        return "redirect:/todos";
    }
    
    @RequestMapping("/logout")
    public View logout() {
        userSessionRepository.clear();
        return new RedirectView("/todos");
    }
    
    /*
     * ?????? ??? ????????? ????????? ??????: login ???????????? ???????????????, ?????? ????????? ??????
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException error, Model model) {
        model.addAttribute("bindingResult", error.getBindingResult());
        model.addAttribute("message", "?????? ?????? ????????? ???????????? ?????????.");
        return "login";
    }
    
    static class LoginCommand {
        @Size(min = 4, max = 20)
        String username;
        String password;
        
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        
        @Override
        public String toString() {
            return String.format("[username=%s, password: %s]", username, password);
        }
    }

}
