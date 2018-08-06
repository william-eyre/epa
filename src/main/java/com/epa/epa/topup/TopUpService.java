package com.epa.epa.topup;

import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class TopUpService {

  private final UserRepository userRepository;

  boolean topUpUser(String employeeId, TopUpAmount topUpAmount) {
    User user = userRepository.findByEmployeeId(employeeId);

    int initialAmount = user.getBalance();
    int amount = topUpAmount.getTopUpAmount();

    if (amount <= 0) {
      log.info("Top up amount must be above 0");
      return false;
    } else {
      User employee = User.builder()
          .employeeId(employeeId)
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .email(user.getEmail())
          .mobileNumber(user.getMobileNumber())
          .bankDetails(user.getBankDetails())
          .balance(initialAmount + amount)
          .pin(user.getPin())
          .build();

      userRepository.save(employee);
      return true;
    }
  }
}


