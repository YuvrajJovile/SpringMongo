package com.mongo.mongo.repository;

import com.mongo.mongo.document.UsersDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UsersDao, Integer> {
    UsersDao findUserById(Integer lObjectId);
}
