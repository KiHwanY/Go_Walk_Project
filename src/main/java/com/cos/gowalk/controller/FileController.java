package com.cos.gowalk.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * @Author   : 윤기환
 * @Class    : FileController.java
 * @Desc     : 이미지 파일 업로드 컨트롤러
 * */
@RestController
@RequestMapping("/user")
public class FileController {

    @Value("${upload.path}") // properties 파일에서 설정된 경로를 가져옵니다
    private String uploadPath;

    /**
     * @part        : 이미지 경로 설정 및 파일 업로드
     * @Author      : 윤기환
     * @Type        : String
     * */
    @PostMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
                                            HttpServletRequest request) {
        JsonObject jsonObject = new JsonObject();

        String originalFileName = multipartFile.getOriginalFilename(); // 오리지널 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자
        String savedFileName = UUID.randomUUID().toString() + extension; // 저장될 파일명
        String savedFilePath = uploadPath + File.separator + savedFileName; // 저장될 파일 경로

        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(savedFilePath), StandardCopyOption.REPLACE_EXISTING);
            String imageUrl = request.getContextPath() + "/summernoteImage/" + savedFileName; // 이미지 URL 생성
            jsonObject.addProperty("url", imageUrl);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e) {
            jsonObject.addProperty("responseCode", "error");
            jsonObject.addProperty("message", "파일 업로드 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}

