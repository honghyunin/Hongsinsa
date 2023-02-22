package commerce.hosinsa.global.scheduler

import commerce.hosinsa.global.batch.job.BirthdayCouponJobConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class JobScheduler(
    private val jobLauncher: JobLauncher,
    private val birthdayCouponJobConfiguration: BirthdayCouponJobConfiguration
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(cron = "0 0 5 * * ?") // 매일 오전 5시에
    fun runJob() {
        val confMap: MutableMap<String, JobParameter> = HashMap()

        confMap["time"] = JobParameter(System.currentTimeMillis())

        val jobParameters = JobParameters(confMap)

        try {
            jobLauncher.run(birthdayCouponJobConfiguration.birthdayCouponJob(), jobParameters)
        } catch (e: JobExecutionAlreadyRunningException) {
            log.error(e.message)
        } catch (e: JobInstanceAlreadyCompleteException) {
            log.error(e.message)
        } catch (e: JobParametersInvalidException) {
            log.error(e.message)
        } catch (e: JobRestartException) {
            log.error(e.message)
        }
    }
}