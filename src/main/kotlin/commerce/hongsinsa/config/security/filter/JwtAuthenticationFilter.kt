package commerce.hongsinsa.config.security.filter

import commerce.hongsinsa.config.security.detail.UserDetailsServiceImpl
import commerce.hongsinsa.config.utils.TokenUtils
import commerce.hongsinsa.exception.CustomException
import commerce.hongsinsa.exception.ErrorCode.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationFilter(private val userDetailsServiceImpl: UserDetailsServiceImpl, private val tokenUtils: TokenUtils): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        if (token != null) {
            if (tokenUtils.isValidToken(token)) {
                val id = tokenUtils.tokenExtractId(token)

                if (id != null)
                    registerUserinfoInSecurityContext(id, request)
            }
        }
        filterChain.doFilter(request, response);
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else null
    }

    private fun registerUserinfoInSecurityContext(id: String, req: HttpServletRequest) {
        try {
            val userDetails: UserDetails = userDetailsServiceImpl.loadUserByUsername(id)
            val usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(req)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        } catch (e: NullPointerException) {
            throw CustomException(MEMBER_NOT_FOUND)
        }
    }
}