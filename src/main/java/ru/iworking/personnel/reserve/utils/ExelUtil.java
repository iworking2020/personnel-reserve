/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.iworking.personnel.reserve.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.service.api.utils.LocaleUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExelUtil {
    
    public static void createXLSResumeList(String fileDir, List<Resume> list) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Birthdays");
        
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            
            Row row = sheet.createRow(i);
                Cell id = row.createCell(0);
                id.setCellValue(resume.getId());
                Cell lastName = row.createCell(1);
                lastName.setCellValue(resume.getLastName());
                Cell firstName = row.createCell(2);
                firstName.setCellValue(resume.getFirstName());
                Cell middleName = row.createCell(3);
                middleName.setCellValue(resume.getMiddleName());
                Cell profession = row.createCell(4);
                profession.setCellValue(resume.getProfession());
                Cell numberPhone = row.createCell(5);
                numberPhone.setCellValue(resume.getNumberPhone());
                Cell email = row.createCell(6);
                email.setCellValue(resume.getEmail());
                Cell profField = row.createCell(7);
                if (resume.getProfField() != null) {
                    profField.setCellValue(resume.getProfField().getNameToView(LocaleUtils.getDefault()));
                } else {
                    profField.setCellValue("не указана");
                }
                Cell workType = row.createCell(8);
                if (resume.getWorkType() != null) {
                    workType.setCellValue(resume.getWorkType().getNameToView(LocaleUtils.getDefault()));
                } else {
                    workType.setCellValue("не указан");
                }
                Cell wage = row.createCell(9);
                if (resume.getWage()!= null) {
                    wage.setCellValue(resume.getWage().toString());
                } else {
                    workType.setCellValue("не указана");
                }
                Cell currency = row.createCell(10);
                if (resume.getCurrency()!= null) {
                    currency.setCellValue(resume.getCurrency().getNameToView(LocaleUtils.getDefault()));
                } else {
                    currency.setCellValue("не указана");
                }
                Cell education = row.createCell(11);
                if (resume.getEducation() != null) {
                    education.setCellValue(resume.getEducation().getNameToView(LocaleUtils.getDefault()));
                } else {
                    education.setCellValue("не указано");
                }
                Cell address = row.createCell(12);
                address.setCellValue(resume.getAddress());
        }

        book.write(new FileOutputStream(fileDir));
        book.close();
    }
    
}
