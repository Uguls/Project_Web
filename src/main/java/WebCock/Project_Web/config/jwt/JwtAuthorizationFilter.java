//package WebCock.Project_Web.config.jwt;
//
//import WebCock.Project_Web.entity.model.Member;
//import WebCock.Project_Web.repository.MemberRepository;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//// 시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있음.
//// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
//// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탐.
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private final MemberRepository memberRepository;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
//        super(authenticationManager);
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
//        String header = request.getHeader(JwtProperties.HEADER_STRING);
//
//        String jwtHeader = request.getHeader("Authorization");
//        System.out.println("jwtHeader : " + jwtHeader);
//
//        if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        System.out.println("header : "+header);
//        String token = request.getHeader(JwtProperties.HEADER_STRING)
//                .replace(JwtProperties.TOKEN_PREFIX, "");
//
//        try {
//            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token);
//            String username = jwt.getClaim("uid").asString();
//            if (username != null) {
//                Member memberEntity = memberRepository.findByUid(username);
//
//                PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);
//
//                Authentication authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                principalDetails,
//                                null,
//                                principalDetails.getAuthorities());
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//            chain.doFilter(request, response);
//        } catch (JWTVerificationException e) {
//            System.out.println("시발 개같은거");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
//        }
//    }
//}