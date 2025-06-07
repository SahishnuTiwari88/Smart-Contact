package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;
    // use this cloudinary to upload image on server

    @Override
    public String uploadImage(MultipartFile contactImage, String fileName) {
        // write code which upload the image on the server(aws, cloudnary) and will
        // return the url
        // we will use cloudnary for image upload for that first we need to do
        // configuration in AppConfig.java i.e. create bean of cloudnary


        // byte array to store actual data of image
        try {
            byte data[] = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data); // to read the content(image) store to data
            // upload data to cloudinary
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", fileName));
                    return this.getUrlFromPublicId(fileName);

        } catch (IOException e) {

            return this.getUrlFromPublicId(null);
        }

    }

    // pass public_id to this method and it will return url
    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url().transformation(new Transformation<>()
                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                .crop(AppConstants.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }

}
