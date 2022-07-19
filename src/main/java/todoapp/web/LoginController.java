package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import todoapp.core.user.domain.User;
import todoapp.web.model.SiteProperties;

@Controller
public class LoginController {

    private final SiteProperties siteProperties;

    public LoginController(SiteProperties siteProperties) {
        this.siteProperties = siteProperties;
    }

    @GetMapping("/login")
    public void loginForm() {

    }

}
