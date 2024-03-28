package com.nithin.blog.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nithin.blog.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // File name
        String random = UUID.randomUUID().toString();
        String name = file.getOriginalFilename();
        // Full path
        String extension = name.substring(name.lastIndexOf("."));
        String newName = random + extension;
        // String fileName1 = random.concat(name.substring(name.lastIndexOf(".")));

        String filePath = path + File.separator + newName;

        // create folderif not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return newName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

}
