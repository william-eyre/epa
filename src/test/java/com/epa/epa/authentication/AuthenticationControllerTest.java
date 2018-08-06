package com.epa.epa.authentication;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epa.epa.ComponentTest;
import com.epa.epa.user.User;
import java.time.ZonedDateTime;
import org.junit.Test;
import org.springframework.http.MediaType;

public class AuthenticationControllerTest extends ComponentTest {

  @Test
  public void shouldReturnJwtIfUserGivesCorrectCredentials() throws Exception {
    User userCredentials = User.builder()
        .employeeId("12345678")
        .pin("1234")
        .build();

    when(authenticationService.isValidLogin(userCredentials)).thenReturn(true);
    when(authenticationService.constructJWT(ZonedDateTime.now(), userCredentials)).thenReturn(jwt);

    mockMvc.perform(post("/authentication")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(userCredentials)))
        .andDo(print())
        .andExpect(status().isOk());
  }
}