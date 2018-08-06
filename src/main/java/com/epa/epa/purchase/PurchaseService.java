package com.epa.epa.purchase;

import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseService {

  private final UserRepository userRepository;

  public boolean purchaseItem(String employeeId, PurchaseTotal purchaseTotal)  {

    User user = userRepository.findByEmployeeId(employeeId);

    int newBalance = user.getBalance() - purchaseTotal.getPurchaseTotal();

    if (newBalance < 0) {
      return false;
    } else {
      User employee = User.builder()
          .employeeId(employeeId)
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .email(user.getEmail())
          .mobileNumber(user.getMobileNumber())
          .bankDetails(user.getBankDetails())
          .balance(newBalance)
          .pin(user.getPin())
          .build();

      userRepository.save(employee);
      return true;
    }
  }


}
