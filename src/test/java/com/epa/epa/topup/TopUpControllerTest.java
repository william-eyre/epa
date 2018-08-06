package com.epa.epa.topup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epa.epa.ComponentTest;
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

  private String token = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MzM1NDc3MjUsImV4cCI6MTUzMzU0ODAyNSwiaWRlbnRpdHkiOiIxMjM0NTY3OCIsImZpcnN0TmFtZSI6IldpbGwiLCJiYWxhbmNlIjowfQ.xLk7o0rYXPUvtjdQ6RHiAU_CQrO7uBv5asNbw0wVnj9gqrgf6d7nnu45E4M0dFjQzzJsqPrceiypEfkZZgphPw";


  @Before
  public void setUp() throws Exception {
    Mockito.when(topUpService.topUpUser("12345678", topUpAmount)).thenReturn(true);
  }

  @Test
  public void shouldTopUpUser() throws Exception {

    mockMvc.perform(patch("/topup/12345678")
        .header("X-AUTHORIZATION", token)
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
        .header("X-AUTHORIZATION", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(topUpAmount)))
        .andDo(print())
        .andExpect(status().isBadRequest());

    Mockito.verify(topUpService).topUpUser("12345678", topUpAmount);

  }
}