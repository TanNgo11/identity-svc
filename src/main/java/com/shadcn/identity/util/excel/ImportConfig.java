package com.shadcn.identity.util.excel;

import java.util.ArrayList;
import java.util.List;

import com.shadcn.identity.dto.request.StudentCreationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportConfig {

    private int sheetIndex;

    private int headerIndex;

    private int startRow;

    private Class dataClazz;

    private List<CellConfig> cellImportConfigs;

    public static final ImportConfig studentImport;

    static {
        studentImport = new ImportConfig();
        studentImport.setSheetIndex(0);
        studentImport.setHeaderIndex(0);
        studentImport.setStartRow(1);
        studentImport.setDataClazz(StudentCreationRequest.class);
        List<CellConfig> studentImportCellConfigs = new ArrayList<>();

        studentImportCellConfigs.add(new CellConfig(0, "academicYearId", "Academic Year ID"));
        studentImportCellConfigs.add(new CellConfig(1, "citizenId", "Citizen ID"));
        studentImportCellConfigs.add(new CellConfig(2, "firstName", "First Name"));
        studentImportCellConfigs.add(new CellConfig(3, "middleName", "Middle Name"));
        studentImportCellConfigs.add(new CellConfig(4, "lastName", "Last Name"));
        studentImportCellConfigs.add(new CellConfig(5, "address", "Address"));
        studentImportCellConfigs.add(new CellConfig(6, "dateOfBirth", "Date of Birth"));
        studentImportCellConfigs.add(new CellConfig(7, "phoneNumber", "Phone Number"));
        studentImportCellConfigs.add(new CellConfig(8, "gender", "Gender"));
        studentImportCellConfigs.add(new CellConfig(9, "enrollmentDate", "Enrollment Date"));
        studentImportCellConfigs.add(new CellConfig(10, "departmentId", "Department ID"));
        studentImportCellConfigs.add(new CellConfig(11, "guardianName", "Guardian Name"));
        studentImportCellConfigs.add(new CellConfig(12, "guardianPhoneNumber", "Guardian Phone Number"));
        studentImportCellConfigs.add(new CellConfig(13, "nationality", "Nationality"));
        studentImportCellConfigs.add(new CellConfig(14, "religion", "Religion"));
        studentImportCellConfigs.add(new CellConfig(15, "degreeLevel", "Degree Level"));

        studentImport.setCellImportConfigs(studentImportCellConfigs);
    }
}
