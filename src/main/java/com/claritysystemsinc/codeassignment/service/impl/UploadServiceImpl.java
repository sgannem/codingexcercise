package com.claritysystemsinc.codeassignment.service.impl;

import com.claritysystemsinc.codeassignment.common.entity.InspectionBean;
import com.claritysystemsinc.codeassignment.inspectionrules.InspectionRule;
import com.claritysystemsinc.codeassignment.inspectionrules.InspectionRuleEngine;
import com.claritysystemsinc.codeassignment.inspectionrules.InspectionType;
import com.claritysystemsinc.codeassignment.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final InspectionRuleEngine inspectionRuleEngine;

    @Override
    public void upload(MultipartFile file) {
        log.info("upload method is called");
        List<InspectionBean> listOfInspections = processUploadedFile(file);
        if (!listOfInspections.isEmpty()) {
            log.info("#writing into xlsx file");
            writeExcelFile(file, listOfInspections);
        } else {
            log.info("no list of inspections. hence no need to write xlsx file");
        }

    }

    private void writeExcelFile(MultipartFile file, List<InspectionBean> listOfInspections) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFCell cell3 = xssfSheet.getRow(0).createCell(2);
            cell3.setCellType(CellType.STRING);
            cell3.setCellValue("Rules");
            cell3.setCellStyle(xssfSheet.getRow(0).getCell(1).getCellStyle());
            for (int i = 0; i < listOfInspections.size(); i++) {
                XSSFCell cell = xssfSheet.getRow(i + 1).createCell(2);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(listOfInspections.get(i).getRules());
            }
            FileOutputStream fos = new FileOutputStream("EWNworkstreamAutomationOutput.xlsx");
            xssfWorkbook.write(fos);
            fos.close();
        } catch (Exception e) {
            log.error("#Error occurred during xlsx writing" + e);
        }
    }

    private List<InspectionBean> processUploadedFile(MultipartFile file) {
        List<InspectionBean> listOfInspections = new ArrayList<>();
        try {
            File requestedFile = File.createTempFile("Finance-Upload-", Objects.requireNonNull(file.getOriginalFilename()));
            try (FileOutputStream fos = new FileOutputStream(requestedFile)) {
                fos.write(file.getBytes());
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet xssfSheet = workbook.getSheetAt(0);
                for (int i = 0; i < xssfSheet.getPhysicalNumberOfRows(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    log.info("# Row number:" + (i + 1));
                    if (!checkIfRowIsEmpty(xssfSheet.getRow(i))) {
                        log.info("#cols 0:" + xssfSheet.getRow(i).getCell(0));
                        log.info("#cols 1:" + xssfSheet.getRow(i).getCell(1));
                        try {
                            InspectionRule inspectionRule = inspectionRuleEngine.findInspectionRule(
                                    InspectionType.getInspectionType(xssfSheet.getRow(i).getCell(0).toString()));
                            String rule = inspectionRule.inspectionRule(xssfSheet.getRow(i).getCell(1).toString());
                            log.info("#got the rule from the inspection engine");
                            InspectionBean inspectionBean = new InspectionBean();
                            inspectionBean.setInspection(xssfSheet.getRow(i).getCell(0).toString());
                            inspectionBean.setRequiredTasks(xssfSheet.getRow(i).getCell(1).toString());
                            inspectionBean.setRules(rule);
                            listOfInspections.add(inspectionBean);
                        } catch (Exception e) {
                            log.error("Needs to be implemented inspection rule" + e);
                        }
                    }
                }
                log.info("All Rows are completed");
            }
        } catch (IOException e) {
            log.error("Error occurred during xlsx file read" + e);
        }
        log.info("list of inspection beans:" + listOfInspections);
        return listOfInspections;
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }
}
