package com.bit.studypage.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bit.studypage.common.StorageException;
import com.bit.studypage.service.FileQnaStorageService;

@Service
public class FileQnaStorageServiceImpl implements FileQnaStorageService {

	//properties에 있는 경로 가져오기 
	private final Path root;

    public FileQnaStorageServiceImpl(@Value("${app.file-upload-dir}") String dir) {
        root = Paths.get(dir);
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    
    //파일을 저장하면서 파일 이름을 중복되지 않게 유니크하게 변경하고, 
    //파일의 MIME 타입을 체크하여 이미지 파일만 허용하도록
    @Override
    public String save(MultipartFile file) {
        try {
            Tika tika = new Tika();
            String mimeType = tika.detect(file.getInputStream());
            if (!mimeType.startsWith("image")) {
                throw new RuntimeException("Invalid file type!");
            }

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName));
            return uniqueFileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
    
    //MultipartFile 객체와 기본 디렉토리 경로를 받아 해당 디렉토리에 파일을 저장하고, 
    //저장된 파일의 절대 경로를 문자열로 반환
    public String storeFile(MultipartFile file, String baseDir) throws IOException {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            
            // 파일이 저장될 경로를 생성.
            Path destinationFile = Paths.get(baseDir)
                    .resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            
            // 파일이 서버의 밖에 저장되지 않도록 확인.
            if (!destinationFile.getParent().equals(Paths.get(baseDir).toAbsolutePath())) {
                throw new StorageException("Cannot store file outside current directory.");
            }
            
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            
            return destinationFile.toString();
            
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    ////파일 이름을 기반으로 파일 시스템에서 해당 파일을 로드하고, Resource 타입으로 반환
	@Override
	public Resource loadFileAsResource(String fileName) {
		try {
	        Path filePath = this.root.resolve(fileName).normalize();
	        Resource resource = new UrlResource(filePath.toUri());
	        if(resource.exists()) {
	            return resource;
	        } else {
	            throw new StorageException("File not found " + fileName);
	        }
	    } catch (MalformedURLException ex) {
	        throw new StorageException("File not found " + fileName, ex);
	    }
	}
}

