package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class contactForm {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Contact Required")
    //@Size(min = 10, max = 12, message = "Invalid Phone Number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "Provide Address Detail")
    private String address;

    @NotBlank(message = "Provide Contact Information")
    private String description;

    
    private boolean favorite;
    private String website;
    private String linkedIn;
    private String profileImage;
}
