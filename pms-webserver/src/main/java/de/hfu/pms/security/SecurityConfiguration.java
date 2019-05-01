package de.hfu.pms.security;

import de.hfu.pms.dao.UserDao;
import de.hfu.pms.service.UserService;
import de.hfu.pms.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static String REALM="PMS-Webserver";

    private final UserService userService;

    @Autowired
    public SecurityConfiguration(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

        //load the Users from the database
        for(de.hfu.pms.model.User user: userService.getUserList()){
            auth.inMemoryAuthentication().withUser(user.getUsername()).password("{noop}"+user.getPassword()).roles(user.getRoleAsString());
        }
        //todo delete testuser before going live
        auth.inMemoryAuthentication().withUser("testuser").password("{noop}admin").roles("administrator");

        //load Users from runtime
        auth.userDetailsService(inMemoryUserDetailsManager());

    }

    //configuration of the access
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").hasRole("administrator")
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/student/**").hasAnyRole("user","administrator")
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.

    }

    //used to load users in runtime
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
    
}