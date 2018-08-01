package com.epa.epa.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
  private String name;
  private String email;
  private String mobileNumber;
  private String bankDetails;
  private String pin;
  public int balance;
}
