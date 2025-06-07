package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile contactImage, String fileName);

    // we can make it directly in serviceimpl also but making in interface is good
    // practice
    String getUrlFromPublicId(String publicId);

}
