package com.distributed.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.*;

@SpringBootApplication
@RestController
public class StorageNode {
    public static void main(String[] args) {
        SpringApplication.run(StorageNode.class, args);
    }

    private final Path root = Paths.get("storage");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(root);
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        return "Uploaded " + file.getOriginalFilename();
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String fileName) throws IOException {
        return Files.readAllBytes(root.resolve(fileName));
    }
}
