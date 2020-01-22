package com.bridgelabz.fundoonotes.utility;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Component
public class BucketManager {

	private AmazonS3 s3client;
	
	@Autowired
	Utility utility;

	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}
	
	public void uploader( MultipartFile multipartFile,String jwt) throws IOException
	{
		
		File file = convertMultiPartToFile(multipartFile);
		s3client.putObject(new PutObjectRequest(bucketName,multipartFile.getOriginalFilename(), file));
		
	}
	public void updatePic(String filetodelete, MultipartFile multipartFile,String jwt) throws IOException
	{
		
		File file = convertMultiPartToFile(multipartFile);
		s3client.deleteObject(new DeleteObjectRequest(bucketName,filetodelete));
		s3client.putObject(new PutObjectRequest(bucketName,multipartFile.getOriginalFilename(), file));
		
	}
	
	
	public void trasher(String filename)
	{
		s3client.deleteObject(new DeleteObjectRequest(bucketName,filename));
	}
	
	public S3Object myProfilePic(String file)
	{
		 return s3client.getObject(new GetObjectRequest(bucketName,file));
	}
	
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    try {
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    return convFile;
	}

}
