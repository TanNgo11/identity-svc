package com.shadcn.identity.util.excel;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExcelUtils {

    private static <T> String getCellValue(T data, CellConfig cellConfig, Class clazz) {
        String fieldName = cellConfig.getFieldName();
        try {
            Field field = getDeclaredField(clazz, fieldName);
            if (!ObjectUtils.isEmpty(field)) {
                field.setAccessible(true);
                return !ObjectUtils.isEmpty(field.get(data)) ? field.get(data).toString() : "";
            }
            return "";
        } catch (Exception e) {
            log.info("" + e);
            return "";
        }
    }

    private static Field getDeclaredField(Class clazz, String fieldName) {
        if (ObjectUtils.isEmpty(clazz) || ObjectUtils.isEmpty(fieldName)) {
            return null;
        }
        do {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {
                log.info("" + e);
            }
        } while ((clazz = clazz.getSuperclass()) != null);

        return null;
    }

    public static <T> List<T> getImportData(Workbook workbook, ImportConfig importConfig) {
        List<T> list = new ArrayList<>();

        List<CellConfig> cellConfigs = importConfig.getCellImportConfigs();

        int countSheet = 0;

        for (Sheet sheet : workbook) {
            if (countSheet != importConfig.getSheetIndex()) {
                countSheet++;
                continue;
            }

            int countRow = 0;
            for (Row row : sheet) {
                if (countRow < importConfig.getStartRow()) {
                    countRow++;
                    continue;
                }
                T rowData = getRowData(row, cellConfigs, importConfig.getDataClazz());
                list.add(rowData);
                countRow++;
            }
            countSheet++;
        }
        return list;
    }

    private static <T> T getRowData(Row row, List<CellConfig> cellConfigs, Class dataClazz) {
        T instance = null;
        try {
            instance = (T) dataClazz.getDeclaredConstructor().newInstance();

            for (int i = 0; i < cellConfigs.size(); i++) {
                CellConfig currentCell = cellConfigs.get(i);
                try {
                    Field field = getDeclaredField(dataClazz, currentCell.getFieldName());

                    Cell cell = row.getCell(currentCell.getColumnIndex());
                    if (!ObjectUtils.isEmpty(cell)) {
                        cell.setCellType(CellType.STRING);

                        Object cellValue = cell.getStringCellValue();

                        setFieldValue(instance, field, cellValue);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return instance;
    }

    private static <T> void setFieldValue(Object instance, Field field, Object cellValue) {
        if (ObjectUtils.isEmpty(instance) || ObjectUtils.isEmpty(field)) {
            return;
        }

        Class clazz = field.getType();

        Object valueConverted = parseValueByClass(clazz, cellValue);

        field.setAccessible(true);

        try {
            field.set(instance, valueConverted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object parseValueByClass(Class clazz, Object cellValue) {
        if (ObjectUtils.isEmpty(cellValue) || ObjectUtils.isEmpty(clazz)) {
            return null;
        }
        String clazzName = clazz.getSimpleName();

        switch (clazzName) {
            case "char":
                cellValue = parseChar(cellValue);
                break;
            case "String":
                cellValue = cellValue.toString().trim();
                break;
            case "boolean":
            case "Boolean":
                cellValue = parseBoolean(cellValue);
                break;
            case "byte":
            case "Byte":
                cellValue = parseByte(cellValue);
                break;
            case "short":
            case "Short":
                cellValue = parseShort(cellValue);
                break;
            case "int":
            case "Integer":
                cellValue = parseInt(cellValue);
                break;
            case "long":
            case "Long":
                cellValue = parseLong(cellValue);
                break;
            case "float":
            case "Float":
                cellValue = parseFloat(cellValue);
                break;
            case "double":
            case "Double":
                cellValue = parseDouble(cellValue);
                break;
            case "Date":
                cellValue = parseDate(cellValue);
                break;
            case "Instant":
                cellValue = parseInstant(cellValue);
                break;
            case "Enum":
                cellValue = parseEnum(cellValue, clazz);
                break;
            case "Map":
                cellValue = parseMap(cellValue);
                break;
            case "BigDecimal":
                cellValue = parseBigDecimal(cellValue);
                break;
            default:
                break;
        }
        return cellValue;
    }

    private static Object parseChar(Object value) {
        return ObjectUtils.isEmpty(value) ? null : (char) value;
    }

    private static Object parseBoolean(Object value) {
        return ObjectUtils.isEmpty(value) ? null : (Boolean) value;
    }

    private static Object parseMap(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return (Map) value;
    }

    private static Object parseEnum(Object value, Class clazz) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        String valueStr = value.toString().trim();
        return Enum.valueOf(clazz, valueStr);
    }

    private static Date parseDate(Object value) {
        String[] formatsDate = {"yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy"};

        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        String dateStr = value.toString();
        for (String format : formatsDate) {
            Date date = null;

            try {
                DateFormat dateFormat = new SimpleDateFormat(format);
                date = dateFormat.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ObjectUtils.isEmpty(date)) {
                return date;
            }
        }

        try {
            Date date = (Date) value;
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private static Object parseInstant(Object value) {
        return ObjectUtils.isEmpty(value) ? null : parseDate(value).toInstant();
    }

    private static Double parseDouble(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private static Object parseFloat(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double doubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(doubleValue) ? null : doubleValue.floatValue();
    }

    private static Object parseLong(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double longDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(longDoubleValue) ? null : longDoubleValue.longValue();
    }

    private static Object parseShort(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double shortDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(shortDoubleValue) ? null : shortDoubleValue.shortValue();
    }

    private static Object parseInt(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double intDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(intDoubleValue) ? null : intDoubleValue.intValue();
    }

    private static Object parseBigDecimal(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            return BigDecimal.valueOf(Double.valueOf(value.toString()));
        } catch (Exception e) {
            return null;
        }
    }

    private static Object parseByte(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        Double byteDoubleValue = parseDouble(value);
        return ObjectUtils.isEmpty(byteDoubleValue) ? null : byteDoubleValue.byteValue();
    }
}
