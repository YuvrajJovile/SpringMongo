package com.mongo.mongo.service;

import com.mongo.mongo.document.FalseCommsDao;
import com.mongo.mongo.repository.IFalseCommsRepo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    private IFalseCommsRepo mFalseCommsRepo;

    public boolean upload(MultipartFile pMultipartFile) throws Exception {

        try {

            File lTempFile = new File("C:/data/" + pMultipartFile.getOriginalFilename());
            pMultipartFile.transferTo(lTempFile);
            Workbook lWorkbook = WorkbookFactory.create(lTempFile);
            Sheet lSheet = lWorkbook.getSheetAt(0);

            List<String> lFieldsList = new ArrayList<>();

            //get the required fields
            if (lSheet.getPhysicalNumberOfRows() > 0) {
                for (int i = 0; i < lSheet.getRow(0).getLastCellNum(); i++) {
                    lFieldsList.add(String.valueOf(lSheet.getRow(0).getCell(i)));
                }
            }


            for (int j = 1; j < lSheet.getLastRowNum(); j++) {
                FalseCommsDao lFalseCommsDao = new FalseCommsDao();
                lFalseCommsDao.setId(j);
                lFalseCommsDao.setCount(j);

                for (int i = 0; i < lSheet.getRow(j).getLastCellNum(); i++) {

                    String lData = lFieldsList.get(i).toLowerCase();

                    switch (lData) {
                        case "utteranceid":
                            lFalseCommsDao.setUtteranceId(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;
                        case "asr":
                            lFalseCommsDao.setAsr(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;
                        case "translation":
                            lFalseCommsDao.setTranslation(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;
                        default:
                            lFalseCommsDao.setComment(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;

                    }

                    mFalseCommsRepo.save(lFalseCommsDao);
                }
            }
            System.out.println("json==" + mFalseCommsRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }
}
