package com.epa.epa.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

  @Id
  private String employeeId;
  @NotBlank
  private String name;
  @NotBlank
  private String email;
  @NotBlank
  private String mobileNumber;
  @NotBlank
  private String bankDetails;
  @NotBlank
  private String pin;
  public int balance;
}
