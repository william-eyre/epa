package com.epa.epa.topup;

import com.epa.epa.ComponentTest;
import com.epa.epa.user.User;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;


public class TopUpControllerTest extends ComponentTest {

  @MockBean
  private TopUpService topUpService;

  @Test
  public void shouldTopUpUsersBalance() {
    User user = User.builder()
        .employeeId("3264tewg")
        .name("Clark Kent")
        .email("super@man.com")
        .mobileNumber("18463728192")
        .bankDetails("1234561234561234")
        .pin("2345")
        .balance(500)
        .build();

    TopUpAmount topUpAmount = TopUpAmount.builder()
        .topUpAmount(100)
        .build();


  }
}