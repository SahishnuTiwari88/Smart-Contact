package com.scm.entities;

import java.util.List;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String description;
    public boolean favorite=false;
    //social link added later
    private String website;
    private String linkedeIn;
    
    //we use this b/c if in future if we want to do any transformation, it can be done b/c hardcoded url in DB
    private String cloudinaryImagePublicId;

    //social link
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SocialLink> socialLink;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
