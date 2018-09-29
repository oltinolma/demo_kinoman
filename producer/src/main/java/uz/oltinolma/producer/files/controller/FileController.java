package uz.oltinolma.producer.files.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.oltinolma.producer.files.dao.FileDao;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileDao fileDao;

    @PostMapping(value = "/uploadFile/forGallery")
    public Object uploadFile(@RequestParam("file") MultipartFile file
    ) {
        return fileDao.saveFile(file, UUID.randomUUID(), null);
    }

    @PostMapping("/uploadMultipleFilesForGallery")
    public Object uploadMultipleFiles(@RequestParam("files") MultipartFile[] files
    ) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/uploadFile/forProduct")
    public Object uploadFileForProduct(@RequestParam("file") MultipartFile file,
                                       @RequestParam("productId") UUID productId) {
        return fileDao.saveFileForProduct(file, UUID.randomUUID(), productId);
    }

    @PostMapping("/uploadMultipleFilesForProduct")
    public Object uploadMultipleFilesForProduct(@RequestParam("files") MultipartFile[] files,
                                                @RequestParam("productId") UUID productId) {
        return Arrays.stream(files)
                .map(file -> uploadFileForProduct(file, productId))
                .collect(Collectors.toList());
    }

    @GetMapping("/getInfo/{id}")
    public Object info(@PathVariable UUID id) {
        return fileDao.getFileFromId(id);
    }

    @GetMapping("/getUsageFilesFromProductId/{id}")
    public Object infoUsageFilesFromProductId(@PathVariable UUID id) {
        return fileDao.getUsageFilesFromProductId(id);
    }
}
