package uz.oltinolma.producer.files.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.oltinolma.producer.files.dao.FileDao;

import java.util.UUID;

@RestController
public class FileController {

    @Autowired
    FileDao fileDao;

    @PostMapping(value = "/uploadFile")
    public Object uploadFile(@RequestParam("file") MultipartFile file
    ) {
        return fileDao.saveFile(file, UUID.randomUUID());
    }

    @GetMapping("/getFile/{id}")
    public Object info(@PathVariable UUID id) {
        return fileDao.getFileFromId(id);
    }
}
