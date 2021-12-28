package kr.co.kesti.iitp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/*")
//                .access("hasIpAddress('192.168.1.106')");
                .access("hasIpAddress('localhost') or hasIpAddress('127.0.0.1') or hasIpAddress('192.168.1.108')");
    }
}
