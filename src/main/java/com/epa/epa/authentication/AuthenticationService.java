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


  public boolean isValidLogin(@RequestBody User userCredentials) {
    User user = userRepository.findByEmployeeId(userCredentials.getEmployeeId());

    return userCredentials.getEmployeeId().equals(user.getEmployeeId()) &&
        passwordEncryption.verifyPassword(userCredentials.getPin(), user.getPin());
  }

  public String constructJWT(ZonedDateTime now, User userCredentials) {
    User user = userRepository.findByEmployeeId(userCredentials.getEmployeeId());

    return Jwts.builder()
        .setClaims(Jwts.claims()
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(now.plusMinutes(5).toInstant())))
        .claim("identity", user.getEmployeeId())
        .claim("name", user.getName())
        .claim("balance", user.getBalance())
        .signWith(SignatureAlgorithm.HS512, KEY)
        .compact();
  }
}
