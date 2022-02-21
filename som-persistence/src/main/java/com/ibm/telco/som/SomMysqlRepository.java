package com.ibm.telco.som;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

@EntityScan("com.ibm.telco.som")
public interface SomMysqlRepository extends CrudRepository<SomPayloadEntity, String> {
    SomPayloadEntity findByPayloadKey(String key);
    //List<SomPayloadEntity> findByOrderContaining(String searchWord);

    //void deleteByKey(String key);
}
