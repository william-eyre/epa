package com.epa.epa.purchase;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epa.epa.ComponentTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class PurchaseControllerTest extends ComponentTest {

  @MockBean
  private PurchaseService purchaseService;

  @Test
  public void shouldDeductMoneyFromUser() throws Exception {

    PurchaseTotal purchaseTotal = PurchaseTotal.builder()
        .purchaseTotal(100)
        .build();

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MzM1NDM4MzMsImV4cCI6MTUzMzU0NDEzMywiaWRlbnRpdHkiOiIxMjM0NTY3OSIsImZpcnN0TmFtZSI6IldpbGwiLCJiYWxhbmNlIjowfQ.w9hDoJS7zJ2YCc3IFJ0ZoXp0rMFxQiI7nWF_MZUE8ObJhNwVANVd-uu4WFjqMH9I0v0ZB0YuIaUOImUwDUiX1w";

    mockMvc.perform(patch("/purchase/12345678")
        .header("X-AUTHORIZATION", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(purchaseTotal)))
        .andDo(print())
        .andExpect(status().isOk());

    verify(purchaseService).purchaseItem("12345678", purchaseTotal);

  }

}