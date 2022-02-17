package com.ibm.telco.som;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SomRepository extends MongoRepository<SomOrder, String> {
    SomOrder findByKey(String key);
    List<SomOrder> findByOrderContaining(String searchWord);

    void deleteByKey(String key);
}
