package com.fixipy.service;

import com.fixipy.entity.Application;
import com.fixipy.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;

    public Application submitApplication(String name, String email, String phone, 
                                       String role, String reason, MultipartFile resume) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = resume.getOriginalFilename();
        String fileExtension = originalFilename != null ? 
            originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String newFilename = "resume-" + System.currentTimeMillis() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(resume.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Create and save application
        Application application = new Application();
        application.setName(name);
        application.setEmail(email);
        application.setPhone(phone);
        application.setRole(role);
        application.setReason(reason);
        application.setResumePath(filePath.toString());

        return applicationRepository.save(application);
    }
}
