package meditrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {
        http.csrf().disable();
        http.cors();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers("/refresh_token").authenticated()
                .antMatchers("/create_account").permitAll()
                .antMatchers("/update_account").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/all_users").hasAnyAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/delete_account/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/sms").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
