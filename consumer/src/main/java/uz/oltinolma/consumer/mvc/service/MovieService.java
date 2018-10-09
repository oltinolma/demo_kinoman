package uz.oltinolma.consumer.mvc.service;

import java.util.UUID;

public interface MovieService {

    String movieInfoById(UUID id);

    void insert(String json);
}
