package com.epa.epa.user;

import com.epa.epa.encryption.PasswordEncryption;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncryption passwordEncryption;

  public String createUser(User user) {
    String encryptedPin = passwordEncryption.generatePassword(user.getPin());

    if (userRepository.findByEmployeeId(user.getEmployeeId()) != null) {
      return "User already exists";
    } else {
      userRepository.save(User.builder()
          .employeeId(user.getEmployeeId())
          .name(user.getName())
          .email(user.getEmail())
          .mobileNumber(user.getMobileNumber())
          .bankDetails(user.getBankDetails())
          .pin(encryptedPin)
          .balance(0)
          .build());
    }

    return "User Created";
  }
}
