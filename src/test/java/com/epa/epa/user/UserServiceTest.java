package com.epa.epa.user;

import static org.mockito.Mockito.mock;

import com.epa.epa.encryption.PasswordEncryption;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  private final UserRepository userRepository = mock(UserRepository.class);

  private final PasswordEncryption passwordEncryption = mock(PasswordEncryption.class);

  private final UserService underTest = new UserService(userRepository, passwordEncryption);

  private User user;


  @Before
  public void setUp() throws Exception {
    user = User.builder()
        .employeeId("12345kjenr4324")
        .firstName("Bruce")
        .lastName("Wayne")
        .email("bat@man.com")
        .mobileNumber("77384756473")
        .bankDetails("1234561234561234")
        .pin("1234")
        .build();

    userRepository.save(user);
  }

  @Test
  public void shouldCreateUser() {

    Assertions.assertThat(underTest.createUser(user)).isTrue();
  }


  @Test
  public void shouldReturnFalseIfUserAlreadyExists() {

    Assertions.assertThat(underTest.verifyUser(user.getEmployeeId())).isFalse();
  }
  
}