package uz.oltinolma.consumer.mvc.dao;


import java.util.UUID;

public interface MovieDao {

    void insert(String json);
    Object info(UUID id);

}
