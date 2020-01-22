package com.bridgelabz.fundoonotes.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.exceptions.FileFormatException;
import com.bridgelabz.fundoonotes.exceptions.NoProfileFoundException;

public interface ProfileService {
	
	void UploadPicturePic(MultipartFile file,String jwt) throws IOException, FileFormatException;
	void EditProfilePic(MultipartFile file,String jwt) throws IOException, FileFormatException;
	void deleteProfilePic(String jwt) throws NoProfileFoundException;
	S3Object retrieveProfile(String jwt);

}
