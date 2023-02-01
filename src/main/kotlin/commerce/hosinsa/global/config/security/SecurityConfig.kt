package commerce.hosinsa.global.config.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig{

    @Bean
    @Throws(Exception::class)
    fun webSecurityCustomizer(): WebSecurityCustomizer? {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {

        http.csrf().disable()

        http.formLogin().disable()

        http.authorizeRequests {
            it.antMatchers("/api/v1/member/signUp").permitAll()
                .antMatchers("/api/v1/member/signIn").permitAll()
                .anyRequest().authenticated()
        }

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }

    @Bean
    fun configure(): WebSecurityCustomizer? = WebSecurityCustomizer { web: WebSecurity ->
        web.ignoring()
            .antMatchers(
                "/h2-console/**",
            )
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? = BCryptPasswordEncoder()
}