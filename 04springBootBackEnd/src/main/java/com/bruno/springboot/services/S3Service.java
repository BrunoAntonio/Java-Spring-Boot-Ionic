package com.bruno.springboot.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bruno.springboot.services.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	//Se o endpoint não for publico: no headers por o token na key authorization
	// enviar imagem para o aws s3
	//http://localhost:8080/clientes/picture
	//escolher POST, apagar os headers(se o endpoint for publico), no body escolher format-data ->file e escolher a imagem a enviar
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: "+e.getMessage());
		}

	}

	public URI uploadFile(InputStream is, String fileName, String ContentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			LOG.info("Iniciando upload");
			s3Client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload terminado");
			return s3Client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter a URL para URI");
		}
	}

}
