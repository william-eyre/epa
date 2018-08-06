package com.epa.epa.encryption;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class PasswordEncryptionTest {

  private PasswordEncryption underTest;
  private String pin = "password123";

  @Before
  public void setUp() throws Exception {
    underTest = new PasswordEncryption();
  }


  @Test
  public void shouldEncryptPassword_andPasswordShouldMatchAfterEncryption() {

    String encryptedPin = underTest.generatePassword(pin);

    boolean verifyPassword = underTest.verifyPassword(pin, encryptedPin);

    assertThat(verifyPassword).isTrue();
  }


  @Test
  public void shouldReturnFalseWhenPasswordsDoNotMatch() {

    assertThat(underTest.verifyPassword(pin, "$2a$10$2tlEPCEMcGyrwoD2co0kHu5k3XPsqcJRw/FjBLOfswjhV/4esf/La")).isFalse();

  }
}