package com.mongo.mongo.resource;

import com.mongo.mongo.document.FalseCommsDao;
import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.models.ResponseModel;
import com.mongo.mongo.models.SuccessResponseModel;
import com.mongo.mongo.repository.IFalseCommsRepo;
import com.mongo.mongo.repository.UserRepository;
import com.mongo.mongo.service.FileUploadService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.mongo.mongo.utils.IConstants.responseCodes.FAILURE;
import static com.mongo.mongo.utils.IConstants.responseCodes.SUCCESS;

@RestController
@RequestMapping("/rest/users")
@SessionAttributes("name")
public class UserResource {

    @Autowired
    private UserRepository mUserRepository;

    @Autowired
    private IFalseCommsRepo mFalseCommsRepo;

    @Autowired
    private FileUploadService mFileUploadService;

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


    @PostMapping("/upload")
    public SuccessResponseModel uploadFile(@RequestParam("file") MultipartFile pFile) {
        System.out.print("dataRecieved==" + pFile.getOriginalFilename());
        try {
            if (mFileUploadService.upload(pFile))
                return new SuccessResponseModel(SUCCESS, "Successfully uploaded!");
            else
                return new SuccessResponseModel(FAILURE, "Upload Failure!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SuccessResponseModel(FAILURE, "Upload Failure!");
    }

    @GetMapping("/getUtterance/{id}")
    public ResponseModel getUtteranceData(@PathVariable("id") Integer pId) {

        FalseCommsDao lFalseCommsDao = mFalseCommsRepo.findDataById(pId);
        System.out.println("Data==" + lFalseCommsDao);
        if (lFalseCommsDao != null)
            return new ResponseModel(SUCCESS, "Success!", lFalseCommsDao);
        return new ResponseModel(FAILURE, "Error");
    }

    @PostMapping("/updateUtterance")
    public ResponseModel updateUtteranceData(@RequestBody FalseCommsDao pFalseCommsDao) {

        FalseCommsDao lFalseCommsDao = mFalseCommsRepo.findDataById(pFalseCommsDao.getId());
        if (lFalseCommsDao != null) {
            lFalseCommsDao.setFalseComms(pFalseCommsDao.getFalseComms());
            lFalseCommsDao.setFalseWake(pFalseCommsDao.getFalseWake());
            lFalseCommsDao.setBucket(pFalseCommsDao.getBucket());
            lFalseCommsDao.setComment(pFalseCommsDao.getComment());
            mFalseCommsRepo.save(lFalseCommsDao);
            return new ResponseModel(SUCCESS, "Update Success!", lFalseCommsDao);
        }

        return new ResponseModel(FAILURE, "Update Failure!");
    }


    @GetMapping("/downloadUtterance")
    public ResponseEntity<Object> downloadUtterance() {

        try {

            File lFile = mFileUploadService.download();
            InputStreamResource lResource = new InputStreamResource(new FileInputStream(lFile));
            HttpHeaders lHeaders = new HttpHeaders();
            lHeaders.add("Content-Disposition", String.format("attachment: filename=\"%s\"", lFile.getName()));
            lHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
            lHeaders.add("Pragma", "no-cache");
            lHeaders.add("Expires", "0");


            return ResponseEntity.ok().headers(lHeaders)
                    .contentLength(lFile.length())
                    .contentType(MediaType.parseMediaType("application/txt")).body(lResource);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Error Occured!", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
