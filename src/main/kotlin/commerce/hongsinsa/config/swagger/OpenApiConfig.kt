package commerce.hongsinsa.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "Hongsinsa API Document",
        description = "Hongsinsa API 명세서입니다",
        version = "v0.0.1"
    )
)
@Configuration
class OpenApiConfig