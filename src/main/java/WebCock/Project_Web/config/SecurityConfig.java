package WebCock.Project_Web.config;

import WebCock.Project_Web.config.jwt.JwtAuthenticationFilter;
import WebCock.Project_Web.config.jwt.JwtTokenProvider;
import WebCock.Project_Web.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    CorsConfig corsConfig;

    @Autowired
    MemberRepository memberRepository;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .authorizeRequests(authorize -> authorize
                        .antMatchers("/user/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                        .antMatchers("/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                        .anyRequest().permitAll())
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
