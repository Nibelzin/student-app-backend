package com.studentapp.api.infra.adapters.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserUpdateRequest {

    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres.")
    private String name;

    @Email(message = "o formato do email é inválido.")
    private String email;

    private String course;

    private Integer currentSemester;

}
