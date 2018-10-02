package uz.oltinolma.producer.mvc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
public class DemoController {

//    @PreAuthorize("@SecurityPermission.hasPermission('role.info')")
    @GetMapping(value = "/hello")
    public HashMap<String, Object> demo() {
        HashMap<String, Object> x = new HashMap<>();
        x.put("hello from", "earth");
        x.put("to", "galaxy");
        return x;
    }


    @PostMapping(value = "/hello")
    public void demoPost(@RequestParam("file") MultipartFile file, Movie movie) {
        System.out.println(file.getOriginalFilename());
        System.out.println(movie.toString());
    }
}
