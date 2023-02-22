package commerce.hosinsa.global.batch.job

import commerce.hosinsa.domain.coupon.entity.CouponMember
import commerce.hosinsa.domain.coupon.repository.CouponMemberRepository
import commerce.hosinsa.domain.coupon.repository.CouponRepository
import commerce.hosinsa.domain.member.repository.MemberQueryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BirthdayCouponJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val couponMemberRepository: CouponMemberRepository,
    private val memberQueryRepository: MemberQueryRepository,
    private val couponRepository: CouponRepository
) {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun birthdayCouponJob(): Job {
        log.info("Birthday Coupon Job Start")

        return jobBuilderFactory["birthdayJob"]
            .start(birthdayCouponStep())
            .build()
    }

    @Bean
    fun birthdayCouponStep() = stepBuilderFactory["birthdayStep"]
        .tasklet { _, _ ->
            val coupon = couponRepository.findByName(COUPON_NAME)!!
            val members = memberQueryRepository.findMembersWithBirthdayToday()

            members.forEach {

                couponMemberRepository.save(CouponMember(couponMemberId = it.memberId!!, member = it, coupon = coupon))
                log.info("Member : ${it.id}, ${it.name} ")
            }

            log.info("Coupon : ${coupon.couponId}, ${coupon.name} ")

            RepeatStatus.FINISHED
        }
        .build()

    companion object {
        const val COUPON_NAME = "생일쿠폰"
    }
}