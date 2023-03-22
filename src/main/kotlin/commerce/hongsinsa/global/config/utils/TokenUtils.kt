package commerce.hongsinsa.global.config.utils

import commerce.hongsinsa.entity.member.Role
import commerce.hongsinsa.global.exception.CustomException
import commerce.hongsinsa.global.exception.ErrorCode.INPUT_VALUE_NOT_FOUND
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


@Component
class TokenUtils {

    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${secret-key}")
    private var SECRET_KEY: String = ""

    fun generateJwtToken(id: String, roles: MutableList<Role>, tokenType: String): String = Jwts
        .builder()
        .setSubject(id)
        .setHeader(createHeader())
        .setClaims(createClaims(id, roles, tokenType))
        .setExpiration(getExpiration(tokenType))
        .signWith(SignatureAlgorithm.HS256, createSigningKey()).compact()

    fun tokenExtractId(token: String): String? = getClaimsFormToken(token).get("id", String::class.java)

    fun getAuthentication(accessToken: String): Authentication? {

        val claims: Claims = getClaimsFormToken(accessToken)
        if (claims["auth"] == null) {
            throw RuntimeException("권한 정보가 없는 토큰입니다.")
        }

        val authorities: Collection<GrantedAuthority?> =
            Arrays.stream(claims["auth"].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())

        val principal: UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun isValidToken(token: String): Boolean = try {
        val claims = getClaimsFormToken(token)
        log.info("expireTime : ${claims.expiration}")
        log.info("id : ${claims["id"]}")
        log.info("role : ${claims["role"]}")

        true
    } catch (exception: ExpiredJwtException) {
        log.error("Token Expired")
        false
    } catch (exception: JwtException) {
        log.error("Token Tampered")
        false
    } catch (exception: NullPointerException) {
        log.error("Token is Null")
        false
    }

    private fun getExpiration(tokenType: String): Date {
        val ACCESS_TOKEN_EXPIRATION_TIME = Calendar.getInstance().apply { add(Calendar.HOUR, 6) }
        val REFRESH_TOKEN_EXPIRATION_TIME = Calendar.getInstance().apply { add(Calendar.DAY_OF_WEEK, 7) }

        return when(tokenType) {
            "AccessToken" -> ACCESS_TOKEN_EXPIRATION_TIME.time // 6 hour
            "RefreshToken" -> REFRESH_TOKEN_EXPIRATION_TIME.time // 7 day
            else -> {
                throw CustomException(INPUT_VALUE_NOT_FOUND)
            }
        }
    }

    private fun createHeader(): MutableMap<String, Any> {
        val header: MutableMap<String, Any> = HashMap()

        header["typ"] = "JWT"
        header["alg"] = "HS256"
        header["regDate"] = System.currentTimeMillis()

        return header
    }

    private fun createClaims(id: String, roles: MutableList<Role>, tokenType: String): Map<String, Any> {
        val claims = HashMap<String, Any>()

        if(tokenType == "AccessToken") {
            claims["id"] = id
            claims["role"] = roles
        }

        claims["tokenType"] = tokenType

        return claims
    }

    private fun createSigningKey(): Key =
        SecretKeySpec(DatatypeConverter.parseBase64Binary(SECRET_KEY), SignatureAlgorithm.HS256.jcaName)

    private fun getClaimsFormToken(token: String): Claims =
        Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(token).body

    fun createAccessToken(id: String, roles: MutableList<Role>): String = generateJwtToken(id, roles,"AccessToken")

    fun createRefreshToken(id: String, roles: MutableList<Role>): String = generateJwtToken(id, roles, "RefreshToken")
}