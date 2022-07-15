package todoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import todoapp.core.todos.domain.Todo;
import todoapp.web.TodoController.TodoCsvView;

import java.util.List;

/**
 * Spring Web MVC 설정
 *
 * @author springrunner.kr@gmail.com
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public WebMvcConfiguration() {
        logger.debug("스프링 MVC 설정자 생성");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 서블릿 컨텐스트 경로에서 정적 자원 제공
//        registry.addResourceHandler("/assets/**").addResourceLocations("assets/");
//
//        // 파일 경로에서 정적 자원 제공
//        registry.addResourceHandler("/assets/**")
//                .addResourceLocations("file:/workspace/todos/files/assets/");
//
//        // 클래스패스 경로에서 정적 자원 제공
//        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:assets/");
//
//        registry.addResourceHandler("/assets/**")
//                .addResourceLocations(
//                        "assets/",
//                        "file:/workspace/todos/files/assets/",
//                        "classpath:assets/"
//                );
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.viewResolver(new TodoController.TodoCsvViewResolver());

        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new Converter<Todo, String>() {
            @Override
            public String convert(Todo source) {
                return source.toString();
            }
        });

        converters.add(new ObjectToStringHttpMessageConverter(conversionService));
    }

    @Bean(name = "todos")
    public TodoCsvView todoCsvView() {
        return new TodoCsvView();
    }

    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 컴포넌트
     */
    public static class ContentNegotiationCustomizer {

        public void configurer(ContentNegotiatingViewResolver viewResolver) {
            // TODO ContentNegotiatingViewResolver 사용자 정의
        }

    }

}
