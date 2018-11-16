package com.mongo.mongo.resource;

import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserResource {

    @Autowired
    private UserRepository lUserRepository;

    @GetMapping("/all")
    public List<UsersDao> getAll() {
        return lUserRepository.findAll();
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UsersDao pUser) {
        System.out.print("dataRecieved==" + pUser);
        lUserRepository.save(pUser);
        //return "Successfully added User : ";
        return "Successfully added User : " + pUser.getId();
    }
}
