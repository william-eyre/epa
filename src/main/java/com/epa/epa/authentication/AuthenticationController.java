package com.epa.epa.authentication;

import static com.epa.epa.authentication.AuthenticationConstants.KEY;

import com.epa.epa.encryption.PasswordEncryption;
import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
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

  private final UserRepository userRepository;
  private final PasswordEncryption passwordEncryption;

  @PostMapping
  @RequiresNoPermission
  public ResponseEntity<AuthenticationResponse> authenticateUser(
      @RequestBody User userCredentials) {

    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

    return isValidLogin(userCredentials) ?
        ResponseEntity.ok(new AuthenticationResponse(constructJWT(now, userCredentials))) :
        ResponseEntity.badRequest().build();
  }

  private boolean isValidLogin(@RequestBody User userCredentials) {
    User user = userRepository.findByEmployeeId(userCredentials.getEmployeeId());

    return userCredentials.getEmployeeId().equals(user.getEmployeeId()) &&
        passwordEncryption.verifyPassword(userCredentials.getPin(), user.getPin());
  }

  private String constructJWT(ZonedDateTime now, User userCredentials) {
    User user = userRepository.findByEmployeeId(userCredentials.getEmployeeId());

    return Jwts.builder()
        .setClaims(Jwts.claims()
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(now.plusHours(1).toInstant())))
        .claim("identity", user.getEmployeeId())
        .claim("name", user.getName())
        .signWith(SignatureAlgorithm.HS512, KEY)
        .compact();
  }
}
