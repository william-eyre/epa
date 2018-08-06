package com.epa.epa.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

  @Id
  @Length(min = 8, max = 8)
  private String employeeId;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  private String email;
  @NotBlank
  @Length(min = 11, max = 11)
  private String mobileNumber;
  @NotBlank
  @Length(min = 16, max = 16)
  private String bankDetails;
  @NotBlank
  private String pin;
  public int balance;
}
