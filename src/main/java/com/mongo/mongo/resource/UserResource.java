package com.mongo.mongo.resource;

import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.models.ResponseModel;
import com.mongo.mongo.models.SuccessResponseModel;
import com.mongo.mongo.repository.UserRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.mongo.mongo.utils.IConstants.responseCodes.FAILURE;
import static com.mongo.mongo.utils.IConstants.responseCodes.SUCCESS;

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
    public SuccessResponseModel addUser(@RequestBody UsersDao pUser) {
        System.out.print("dataRecieved==" + pUser);
        mUserRepository.save(pUser);
        return new SuccessResponseModel(SUCCESS, "Successfully added User id: " + pUser.getId());
    }

    @DeleteMapping("/{id}")
    public SuccessResponseModel deleteData(@PathVariable("id") Integer pId) {
        UsersDao lUsersDao = mUserRepository.findUserById(pId);
        if (lUsersDao != null) {
            mUserRepository.delete(lUsersDao);
            return new SuccessResponseModel(SUCCESS, "Successfully deleted User id: " + pId);
        }
        return new SuccessResponseModel(FAILURE, "Entry not found");
    }


    //post request for uploading file
    @PostMapping("/upload")
    public SuccessResponseModel uploadFile(@RequestParam("file") MultipartFile pFile) {
        System.out.print("dataRecieved==" + pFile.getOriginalFilename());

        try {

            File lTempFile = new File("C:/data/" + pFile.getOriginalFilename());
            pFile.transferTo(lTempFile);
            try {
                Workbook lWorkbook = WorkbookFactory.create(lTempFile);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new SuccessResponseModel(SUCCESS, "Successfully uploaded!");
    }
}
