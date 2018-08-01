package com.epa.epa.authentication;

import static com.epa.epa.authentication.AuthenticationConstants.KEY;

import com.epa.epa.encryption.PasswordEncryption;
import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncryption passwordEncryption;
  private final TokenRepository tokenRepository;


  public boolean isValidLogin(@RequestBody User userCredentials) {
    User user = userRepository.findByEmployeeId(userCredentials.getEmployeeId());

    return userCredentials.getEmployeeId().equals(user.getEmployeeId()) &&
        passwordEncryption.verifyPassword(userCredentials.getPin(), user.getPin());
  }

  public Token constructJWT(ZonedDateTime now, User userCredentials) {
    User user = userRepository.findByEmployeeId(userCredentials.getEmployeeId());

    Date expiration = Date.from(now.plusHours(1).toInstant());

    Token token = Token.builder()
        .employeeId(user.getEmployeeId())
        .token(Jwts.builder()
            .setClaims(Jwts.claims()
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(expiration))
            .claim("identity", user.getEmployeeId())
            .claim("name", user.getName())
            .signWith(SignatureAlgorithm.HS512, KEY)
            .compact())
        .expiryDate(expiration)
        .build();

    tokenRepository.save(token);

    return token;
  }
}
