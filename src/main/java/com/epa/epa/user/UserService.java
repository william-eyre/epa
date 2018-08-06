package com.epa.epa.user;

import com.epa.epa.encryption.PasswordEncryption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncryption passwordEncryption;

  boolean createUser(User user) {
    String encryptedPin = passwordEncryption.generatePassword(user.getPin());

    if (verifyUser(user.getEmployeeId())) {
      return false;
    } else {
      userRepository.save(User.builder()
          .employeeId(user.getEmployeeId())
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .email(user.getEmail())
          .mobileNumber(user.getMobileNumber())
          .bankDetails(user.getBankDetails())
          .pin(encryptedPin)
          .balance(0)
          .build());
    }
    return true;
  }

  boolean verifyUser(@RequestBody String employeeId) {
    User user = userRepository.findByEmployeeId(employeeId);
    return user != null;
  }
}
