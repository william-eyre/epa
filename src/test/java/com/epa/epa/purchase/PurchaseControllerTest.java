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

    String token = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1MzMxMzIyMjQsImV4cCI6MTUzMzEzNTgyNCwiaWRlbnRpdHkiOiIxMjM0NTYiLCJuYW1lIjoiV2lsbCBFeXJlIn0.0A3M79Q3o6zLrPGuWlhhWisUAtuobd8QQ2GldZm0j8hULHMLRcmsgVFxFKfwDBqXr-TeNAQyCUXH36wz8wRAxg";

    mockMvc.perform(patch("/purchase/12345678")
        .header("X-AUTHORIZATION", token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(purchaseTotal)))
        .andDo(print())
        .andExpect(status().isOk());

    verify(purchaseService).purchaseItem("12345678", purchaseTotal);

  }

}