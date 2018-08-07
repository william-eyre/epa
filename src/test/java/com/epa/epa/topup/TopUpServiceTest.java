package com.epa.epa.topup;

import static org.mockito.Mockito.when;

import com.epa.epa.user.User;
import com.epa.epa.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopUpServiceTest {

  @Mock
  private UserRepository userRepository;

  private TopUpService underTest;

  private User user = User.builder()
      .employeeId("12345kjenr4324")
      .firstName("Bruce")
      .lastName("Wayne")
      .email("bat@man.com")
      .mobileNumber("77384756473")
      .bankDetails("1234561234561234")
      .pin("1234")
      .build();


  @Before
  public void setUp() throws Exception {
    underTest = new TopUpService(userRepository);

    when(userRepository.findByEmployeeId(user.getEmployeeId())).thenReturn(user);

  }


  @Test
  public void shouldTopUpUserBalance_givenEmployeeId() {
    TopUpAmount topUpAmount = TopUpAmount.builder()
        .topUpAmount(500)
        .build();

    Assertions.assertThat(underTest.topUpUser(user.getEmployeeId(), topUpAmount)).isTrue();

  }


  @Test
  public void shouldThrowErrorIfBalanceIsNegativeOr0() {
    TopUpAmount topUpAmount = TopUpAmount.builder()
        .topUpAmount(0)
        .build();

    Assertions.assertThat(underTest.topUpUser(user.getEmployeeId(), topUpAmount)).isFalse();

  }
}