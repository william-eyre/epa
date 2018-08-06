package com.epa.epa.user;

import static org.mockito.Mockito.mock;

import com.epa.epa.encryption.PasswordEncryption;
import org.junit.Ignore;
import org.junit.Test;

public class UserServiceTest {

  private final UserRepository userRepository = mock(UserRepository.class);

  private final PasswordEncryption passwordEncryption = mock(PasswordEncryption.class);

  private final UserService underTest = new UserService(userRepository, passwordEncryption);

  @Test
  @Ignore
  public void shouldCreateUser() {


  }
}