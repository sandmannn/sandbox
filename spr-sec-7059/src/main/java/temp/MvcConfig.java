
package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


  @Bean
  public ViewResolver jspViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }


//  public void addViewControllers(ViewControllerRegistry registry) {
//    registry.addViewController("/home").setViewName("home");
//    registry.addViewController("/").setViewName("home");
//    registry.addViewController("/hello").setViewName("hello");
//    registry.addViewController("/welcome").setViewName("welcome");
//    registry.addViewController("/admin_only").setViewName("admin_only");
//    registry.addViewController("/user_also").setViewName("user_also");
//    registry.addViewController("/login").setViewName("login");
//  }

}



