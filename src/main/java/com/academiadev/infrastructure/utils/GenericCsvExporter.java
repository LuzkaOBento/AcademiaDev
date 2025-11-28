package com.academiadev.infrastructure.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class GenericCsvExporter {
    public static <T> String exportToCsv(List<T> data, List<String> columns) {
        if (data.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.join(",", columns)).append("\n");

        for (T item : data) {
            String row = columns.stream().map(col -> {
                try {
                    Field field = getField(item.getClass(), col);
                    field.setAccessible(true);
                    Object val = field.get(item);
                    return val != null ? val.toString() : "";
                } catch (Exception e) { return "ERR"; }
            }).collect(Collectors.joining(","));
            sb.append(row).append("\n");
        }
        return sb.toString();
    }

    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), fieldName);
            }
            throw e;
        }
    }
}