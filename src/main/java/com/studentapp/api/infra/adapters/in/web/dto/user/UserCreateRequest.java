package com.studentapp.api.infra.adapters.in.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    @NotBlank(message = "o nome é obrigatório.")
    @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres.")
    private String name;

    @NotBlank(message = "o email é obrigatório")
    @Email(message = "o formato do email é inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min= 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;
}
