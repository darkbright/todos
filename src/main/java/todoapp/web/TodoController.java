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

    public TodoController(Environment environment, @Value("${site.author}") String siteAuthor) {
        this.environment = environment;
        this.siteAuthor = siteAuthor;
    }

    @RequestMapping("/todos")
    public ModelAndView todos() {
        SiteProperties site = new SiteProperties();
//        site.setAuthor(environment.getProperty("site.author"));
        site.setAuthor(siteAuthor);
        site.setDescription("스프링 MVC");

        ModelAndView mav = new ModelAndView();
        mav.addObject("site", site);
        mav.setViewName("todos");

        return mav;
    }
}
