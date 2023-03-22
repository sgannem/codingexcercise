package com.claritysystemsinc.codeassignment.service.impl;

import com.claritysystemsinc.codeassignment.common.entity.InspectionBean;
import com.claritysystemsinc.codeassignment.exception.ServiceException;
import com.claritysystemsinc.codeassignment.inspectionrules.InspectionRule;
import com.claritysystemsinc.codeassignment.inspectionrules.InspectionRuleEngine;
import com.claritysystemsinc.codeassignment.inspectionrules.InspectionType;
import com.claritysystemsinc.codeassignment.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final InspectionRuleEngine inspectionRuleEngine;

    @Override
    public boolean upload(MultipartFile file) {
        boolean uploadStatus = Boolean.FALSE;
        log.info("upload method is called");
        List<InspectionBean> listOfInspections = processUploadedFile(file);
        if (!listOfInspections.isEmpty()) {
            log.info("#writing into xlsx file");
            writeExcelFile(file, listOfInspections);
            uploadStatus = Boolean.TRUE;
        } else {
            log.info("no list of inspections. hence no need to write xlsx file");
        }
        return uploadStatus;
    }

    private void writeExcelFile(MultipartFile file, List<InspectionBean> listOfInspections) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFCell cell3 = xssfSheet.getRow(0).createCell(2);
            cell3.setCellType(CellType.STRING);
            cell3.setCellValue("Rules");
            CellStyle cellStyle = xssfSheet.getRow(0).getCell(1).getCellStyle();
            cell3.setCellStyle(cellStyle);
            xssfSheet.setColumnWidth(2, 25 * 700);
            for (int i = 0; i < listOfInspections.size(); i++) {
                XSSFCell cell = xssfSheet.getRow(i + 1).createCell(2);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(listOfInspections.get(i).getRules());
            }
            File outFile = new File("EWNworkstreamAutomationOutput.xlsx");
            log.info("#File absolute path:"+outFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(outFile);
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
                            InspectionBean inspectionBean = new InspectionBean(xssfSheet.getRow(i).getCell(0).toString(),
                                    xssfSheet.getRow(i).getCell(1).toString(), rule);
                            listOfInspections.add(inspectionBean);
                        } catch (NullPointerException e) {
                            log.error("There is no inspection rule define for a given " + xssfSheet.getRow(i).getCell(0).toString());
                        }
                    } else {
                        log.info("#It is an empty row");
                    }
                }
                log.info("All Rows are completed");
            }
        } catch (Exception e) {
            log.error("Error occurred during xlsx file read" + e);
            throw new ServiceException("invalid xlsx file");
        }
        log.info("list of inspection beans:" + listOfInspections);
        return listOfInspections;
    }

    /**
     * To check whether row is empty or not
     **/
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
