package com.ibm.telco.som;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Document(collection = "somorders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SomOrder
{
    public String key;
    public String order;


    @Override
    public String toString()
    {
        return String.format(
                "SomOrder[key='%s', order='%s']",
                key, order);
    }
}
