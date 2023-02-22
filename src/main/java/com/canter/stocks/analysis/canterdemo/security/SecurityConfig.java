package com.canter.stocks.analysis.canterdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    // injecting dependencies using lazy to avoid cyclic dependencies
    @Autowired
    public SecurityConfig(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // configure authentication
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();

    }

    // password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // set up authentication for users specified in user.properties file
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        Properties users = new Properties();
        InputStream is = getClass().getResourceAsStream("/user.properties");
        users.load(is);

        // add all users in memory user manager
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();

        for (String key : users.stringPropertyNames()) {
            String value = users.getProperty(key);
            String[] tokens = value.split(":");
            String username = tokens[0];
            String password = tokens[1];
            UserDetails userDetails = User.withUsername(username).password(password).authorities("user").build();
            userDetailsService.createUser(userDetails);
        }

        auth.userDetailsService(userDetailsService);
    }

}
