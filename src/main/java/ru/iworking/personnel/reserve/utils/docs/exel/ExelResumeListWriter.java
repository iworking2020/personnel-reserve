package ru.iworking.personnel.reserve.utils.docs.exel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.personnel.reserve.service.CurrencyService;
import ru.iworking.personnel.reserve.service.EducationService;
import ru.iworking.personnel.reserve.service.ProfFieldService;
import ru.iworking.personnel.reserve.service.WorkTypeService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExelResumeListWriter extends ExelWriterFactory {

    private static final Logger logger = LogManager.getLogger(ExelResumeListWriter.class);

    private ProfFieldService profFieldService = ProfFieldService.INSTANCE;
    private EducationService educationService = EducationService.INSTANCE;
    private CurrencyService currencyService = CurrencyService.INSTANCE;
    private WorkTypeService workTypeService = WorkTypeService.INSTANCE;

    public enum props {
        PATH, LIST_RESUME
    }

    private static volatile ExelResumeListWriter instance;

    public static ExelResumeListWriter getInstance() {
        ExelResumeListWriter localInstance = instance;
        if (localInstance == null) {
            synchronized (ExelResumeListWriter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ExelResumeListWriter();
                }
            }
        }
        return localInstance;
    }

    private ExelResumeListWriter() { }

    @Override
    public void write(Map<String, Object> props) {
        List<Resume> list = new LinkedList<>();
        Object object = props.get(ExelResumeListWriter.props.LIST_RESUME);
        if (object != null) {
            try {
                list = (List<Resume>) props.get(ExelResumeListWriter.props.LIST_RESUME);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        String fileDir = (String) props.get(ExelResumeListWriter.props.PATH);

        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Birthdays");

        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);

            Row row = sheet.createRow(i);
            Cell id = row.createCell(0);
            id.setCellValue(resume.getId());
            Cell lastName = row.createCell(1);
            lastName.setCellValue(resume.getProfile().getLastName());
            Cell firstName = row.createCell(2);
            firstName.setCellValue(resume.getProfile().getFirstName());
            Cell middleName = row.createCell(3);
            middleName.setCellValue(resume.getProfile().getMiddleName());
            Cell profession = row.createCell(4);
            profession.setCellValue(resume.getProfession());
            Cell numberPhone = row.createCell(5);
            numberPhone.setCellValue(resume.getNumberPhone().getNumber());
            Cell email = row.createCell(6);
            email.setCellValue(resume.getEmail());
            Cell profField = row.createCell(7);
            if (resume.getProfFieldId() != null) {
                ProfField profField1 = profFieldService.findById(resume.getProfFieldId());
                profField.setCellValue(profField1.getNameView().getName());
            } else {
                profField.setCellValue("не указана");
            }
            Cell workType = row.createCell(8);
            if (resume.getWorkTypeId() != null) {
                WorkType workType1 = workTypeService.findById(resume.getWorkTypeId());
                workType.setCellValue(workType1.getNameView().getName());
            } else {
                workType.setCellValue("не указан");
            }
            Cell wage = row.createCell(9);
            if (resume.getWage()!= null) {
                wage.setCellValue(resume.getWage().getCountBigDecimal().toString());
            } else {
                workType.setCellValue("не указана");
            }
            Cell currency = row.createCell(10);
            if (resume.getWage() != null && resume.getWage().getCurrencyId()!= null) {
                Currency currency1 = currencyService.findById(resume.getWage().getCurrencyId());
                currency.setCellValue(currency1.getNameView().getName());
            } else {
                currency.setCellValue("не указана");
            }
            Cell education = row.createCell(11);
            //TODO
            /*if (resume.getEducationId() != null) {
                Education education1 = educationService.findById(resume.getEducationId());
                education.setCellValue(education1.getNameView().getName());
            } else {
                education.setCellValue("не указано");
            }*/
            Cell address = row.createCell(12);
            address.setCellValue(resume.getAddress().getHouse());
        }

        try {
            book.write(new FileOutputStream(fileDir));
        } catch (IOException e) {
            logger.error(e);
        }
        try {
            book.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
