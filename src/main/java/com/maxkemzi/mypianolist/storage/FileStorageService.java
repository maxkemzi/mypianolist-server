package com.maxkemzi.mypianolist.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

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
		String extension = getFileExtension(file.getOriginalFilename());
		String filename = UUID.randomUUID() + "." + extension;

		storeAvatar(filename, file);

		return filename;
	}

	private void storeAvatar(String filename, MultipartFile file) throws StorageException {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}

			Path destination = root.resolve(Paths.get("avatars", filename)).normalize().toAbsolutePath();
			Files.createDirectories(destination.getParent());

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	private String getFileExtension(String filename) {
		if (filename == null) {
			return null;
		}
		int dotIndex = filename.lastIndexOf(".");
		if (dotIndex >= 0) {
			return filename.substring(dotIndex + 1);
		}
		return "";
	}
}
