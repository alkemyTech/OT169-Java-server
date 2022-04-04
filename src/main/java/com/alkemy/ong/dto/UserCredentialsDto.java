package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCredentialsDto {

    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;


}
