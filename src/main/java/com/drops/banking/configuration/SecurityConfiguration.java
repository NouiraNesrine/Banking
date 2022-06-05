package com.drops.banking.configuration;

import com.drops.banking.services.implementation.UserLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserLoaderService userLoader;
    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
    @Bean
    public DaoAuthenticationProvider getProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userLoader);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("accept", "accept-encoding", "authorization", "content-type",
                "dnt", "origin", "user-agent", "x-csrftoken", "x-requested-with","Access-Control-Allow-Origin"));

        configuration.setAllowCredentials(false);

        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("supervisor").password(passwordEncoder().encode("0123")).roles("supervisor");
        auth.inMemoryAuthentication().withUser("banker").password(passwordEncoder().encode("0123")).roles("banker");
        auth.inMemoryAuthentication().withUser("customer").password(passwordEncoder().encode("0123")).roles("customer");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credentials,active from user where username=?")
                .authoritiesByUsernameQuery("select username as principal , role as role from user u , role r where username = ? and u.role_id =r.id")
                .rolePrefix("ROLE_").passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http)  throws Exception{
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/supervisor**/**/**").hasRole("supervisor")
                .antMatchers("/banker**/**/**/**").hasRole("banker")
                .antMatchers("/customer**/**/**/**").hasRole("customer")
                .antMatchers("/").hasRole("user")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
        super.configure(http);
    }

}
