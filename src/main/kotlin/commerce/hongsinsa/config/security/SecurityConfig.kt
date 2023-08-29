package commerce.hongsinsa.config.security

import commerce.hongsinsa.config.security.detail.UserDetailsServiceImpl
import commerce.hongsinsa.config.security.filter.JwtAuthenticationFilter
import commerce.hongsinsa.config.utils.TokenUtils
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry




@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val tokenUtils: TokenUtils
) {

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

        http
            .cors().disable()
            .csrf().disable()
            .formLogin().disable()

        http.authorizeRequests {
            it.antMatchers("/api/v1/members/signUp").permitAll()
                .antMatchers("/api/v1/members/signIn").permitAll()
                .antMatchers("/api/v1/members/**").hasRole("MEMBER")
                .antMatchers("/api/v1/memberCoupons/**").hasRole("MEMBER")

                .antMatchers("/api/v1/orders/**").hasRole("MEMBER")

                .antMatchers("/api/v1/products/**").hasRole("SELLER")
                .antMatchers(HttpMethod.GET, "/api/v1/products/**").hasRole("MEMBER")

                .antMatchers("/api/v1/audit/available/**").hasRole("ADMIN")

                .antMatchers("/api/v1/brands/**").hasRole("SELLER")

                .antMatchers("/api/v1/coupons/save").hasRole("ADMIN")

                .antMatchers("/api/v1/carts/**").hasRole("MEMBER")

                .antMatchers( "/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()


                .anyRequest().authenticated()
        }

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(
            JwtAuthenticationFilter(userDetailsService, tokenUtils),
            UsernamePasswordAuthenticationFilter::class.java
        )

        return http.build()
    }

    @Bean
    fun configure(): WebSecurityCustomizer? = WebSecurityCustomizer { web: WebSecurity ->
        web.ignoring()
            .antMatchers("/h2-console/**")
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? = BCryptPasswordEncoder()
}