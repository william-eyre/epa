package com.epa.epa.purchase;

import static com.epa.epa.authentication.AuthenticationConstants.KEY;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epa.epa.ComponentTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class PurchaseControllerTest extends ComponentTest {

  @MockBean
  private PurchaseService purchaseService;

  private PurchaseTotal purchaseTotal = PurchaseTotal.builder()
      .purchaseTotal(100)
      .build();

  private String jwt;

  @Before
  public void setUp() throws Exception {
    Mockito.when(purchaseService.purchaseItem("12345678", purchaseTotal)).thenReturn(true);

    ZonedDateTime now = ZonedDateTime.now();

    jwt = Jwts.builder()
        .setClaims(Jwts.claims()
            .setIssuedAt(Date.from(now.toInstant()))
            .setExpiration(Date.from(now.plusMinutes(5).toInstant())))
        .claim("identity", "123435678")
        .claim("firstName", "Bruce")
        .claim("balance", "1000")
        .signWith(SignatureAlgorithm.HS512, KEY)
        .compact();
  }

  @Test
  public void shouldDeductMoneyFromUser() throws Exception {

    mockMvc.perform(patch("/purchase/12345678")
        .header("X-AUTHORIZATION", jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(purchaseTotal)))
        .andDo(print())
        .andExpect(status().isOk());

    verify(purchaseService).purchaseItem("12345678", purchaseTotal);

  }


  @Test
  public void shouldThrowErrorIfUserBalanceWouldGoBelow0() throws Exception {
    PurchaseTotal purchaseTotal = PurchaseTotal.builder()
        .purchaseTotal(0)
        .build();

    mockMvc.perform(patch("/purchase/12345678")
        .header("X-AUTHORIZATION", jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(purchaseTotal)))
        .andDo(print())
        .andExpect(status().isBadRequest());

    verify(purchaseService).purchaseItem("12345678", purchaseTotal);

  }
}