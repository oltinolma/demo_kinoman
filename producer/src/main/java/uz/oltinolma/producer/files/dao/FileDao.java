package uz.oltinolma.producer.files.dao;

import uz.oltinolma.producer.files.payload.FileServerConfig;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.UUID;

public interface FileDao {


    HashMap<Object, Object> saveFile(MultipartFile file, UUID id, UUID productId);

    HashMap<Object, Object> saveFileForProduct(MultipartFile file, UUID id, UUID productId);

    HashMap<Object, Object> getFileFromId(UUID fileId);

    HashMap<Object, Object> getUsageFilesFromProductId(UUID productId);

    FileServerConfig getCurrentConfig();

}
