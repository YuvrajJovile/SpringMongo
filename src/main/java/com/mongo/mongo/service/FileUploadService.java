package com.mongo.mongo.service;

import com.mongo.mongo.document.FalseCommsDao;
import com.mongo.mongo.document.UsersDao;
import com.mongo.mongo.repository.IFalseCommsRepo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
                    System.out.println("HEading==" + lSheet.getRow(0).getCell(i));
                    lFieldsList.add(String.valueOf(lSheet.getRow(0).getCell(i)));
                }
            }

            System.out.println("lastRow==" + lSheet.getLastRowNum());
            System.out.println("fieldSize==" + lFieldsList.size());

            for (int j = 1; j < lSheet.getLastRowNum(); j++) {
                FalseCommsDao lFalseCommsDao = new FalseCommsDao();
                lFalseCommsDao.setId(j);
                lFalseCommsDao.setCount(j);

                for (int i = 0; i < lFieldsList.size(); i++) {

                    String lData = lFieldsList.get(i).toLowerCase();

                    switch (lData) {
                        case "h1_intent":
                            lFalseCommsDao.setUtteranceType(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;
                        case "utterance_id":
                            lFalseCommsDao.setUtteranceId(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;
                        case "asr":
                            lFalseCommsDao.setAsr(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;
                        case "translation":
                            lFalseCommsDao.setTranslation(String.valueOf(lSheet.getRow(j).getCell(i)));
                            break;

                    }

                    mFalseCommsRepo.save(lFalseCommsDao);
                }
            }


            List<FalseCommsDao> list = mFalseCommsRepo.findAll();
            for (int i = 0; i < list.size(); i++) {
                System.out.println("ListData==" + list.get(i).getUtteranceType());
            }
            System.out.println("json==" + mFalseCommsRepo.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    public File download() throws Exception {

        XSSFWorkbook lWorkbook = new XSSFWorkbook();
        XSSFSheet lSheet = lWorkbook.createSheet("result");

        List<FalseCommsDao> lFalseCommsDaos = mFalseCommsRepo.findAll();

        XSSFRow lHeading = lSheet.createRow(0);
        lHeading.createCell(0).setCellValue("h1_intent");
        lHeading.createCell(1).setCellValue("utterance_id");
        lHeading.createCell(2).setCellValue("false_wake");
        lHeading.createCell(3).setCellValue("false_comms");
        lHeading.createCell(4).setCellValue("comment");
        lHeading.createCell(5).setCellValue("bucket");
        lHeading.createCell(6).setCellValue("user_id");

        for (int i = 0; i < lFalseCommsDaos.size(); i++) {
            FalseCommsDao lFalseCommsDao = lFalseCommsDaos.get(i);
            XSSFRow lRow = lSheet.createRow(i + 1);
            System.out.println("DATABefore==" + lFalseCommsDao.getUtteranceType());
            lRow.createCell(0).setCellValue(lFalseCommsDao.getUtteranceType());
            lRow.createCell(1).setCellValue(lFalseCommsDao.getUtteranceId());
            lRow.createCell(2).setCellValue(getFalseWakeOrFalseCommsData(lFalseCommsDao.getFalseWake()));
            lRow.createCell(3).setCellValue(getFalseWakeOrFalseCommsData(lFalseCommsDao.getFalseComms()));
            lRow.createCell(4).setCellValue(lFalseCommsDao.getComment());
            lRow.createCell(5).setCellValue(lFalseCommsDao.getBucket());
            lRow.createCell(6).setCellValue(lFalseCommsDao.getUserId());
            System.out.println("DATA==" + lRow.getCell(0).getStringCellValue());
        }
        File lFile = new File("C:/data/result.xlsx");
        OutputStream lFileOutputStream = new FileOutputStream(lFile);
        lWorkbook.write(lFileOutputStream);
        lFileOutputStream.flush();
        lFileOutputStream.close();

        return lFile;
    }

    private String getFalseWakeOrFalseCommsData(Integer pData) {
        switch (pData) {
            case 0:
                return "false";
            case 1:
                return "true";
        }
        return "";
    }
}
