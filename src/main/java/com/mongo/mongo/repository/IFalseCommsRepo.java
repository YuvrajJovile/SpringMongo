package com.mongo.mongo.repository;

import com.mongo.mongo.document.FalseCommsDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IFalseCommsRepo extends MongoRepository<FalseCommsDao, Integer> {
    FalseCommsDao findDataById(Integer lInteger);
}
