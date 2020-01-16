package com.bridgelabz.fundoonotes.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.exceptions.FileFormatException;
import com.bridgelabz.fundoonotes.exceptions.NoProfileFoundException;

public interface ProfileService {
	
	public void UploadPicturePic(MultipartFile file,String jwt) throws IOException, FileFormatException;
	public void EditProfilePic(MultipartFile file,String jwt) throws IOException, FileFormatException;
	public void deleteProfilePic(String jwt) throws NoProfileFoundException;
	public S3Object retrieveProfile(String jwt);

}
