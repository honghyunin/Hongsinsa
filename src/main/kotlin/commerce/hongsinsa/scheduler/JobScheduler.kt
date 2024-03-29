package commerce.hongsinsa.scheduler

import commerce.hongsinsa.batch.job.BirthdayCouponJobConfig
import commerce.hongsinsa.batch.job.CouponValidCheckJobConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
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
    private val birthdayCouponJobConfig: BirthdayCouponJobConfig,
    private val couponValidCheckJobConfig: CouponValidCheckJobConfig
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Scheduled(cron = "0 0 5 * * ?") // 매일 오전 5시에
    fun runBirthdayCouponJob() {
        jobLauncherRun(birthdayCouponJobConfig.birthdayCouponJob(), createJobParameters())
    }

    @Scheduled(cron = "0 0 6 * * ?") // 매일 오전 6시에
    fun runCouponValidCheckJob() {
        jobLauncherRun(couponValidCheckJobConfig.couponValidCheckJob(), createJobParameters())
    }

    private fun createJobParameters(): JobParameters {
        val confMap: MutableMap<String, JobParameter> = HashMap()

        confMap["time"] = JobParameter(System.currentTimeMillis())

        return JobParameters(confMap)
    }

    private fun jobLauncherRun(job: Job, jobParameters: JobParameters) = try {
        jobLauncher.run(job, jobParameters)
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