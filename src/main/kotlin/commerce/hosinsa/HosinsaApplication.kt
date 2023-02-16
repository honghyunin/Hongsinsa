package commerce.hosinsa

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class HosinsaApplication

fun main(args: Array<String>) {
    runApplication<HosinsaApplication>(*args)
}
