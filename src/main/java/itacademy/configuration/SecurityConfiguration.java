package itacademy.configuration;

import itacademy.security.filter.AuthenticationTokenFilter;
import itacademy.service.security.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .authorizeRequests()
                .mvcMatchers("/authentication/**").permitAll()
                .mvcMatchers(HttpMethod.GET,
                        "/cars/free/**", "/cars/find/**", "/carInfo/**").permitAll()
                .mvcMatchers(HttpMethod.PUT,
                        "/users/**", "/addresses/**", "/rentCar/cancel/**").hasAnyRole("ADMIN", "USER")
                .mvcMatchers(HttpMethod.POST, "/rentCar/**").hasRole("USER")
                .mvcMatchers(
                        "/users/**", "/addresses/**",
                        "/cars/**", "/carInfo/**",
                        "/carModels/**", "/engines/**",
                        "/rentCar/**", "/userFines/**").hasRole("ADMIN");

        final AuthenticationTokenFilter tokenFilter = new AuthenticationTokenFilter(tokenService, userDetailsService);
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
