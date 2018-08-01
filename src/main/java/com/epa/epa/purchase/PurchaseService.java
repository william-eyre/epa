package com.epa.epa.purchase;

import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseService {

  private final UserRepository userRepository;

  public void purchaseItem(PurchaseTotal purchaseTotal, String employeeId)  {

    User user = userRepository.findByEmployeeId(employeeId);

    int initialAmount = user.getBalance();
    int deduction = purchaseTotal.getPurchaseTotal();


    User employee = User.builder()
        .employeeId(employeeId)
        .name(user.getName())
        .email(user.getEmail())
        .mobileNumber(user.getMobileNumber())
        .bankDetails(user.getBankDetails())
        .balance(initialAmount - deduction)
        .pin(user.getPin())
        .build();

    userRepository.save(employee);
  }


}
