package com.epa.epa.authentication;

import com.epa.epa.user.User;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("authentication")
public class AuthenticationController {

  private final AuthenticationService authenticationService;


  @PostMapping
  @RequiresNoPermission
  public ResponseEntity<Token> authenticateUser(
      @RequestBody User userCredentials) {

    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

    Token jwt = authenticationService.constructJWT(now, userCredentials);

    return authenticationService.isValidLogin(userCredentials) ?
        ResponseEntity.ok(new Token(jwt.getEmployeeId(), jwt.getToken(), jwt.getExpiryDate())) :
        ResponseEntity.badRequest().build();
  }
}

