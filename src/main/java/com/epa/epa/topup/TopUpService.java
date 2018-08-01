package com.epa.epa.topup;

import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopUpService {

  private final UserRepository userRepository;

  public void topUpUser(String employeeId, TopUpAmount topUpAmount) {
    User user = userRepository.findByEmployeeId(employeeId);

    int initialAmount = user.getBalance();
    int amount = topUpAmount.getTopUpAmount();

    User employee = User.builder()
        .employeeId(employeeId)
        .name(user.getName())
        .email(user.getEmail())
        .mobileNumber(user.getMobileNumber())
        .bankDetails(user.getBankDetails())
        .balance(initialAmount + amount)
        .pin(user.getPin())
        .build();

    userRepository.save(employee);
  }

}
