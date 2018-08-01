package com.epa.epa.encryption;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption {

  public String generatePassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public boolean verifyPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }

}
