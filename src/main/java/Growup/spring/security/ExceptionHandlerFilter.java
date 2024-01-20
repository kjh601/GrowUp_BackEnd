package Growup.spring.security;

import Growup.spring.constant.status.ErrorStatus;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            // JwtAuthenticationFilter에서 발생하는 에러를 여기서 캐치해서 예외를 던지기 위함.
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {

            //토큰의 유효기간 만료
            log.error("만료된 토큰입니다");
            JwtAuthenticationFilter.setErrorResponse(response, ErrorStatus.JWT_EXPIRED);

        } catch (JwtException | IllegalArgumentException e) {

            //유효하지 않은 토큰
            log.error("유효하지 않은 토큰이 입력되었습니다.");
            JwtAuthenticationFilter.setErrorResponse(response, ErrorStatus.JWT_INVALID);//다시 로그인

        } catch (UsernameNotFoundException e) {

            //사용자 찾을 수 없음
            log.error("사용자를 찾을 수 없습니다.");
            JwtAuthenticationFilter.setErrorResponse(response, ErrorStatus.USER_NOT_FOUND);

            filterChain.doFilter(request, response);
        } catch (NullPointerException e) {
            // 토큰이 없는 경우
            log.error("토큰이 없습니다.");
            JwtAuthenticationFilter.setErrorResponse(response, ErrorStatus.JWT_EMPTY); // 토큰이 없음

        }

    }
}
