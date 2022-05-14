package com.dan.cosmos.dto;

import com.dan.cosmos.model.AppUserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

  private Integer id;
  private String username;
  private String email;
  List<AppUserRole> appUserRoles;

}
