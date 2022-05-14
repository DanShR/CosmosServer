package com.dan.cosmos.dto;

import com.dan.cosmos.model.AppUserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserSignUpDTO {
  
  private String username;
  private String email;
  private String password;
  private String passwordConfirm;
  private String firstName;
  private String lastName;
  private Date birthDate;
  List<AppUserRole> appUserRoles;

}
