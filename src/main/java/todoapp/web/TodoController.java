package todoapp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import todoapp.web.model.SiteProperties;

import javax.validation.Valid;

@Controller
public class TodoController {

    private Environment environment;
    private String siteAuthor;
    private SiteProperties siteProperties;

    public TodoController(Environment environment, @Value("${site.author}") String siteAuthor, SiteProperties siteProperties) {
        this.environment = environment;
        this.siteAuthor = siteAuthor;
        this.siteProperties = siteProperties;
    }

    @RequestMapping("/todos")
    public ModelAndView todos() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("site", siteProperties);
        mav.setViewName("todos");

        return mav;
    }
}
