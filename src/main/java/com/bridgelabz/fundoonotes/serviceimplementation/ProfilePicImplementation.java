package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.exceptions.FileFormatException;
import com.bridgelabz.fundoonotes.exceptions.NoProfileFoundException;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ProfileService;
import com.bridgelabz.fundoonotes.utility.BucketManager;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class ProfilePicImplementation implements ProfileService {

	@Autowired
	BucketManager bucketManager;
	@Autowired
	Utility utility;
	@Autowired
	UserRepository repository;

	public void UploadPicturePic(MultipartFile file, String jwt) throws IOException, FileFormatException {
		UserInfo user = utility.getUser(jwt);
		if (utility.checkImage(file.getOriginalFilename())) {
			repository.insertProfile(file.getOriginalFilename(), user.getId());
			bucketManager.Uploader(file, jwt);
		} else
			throw new FileFormatException("Format of Image is not supported");

	}

	@Override
	public void EditProfilePic(MultipartFile file, String jwt) throws IOException, FileFormatException {
		if (utility.checkImage(file.getOriginalFilename())) {
			UserInfo user = utility.getUser(jwt);
			String filetodelete = repository.getProfile(user.getId());
			repository.updateProfile(file.getOriginalFilename(), user.getId());
			bucketManager.UpdatePic(filetodelete, file, jwt);
		} else
			throw new FileFormatException("Format of Image is not supported");
	}

	public void deleteProfilePic(String jwt) throws NoProfileFoundException {
		UserInfo user = utility.getUser(jwt);
		if (repository.getProfile(user.getId()) != null) {
			bucketManager.Trasher(repository.getProfile(user.getId()));
			repository.deleteProfile(user.getId());
		}
		else
		{
			throw new NoProfileFoundException("you dont have a profile picture");
		}
	}

	public S3Object retrieveProfile(String jwt) {
		UserInfo user = utility.getUser(jwt);
		String filename = repository.getProfile(user.getId());
		return bucketManager.myProfilePic(filename);

	}

}
