package com.shadcn.identity.util.excel;

import com.shadcn.identity.dto.response.ExcelStudentResponse;
import com.shadcn.identity.dto.response.StudentProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportConfig {
    private int sheetIndex;

    private int startRow;

    private Class dataClazz;

    private List<CellConfig> cellExportConfigList;

    public static final ExportConfig studentsExport;

    static{
        studentsExport = new ExportConfig();
        studentsExport.setSheetIndex(0);
        studentsExport.setStartRow(1);
        studentsExport.setDataClazz(ExcelStudentResponse.class);
        List<CellConfig> studentCellConfig = new ArrayList<>();

        studentCellConfig.add(new CellConfig(0, "academicYearId", "Academic Year ID"));
        studentCellConfig.add(new CellConfig(1, "citizenId", "Citizen ID"));
        studentCellConfig.add(new CellConfig(2, "firstName", "First Name"));
        studentCellConfig.add(new CellConfig(3, "middleName", "Middle Name"));
        studentCellConfig.add(new CellConfig(4, "lastName", "Last Name"));
        studentCellConfig.add(new CellConfig(5, "address", "Address"));
        studentCellConfig.add(new CellConfig(6, "dateOfBirth", "Date of Birth"));
        studentCellConfig.add(new CellConfig(7, "phoneNumber", "Phone Number"));
        studentCellConfig.add(new CellConfig(8, "gender", "Gender"));
        studentCellConfig.add(new CellConfig(9, "enrollmentDate", "Enrollment Date"));
        studentCellConfig.add(new CellConfig(10, "departmentId", "Department ID"));
        studentCellConfig.add(new CellConfig(11, "guardianName", "Guardian Name"));
        studentCellConfig.add(new CellConfig(12, "guardianPhoneNumber", "Guardian Phone Number"));
        studentCellConfig.add(new CellConfig(13, "nationality", "Nationality"));
        studentCellConfig.add(new CellConfig(14, "religion", "Religion"));
        studentCellConfig.add(new CellConfig(15, "degreeLevel", "Degree Level"));
        studentCellConfig.add(new CellConfig(16, "username", "Username")); 
        studentCellConfig.add(new CellConfig(17, "password", "Password")); 
        studentCellConfig.add(new CellConfig(18, "email", "Email")); 

        studentsExport.setCellExportConfigList(studentCellConfig);
    }
}
