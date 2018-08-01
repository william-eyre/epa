package com.epa.epa.authentication;

import static com.epa.epa.authentication.AuthenticationConstants.IDENTITY_KEY;
import static com.epa.epa.authentication.AuthenticationConstants.KEY;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
public class PermissionCheckInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;

      RequiresNoPermission noPermission = handlerMethod
          .getMethodAnnotation(RequiresNoPermission.class);
      if (noPermission != null) {
        return true;
      }

      RequiresPermission permission = handlerMethod.getMethodAnnotation(RequiresPermission.class);
      if (permission != null) {
        String header = request.getHeader("X-AUTHORIZATION");
        if (header == null) {
          return unauthorized(response);
        } else {
          try {
            String identity = Jwts.parser().setSigningKey(KEY)
                .parseClaimsJws(header).getBody()
                .get("identity", String.class);

            request.setAttribute(IDENTITY_KEY, identity);
            return true;
          } catch (JwtException e) {
            return unauthorized(response);
          }
        }
      }
    }
    log.error("No permission annotation on method");
    return unauthorized(response);
  }

  private boolean unauthorized(HttpServletResponse response) {
    response.setStatus(401);
    return false;
  }
}