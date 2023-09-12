package commerce.hongsinsa.config.security.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig: WebMvcConfigurer {

    @Value("\${deploy.port}")
    private var SERVER_IP: String? = null

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedOriginPatterns("*")
            .allowedMethods("*")
            .allowedOrigins("http://localhost:8080", "http://$SERVER_IP:8080", "http://$SERVER_IP")
            .maxAge(3000)
    }

}