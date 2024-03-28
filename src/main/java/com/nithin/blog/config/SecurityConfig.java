package com.nithin.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.firewall.HttpFirewall;
// import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// import com.nithin.blog.security.CustomUserDetailService;
import com.nithin.blog.security.JwtAuthenticationEntryPoint;
import com.nithin.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        public static final String[] PUBLIC_URLS = {
                        "/api/v1/auth/**",
                        "/v3/api-docs/**", // Include v3/api-docs mapping
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**"
        };

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private AuthenticationProvider authenticationProvider;

        // @SuppressWarnings("removal")
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(
                            crsf -> crsf
                                    .disable())
                    .authorizeHttpRequests(
                            authorizeRequests -> authorizeRequests
                                    .requestMatchers(PUBLIC_URLS).permitAll()
                                    .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                                    .anyRequest()
                                    .authenticated())
                    .sessionManagement(
                            sessionManagement -> sessionManagement
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .exceptionHandling(handling -> handling.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(this.jwtAuthenticationFilter,
                            UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        // @Bean
        // public HttpFirewall httpFirewall() {
        // StrictHttpFirewall firewall = new StrictHttpFirewall();
        // firewall.setAllowUrlEncodedSlash(true); // Allow encoded slashes
        // return firewall;
        // }

}
