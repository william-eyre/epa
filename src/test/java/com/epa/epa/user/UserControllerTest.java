package com.epa.epa.user;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epa.epa.ComponentTest;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

public class UserControllerTest extends ComponentTest {

  @MockBean
  private UserService userService;

  @Test
  public void shouldCreateNewUser() throws Exception {
    User user = User.builder()
        .employeeId("12345kjenr4324")
        .name("Bruce Wayne")
        .email("bat@man.com")
        .mobileNumber("77384756473")
        .bankDetails("1234561234561234")
        .pin("1234")
        .balance(1000)
        .build();

    mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json(user)))
        .andDo(print())
        .andExpect(status().isOk());

    verify(userService).createUser(eq(user));
  }
}