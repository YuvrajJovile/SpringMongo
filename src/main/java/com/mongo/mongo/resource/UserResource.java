package com.mongo.mongo.resource;

import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.models.ResponseModel;
import com.mongo.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mongo.mongo.utils.Constants.ResponseCodes.SUCCESS;

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
    public ResponseModel addUser(@RequestBody UsersDao pUser) {
        System.out.print("dataRecieved==" + pUser);
        lUserRepository.save(pUser);
        return new ResponseModel(SUCCESS, "Successfully added User id: " + pUser.getId());
    }
}
