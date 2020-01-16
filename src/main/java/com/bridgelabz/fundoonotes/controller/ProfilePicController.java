package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.exceptions.FileFormatException;
import com.bridgelabz.fundoonotes.exceptions.NoProfileFoundException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfileService;

@RestController
@RequestMapping("/profilepic")
public class ProfilePicController {
	@Autowired
	ProfileService profileservice;

	@PostMapping("/uploadpic")
	public ResponseEntity<Response> uploadProfile(@RequestPart("file") MultipartFile file,
			@RequestHeader("jwt") String jwt) throws Exception {

		profileservice.UploadPicturePic(file, jwt);
		return ResponseEntity.ok().body(new Response(200, "successfully uploaded", file.getName()));

	}

	@PutMapping("/editprofile")
	public ResponseEntity<Response> EditProfile(@RequestPart("file") MultipartFile file,
			@RequestHeader("jwt") String jwt) throws IOException, FileFormatException {

		profileservice.EditProfilePic(file, jwt);
		return ResponseEntity.ok().body(new Response(200, "successfully updated", file.getName()));

	}

	@DeleteMapping("/deleteprofile")
	public ResponseEntity<Response> DeleteProfile(@RequestHeader("jwt") String jwt) throws NoProfileFoundException {
		profileservice.deleteProfilePic(jwt);
		return ResponseEntity.ok().body(new Response(200, "sccessfully Deleted", null));
	}

	@GetMapping("/getprofile")
	public ResponseEntity<Response> getProfile(@RequestHeader("jwt") String jwt) {

		if (profileservice.retrieveProfile(jwt) != null) {
			return ResponseEntity.ok().body(
					new Response(200, "sccessfully Retrieved", profileservice.retrieveProfile(jwt).getObjectContent()));
		}

		else
			return ResponseEntity.badRequest().body(new Response(400,"image not retrived",null));

	}

}
