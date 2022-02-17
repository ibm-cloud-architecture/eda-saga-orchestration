package com.ibm.telco.som;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
public class SomRepoApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SomRepoApplication.class, args);
    }

}


