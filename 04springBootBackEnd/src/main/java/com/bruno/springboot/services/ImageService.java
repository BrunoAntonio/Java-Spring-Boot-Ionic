package com.bruno.springboot.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bruno.springboot.services.exceptions.FileException;

@Service
public class ImageService {

	// obter uma imagem a partir do arquivo
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename()); // obter extensão do arquivo
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}

		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());// ler a imagem do arquivo
			if ("png".equals(ext)) {
				img = pngToJpg(img); // se a imagem for .png converter para jpg
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	
	//obter um inputstream apartir de um bfferedimage por causa do metodo que faz o upload para o s3
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	//recortar imagem
	public BufferedImage cropSquare(BufferedImage sourceImg) { //descobrir qual o tamanho menor min: a largura ou altura
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
		return Scalr.crop(//cortar a imagem
			sourceImg, 
			(sourceImg.getWidth()/2) - (min/2), //coordenadas de origem - meio da largura menos a metade do minimo
			(sourceImg.getHeight()/2) - (min/2), 
			min, //cortar o min na largura e na altura
			min);		
	}
	
	//redimensionar imagem
	public BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}

}
