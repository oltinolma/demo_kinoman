package uz.oltinolma.producer.files.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.oltinolma.producer.files.dao.FileDao;

import java.util.UUID;

@RestController
public class FileController {

    @Autowired
    FileDao fileDao;

    @PreAuthorize("@SecurityPermission.hasPermission('file.upload')")
    @PostMapping(value = "/uploadFile")
    public Object uploadFile(@RequestParam("file") MultipartFile file
    ) {
        return fileDao.saveFile(file, UUID.randomUUID());
    }

    @PreAuthorize("@SecurityPermission.hasPermission('file.info')")
    @GetMapping("/getFile/{id}")
    public Object info(@PathVariable UUID id) {
        return fileDao.getFileFromId(id);
    }
}
