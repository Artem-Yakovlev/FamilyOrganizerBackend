import com.badger.familyorgbe.repository.jwt.IJwtRepository
import com.badger.familyorgbe.repository.users.IUsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtTokenFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var usersRepository: IUsersRepository

    @Autowired
    private lateinit var jwtRepository: IJwtRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        // Get authorization header and validate
        val header = request.getHeader(HttpHeaders.AUTHORIZATION).orEmpty()

        if (header.isEmpty() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        // Get jwt token and validate
        val token = header.split(" ").toTypedArray()[1].trim { it <= ' ' }
        if (!jwtRepository.validateToken(token)) {
            chain.doFilter(request, response)
            return
        }

        val email = jwtRepository.getEmail(token)
        if (email == null) {
            chain.doFilter(request, response)
            return
        }

        // Get user identity and set it on the spring security context
        val userDetails: UserDetails? = usersRepository
            .findByEmail(email)?.toUserDetails()

        val authentication = UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails?.authorities ?: emptyList()
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}