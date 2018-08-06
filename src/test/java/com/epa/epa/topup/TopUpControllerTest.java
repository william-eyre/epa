package com.epa.epa.topup;

import static com.epa.epa.authentication.AuthenticationConstants.KEY;
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


public class TopUpControllerTest extends ComponentTest {

  @MockBean
  private TopUpService topUpService;

  private TopUpAmount topUpAmount = TopUpAmount.builder()
      .topUpAmount(100)
      .build();

  private String jwt;


  @Before
  public void setUp() throws Exception {
    Mockito.when(topUpService.topUpUser("12345678", topUpAmount)).thenReturn(true);

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
  public void shouldTopUpUser() throws Exception {

    mockMvc.perform(patch("/topup/12345678")
        .header("X-AUTHORIZATION", jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(topUpAmount)))
        .andDo(print())
        .andExpect(status().isOk());

    Mockito.verify(topUpService).topUpUser("12345678", topUpAmount);
  }


  @Test
  public void shouldThrowBadRequestWhenTopUpAmountIsEither0OrNegative() throws Exception {

    TopUpAmount topUpAmount = TopUpAmount.builder()
        .topUpAmount(0)
        .build();

    mockMvc.perform(patch("/topup/12345678")
        .header("X-AUTHORIZATION", jwt)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(topUpAmount)))
        .andDo(print())
        .andExpect(status().isBadRequest());

    Mockito.verify(topUpService).topUpUser("12345678", topUpAmount);

  }
}