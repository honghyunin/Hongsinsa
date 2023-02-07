package commerce.hosinsa.global.config.security

import commerce.hosinsa.global.config.security.detail.UserDetailsServiceImpl
import commerce.hosinsa.global.config.security.filter.JwtAuthenticationFilter
import commerce.hosinsa.global.config.utils.TokenUtils
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val tokenUtils: TokenUtils
){

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

                .antMatchers("/api/v1/audit/available/**").hasRole("ADMIN")
                .antMatchers("/api/v1/brand/update").hasRole("SELLER")
                .antMatchers("/api/v1/coupon/issued").hasRole("ADMIN")
                .antMatchers("/api/v1/coupon/apply").hasRole("ADMIN")
                .antMatchers("/api/v1/coupon/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
        }

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(JwtAuthenticationFilter(userDetailsService, tokenUtils), UsernamePasswordAuthenticationFilter::class.java)

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