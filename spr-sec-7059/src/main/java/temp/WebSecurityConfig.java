package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception{
    builder.inMemoryAuthentication()
            .withUser("admin").password("{noop}admin").roles("admin").and()
            .withUser("user").password("{noop}user").roles("user");
  }

// issue content

  @Bean
  public RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_admin > ROLE_user");
    return roleHierarchy;
  }


// tutorial content
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//            .authorizeRequests()
//            .antMatchers("/", "/home").permitAll()
//            .antMatchers("/admin_only").hasRole("admin")
//            .antMatchers("/user_also").hasRole("user")
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .permitAll()
//            .and()
//            .logout()
//            .permitAll();
//  }
}