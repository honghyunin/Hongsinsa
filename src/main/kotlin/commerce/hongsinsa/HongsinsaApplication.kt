package commerce.hongsinsa

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class HongsinsaApplication

fun main(args: Array<String>) {
    val application = SpringApplication(HongsinsaApplication::class.java)
    application.addListeners(ApplicationPidFileWriter())
    application.run(*args)
}
