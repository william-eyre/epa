package com.epa.epa.user;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epa.epa.ComponentTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class UserControllerTest extends ComponentTest {

  @MockBean
  private UserService userService;

  @Before
  public void setUp() throws Exception {
    String employeeId = "12345kjenr4324";
    when(userService.verifyUser(employeeId))
        .thenReturn(true);
  }

  @Test
  public void shouldCreateNewUser() throws Exception {
    User user = User.builder()
        .employeeId("213123123")
        .firstName("Wally")
        .lastName("West")
        .email("Flash@Speed.com")
        .mobileNumber("12345678901")
        .bankDetails("1234567890123456")
        .pin("1234")
        .build();

    when(userService.createUser(user)).thenReturn(true);

    mockMvc.perform(post("/user")
        .contentType(APPLICATION_JSON)
        .content(json(user)))
        .andDo(print())
        .andExpect(status().isCreated());

    verify(userService).createUser(user);

  }

  @Test
  public void shouldVerifyUserExists_andReturn200() throws Exception {
    mockMvc.perform(get("/user/12345kjenr4324"))
        .andDo(print())
        .andExpect(status().isOk());

    verify(userService).verifyUser("12345kjenr4324");
  }


  @Test
  public void shouldVerifyUserDoesNotExist_andReturn404() throws Exception {
    mockMvc.perform(get("/user/2131231dfd"))
        .andDo(print())
        .andExpect(status().isNotFound());

    verify(userService).verifyUser("2131231dfd");
  }
}