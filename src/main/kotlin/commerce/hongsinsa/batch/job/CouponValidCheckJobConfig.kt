package commerce.hongsinsa.batch.job

import commerce.hongsinsa.repository.coupon.CouponMemberQueryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.transaction.Transactional

@Configuration
class CouponValidCheckJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val couponMemberQueryRepository: CouponMemberQueryRepository
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun couponValidCheckJob(): Job = jobBuilderFactory["couponValidCheckJob"]
        .start(couponValidCheckStep())
        .build()

    @Transactional
    @Bean
    fun couponValidCheckStep() = stepBuilderFactory["couponValidCheckStep"]
        .tasklet { _, _ ->

            val coupons = couponMemberQueryRepository.findByExpiredCoupon()

            coupons.forEach { coupon ->
                coupon.status = 'E'
            }

            RepeatStatus.FINISHED
        }.build()
}