package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "Username is required")
    @Size(min = 5, message = "Min 5 character required")
    private String name;

    @Email(message = "Invalid Email Address")
    private String email;

    @NotBlank(message = "Password Is Required")
    @Size(min = 6, message = "Min 6 Character Needed")
    private String password;

    @NotBlank(message = "About required")
    private String about;
    
    @NotBlank(message = "Contact Required")
    @Size(min = 10, max = 12, message = "Invalid Phone Number")
    private String phoneNumber;
}
