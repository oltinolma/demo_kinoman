package uz.oltinolma.producer.files.dao.impl;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.oltinolma.producer.files.dao.FileDao;
import uz.oltinolma.producer.files.dao.FileIO;
import uz.oltinolma.producer.files.dao.RenameU;
import uz.oltinolma.producer.files.exception.FileStorageException;
import uz.oltinolma.producer.files.payload.FileServerConfig;
import uz.oltinolma.producer.files.util.PathInfo;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Repository
public class FileDaoImpl implements FileDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${file.uploadDir}")
    String uploadDir;

    @Autowired
    public FileDaoImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public HashMap<Object, Object> saveFile(MultipartFile file, UUID fileId) {
        Long time = System.currentTimeMillis();
        PathInfo pathInfo = createSubDirectories(time);
        Path path = pathInfo.getPath();
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String file_id = String.valueOf(fileId) +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        FileIO fileIO = new FileIO(file_id, pathInfo.getAbsolutePath());
        fileIO.setOriginalName(fileName);
        fileIO.setHost(getCurrentConfig().getHost() + pathInfo.getRelativePath());
        fileIO.setPath(pathInfo.getRelativePath());
        fileIO.setSize(file.getSize());
        fileIO.setTime(time);
        fileIO.setId(fileId);
        fileIO.setExtension(file.getContentType());
        try {
            Path targetLocation = path.resolve(file_id);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            Resource resource = new UrlResource(path.resolve(file_id).toUri());
            if (isImage(fileIO.getExtension())) {
                resizeImage(resource.getFile());
            }
            return insert(fileIO);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + file_id + ". Please try again!", ex);
        }
    }

    @Override
    @Transactional
    public HashMap<Object, Object> getFileFromId(UUID fileId) {
        String sql = "select * from files f where f.id=:id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id", fileId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, rs -> {
            if (rs.next()) {
                HashMap<Object, Object> io = new HashMap<>();
                io.put("id", rs.getObject("id"));
                io.put("originalName", rs.getString("original_name"));
                io.put("comment", rs.getString("comment"));
                io.put("time", rs.getLong("time"));
                io.put("extension", rs.getString("extension"));
                io.put("size", rs.getLong("size"));
                HashMap<Object, Object> urls = new HashMap<>();
                urls.put("original_absolute_path", rs.getString("original_absolute_path"));
                if (rs.getString("extension").matches("image/\\S+")) {
                    urls.put("standart_absolute_path", rs.getString("original_absolute_path"));
                    urls.put("thumbnail_absolute_path", rs.getString("original_absolute_path"));
                }
                io.put("urls", urls);
                return io;
            } else {
                return null;
            }
        });
    }

    public HashMap<Object, Object> insert(FileIO fileIO) {
        String sqlInsert = "INSERT INTO files (id,original_name, original_absolute_path," +
                "standart_absolute_path," +
                "thumbnail_absolute_path,comment,relative_path, extension, size, time)" +
                " VALUES (:id,:original_name, :original_absolute_path,:standart_absolute_path" +
                ",:thumbnail_absolute_path,:comment,:relative_path,:extension, :size, :time)";
        HashMap<String, Object> map = new HashMap<>();
        map.put("original_name", fileIO.getOriginalName());
        map.put("relative_path", fileIO.getPath());
        map.put("extension", fileIO.getExtension());
        map.put("size", fileIO.getSize());
        map.put("time", fileIO.getTime());
        map.put("id", fileIO.getId());
        map.put("comment", fileIO.getComment());
        map.put("original_absolute_path", fileIO.getHost() + fileIO.getComment());
        map.put("thumbnail_absolute_path", (isImage(fileIO.getExtension())) ? fileIO.getHost() + fileIO.getThumbnailName() : null);
        map.put("standart_absolute_path", (isImage(fileIO.getExtension())) ? fileIO.getHost() + fileIO.getStandardName() : null);
        namedParameterJdbcTemplate.update(sqlInsert, map);
        return fileIO.responseBuilder(fileIO, map);
    }


    @Override
    public FileServerConfig getCurrentConfig() {
        String sql = "select * from file_server_config m where m.id=0";
        return namedParameterJdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                FileServerConfig io = new FileServerConfig();
                io.setHost(rs.getString("host"));
                return io;
            } else {
                return null;
            }
        });
    }

    public PathInfo createSubDirectories(Long time) {
        SimpleDateFormat simpleDateFormat;
        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));//days
        simpleDateFormat = new SimpleDateFormat("MMMM");
        String month = simpleDateFormat.format(date).toLowerCase();//month
        simpleDateFormat = new SimpleDateFormat("YYYY");
        String year = simpleDateFormat.format(date).toLowerCase(); //year
        PathInfo pathInfo = new PathInfo();
        pathInfo.setDay(day);
        pathInfo.setYear(year);
        pathInfo.setMonth(month);
        pathInfo.setAbsolutePath(uploadDir);
        pathInfo.setRelativePath("/" + year + "/" + month + "/" + day + "/");
        pathInfo.setPath(Paths.get(uploadDir + "/" + year + "/" + month + "/" + day + "/")
                .toAbsolutePath().normalize());
        return pathInfo;
    }

    public void resizeImage(File file) throws IOException {
        Thumbnails.of(file)
                .size(800, 800)
                .outputQuality(0.9)
                .toFiles(RenameU.PREFIX_800x800);
        Thumbnails.of(file)
                .crop(Positions.CENTER)
                .size(100, 100)
                .keepAspectRatio(false)
                .outputQuality(0.3)
                .toFiles(RenameU.PREFIX_DOT_THUMBNAIL);
    }


    public boolean isImage(String extension) {
        return extension.matches("image/\\S+");
    }

}
