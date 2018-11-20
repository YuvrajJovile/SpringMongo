package com.mongo.mongo.resource;

import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.models.ResponseModel;
import com.mongo.mongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mongo.mongo.utils.Constants.ResponseCodes.FAILURE;
import static com.mongo.mongo.utils.Constants.ResponseCodes.SUCCESS;

@RestController
@RequestMapping("/rest/users")
public class UserResource {

    @Autowired
    private UserRepository mUserRepository;

    @GetMapping("/all")
    public ResponseModel getAll() {
        ResponseModel lResponseModel = new ResponseModel();
        lResponseModel.setStatusCode(SUCCESS);
        lResponseModel.setStatusMessage("Success");
        lResponseModel.setData(mUserRepository.findAll());
        return lResponseModel;
    }

    @GetMapping("/{id}")
    public ResponseModel getUser(@PathVariable("id") Integer pId) {
        ResponseModel lResponseModel = new ResponseModel();
        UsersDao lUserDao = mUserRepository.findUserById(pId);
        if (lUserDao != null) {
            lResponseModel.setStatusCode(SUCCESS);
            lResponseModel.setStatusMessage("Success");
            lResponseModel.setData(lUserDao);
            return lResponseModel;
        }
        lResponseModel.setStatusCode(FAILURE);
        lResponseModel.setStatusMessage("Entry not found!");
        return lResponseModel;
    }

    @PutMapping("/update")
    public ResponseModel updateData(@RequestBody UsersDao lUsersDao) {
        mUserRepository.save(lUsersDao);
        return new ResponseModel(SUCCESS, "Successfully Updated User!", lUsersDao);
    }

    @PostMapping("/addUser")
    public ResponseModel addUser(@RequestBody UsersDao pUser) {
        System.out.print("dataRecieved==" + pUser);
        mUserRepository.save(pUser);
        return new ResponseModel(SUCCESS, "Successfully added User id: " + pUser.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteData(@PathVariable("id") Integer pId) {
        UsersDao lUsersDao = mUserRepository.findUserById(pId);
        if (lUsersDao != null) {
            mUserRepository.delete(lUsersDao);
            return new ResponseModel(SUCCESS, "Successfully deleted User id: " + pId);
        }
        return new ResponseModel(FAILURE, "Entry not found");
    }
}
