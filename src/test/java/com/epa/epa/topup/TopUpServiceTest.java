package com.epa.epa.topup;

import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TopUpServiceTest {

  @Mock
  private UserRepository userRepository;

  private TopUpService underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new TopUpService(userRepository);
  }

  @Test
  public void shouldTopUpUserBalance_givenEmployeeId() {
    User user = User.builder()
        .employeeId("12345kjenr4324")
        .firstName("Bruce")
        .lastName("Wayne")
        .email("bat@man.com")
        .mobileNumber("77384756473")
        .bankDetails("1234561234561234")
        .pin("1234")
        .balance(1000)
        .build();

    userRepository.save(user);

    TopUpAmount topUpAmount = TopUpAmount.builder()
        .topUpAmount(500)
        .build();

    underTest.topUpUser(user.getEmployeeId(), topUpAmount);

    User employee = userRepository.findByEmployeeId("12345kjenr4324");

  }
}