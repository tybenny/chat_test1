package org.example.chat_test1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username khong duoc de trong")
    @Size(min = 5, max = 20, message = "Username tu 5-20 ki tu")
    private String username;

    @NotBlank(message = "Email khong duoc de trong")
    @Email(message = "Email khong hop le")
    private String email;

    @NotBlank(message = "Password khong duoc de trong")
    @Size(min = 5, message = "Password it nhat la 5 ki tu")
    private String password;
}
