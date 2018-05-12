package com.team12.financialservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService customUserService;

//    //Reference to user and role queries stored in application.properties file
//    @Value("${spring.queries.users-query}")
//    private String usersQuery;
//
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;

    //user Details Service verification
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth

                .userDetailsService(customUserService);
       // auth.authenticationProvider(authProvider());


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      // http.csrf().disable() Below Transition day form csrf will display error
        http

                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/registration").permitAll()

                 .antMatchers("/customer/**").hasRole("CUSTOMER")
                .antMatchers("/employee/**").hasRole("EMPLOYEE")

//                .anyRequest().authenticated()


                .and()
                .formLogin()
                .loginPage("/login")
                //.successForwardUrl("/dispatch")
                .defaultSuccessUrl("/dispatch")
                .permitAll()
                .and()
                .logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
               // .logoutSuccessUrl("/")
                .permitAll()
                .and()
               .exceptionHandling()
                .accessDeniedPage("/error/403");

    }

    //    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/dispatch")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login_employee?logout")
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/403");
//
//    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }

}




