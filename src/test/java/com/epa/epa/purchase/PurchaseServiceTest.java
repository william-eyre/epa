package com.epa.epa.purchase;

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
public class PurchaseServiceTest {


  @Mock
  private UserRepository userRepository;

  private PurchaseService underTest;

  private User user = User.builder()
      .employeeId("12345kjenr4324")
      .firstName("Bruce")
      .lastName("Wayne")
      .email("bat@man.com")
      .mobileNumber("77384756473")
      .bankDetails("1234561234561234")
      .pin("1234")
      .balance(500)
      .build();

  @Before
  public void setUp() throws Exception {
    underTest = new PurchaseService(userRepository);

    when(userRepository.findByEmployeeId(user.getEmployeeId())).thenReturn(user);
  }


  @Test
  public void shouldReturnTrueWhenPurchasingAnItem() {
   PurchaseTotal purchaseTotal = PurchaseTotal.builder()
       .purchaseTotal(100)
       .build();

    Assertions.assertThat(underTest.purchaseItem(user.getEmployeeId(), purchaseTotal)).isTrue();
  }


  @Test
  public void shouldThrowErrorIfBalanceWouldGoNegativeFromPurchase() {
    PurchaseTotal purchaseTotal = PurchaseTotal.builder()
        .purchaseTotal(600)
        .build();

    Assertions.assertThat(underTest.purchaseItem(user.getEmployeeId(), purchaseTotal)).isFalse();
  }

  @Test
  public void shouldThrowErrorIfPurchaseTotalIs0() {
    PurchaseTotal purchaseTotal = PurchaseTotal.builder()
        .purchaseTotal(0)
        .build();

    Assertions.assertThat(underTest.purchaseItem(user.getEmployeeId(), purchaseTotal)).isFalse();
  }
}