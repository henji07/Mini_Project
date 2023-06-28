package com.bit.studypage.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	//파일 저장 디렉토리 초기화
    public void init();
    //MultipartFile 받아서 해당 파일을 저장
    public String save(MultipartFile file);
    //파일 이름을 입력받아, 해당 파일을 불러옴
    public Resource load(String filename);
    //저장된 모든 파일을 삭제
    public void deleteAll();
    //저장된 모든 파일을 불러옴
    public Stream<Path> loadAll();
    //MultipartFile 객체와 기본 디렉토리 경로를 받아 해당 디렉토리에 파일을 저장하고, 
    //저장된 파일의 절대 경로를 문자열로 반환
    public String storeFile(MultipartFile file, String baseDir) throws IOException;
    //파일 이름을 기반으로 파일 시스템에서 해당 파일을 로드하고, Resource 타입으로 반환
    public Resource loadFileAsResource(String fileName);
}
