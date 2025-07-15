package com.maxkemzi.mypianolist.storage;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {
	private final Path root = Paths.get("uploads");

	@PostConstruct
	public void init() throws IOException {
		Files.createDirectories(root);
	}

	public String saveAvatar(MultipartFile file) {
		try {
			String extension = getFileExtension(file.getOriginalFilename());
			String hash = computeSHA256Hash(file.getInputStream());

			String filename = hash + "." + extension;

			storeAvatar(filename, file);

			return filename;
		} catch (IOException e) {
			throw new StorageException("Failed to process file.", e);
		}
	}

	private String computeSHA256Hash(InputStream input) throws IOException {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] buffer = new byte[8192];
			int read;
			while ((read = input.read(buffer)) != -1) {
				digest.update(buffer, 0, read);
			}
			byte[] hashBytes = digest.digest();
			return new BigInteger(1, hashBytes).toString(16);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 not supported", e);
		}
	}

	private void storeAvatar(String filename, MultipartFile file) throws StorageException {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			Path destination = root.resolve(Paths.get("avatars", filename)).normalize().toAbsolutePath();
			if (Files.exists(destination)) {
				return;
			}

			Files.createDirectories(destination.getParent());

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	private String getFileExtension(String filename) {
		if (filename != null) {
			int dotIndex = filename.lastIndexOf(".");
			if (dotIndex >= 0) {
				return filename.substring(dotIndex + 1);
			}
		}
		return "";
	}
}
