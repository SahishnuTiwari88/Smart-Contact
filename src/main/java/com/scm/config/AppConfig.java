package com.scm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Configuration
public class AppConfig {

    @Value("${spring.cloudinary.cloud_name}")
    private String cloudinaryName;
     @Value("${spring.cloudinary.api_key}")
    private String cloudinaryApiKey;
     @Value("${spring.cloudinary.api_secret}")
    private String cloudinaryApiSecret;

    //configure cloudnary bean
   @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name",cloudinaryName,
                "api_key",cloudinaryApiKey,
                "api_secret",cloudinaryApiSecret


                
            )
        );
    }
}
