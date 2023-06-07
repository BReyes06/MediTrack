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
                .antMatchers("/refresh_token").authenticated()
                .antMatchers("/create_account").permitAll()
                .antMatchers("/update_account").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/sms").permitAll()
                .antMatchers(HttpMethod.GET,"/prescription/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/prescription/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/prescription/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/prescription/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/pharmacy/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/pharmacy/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/pharmacy/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/pharmacy/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/doctor/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/doctor/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/doctor/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/doctor/**").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.GET,"/tracker/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/tracker/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/tracker/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/tracker/**").hasAnyAuthority("USER","ADMIN")
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
