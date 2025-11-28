package com.academiadev.infrastructure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.academiadev.domain.entities.Student;
import com.academiadev.domain.enums.SubscriptionPlan;
import com.academiadev.infrastructure.utils.GenericCsvExporter;

class GenericCsvExporterTest {

    @Test
    void deveGerarCsvCorretamente() {
        Student s1 = new Student("A", "a@a.com", SubscriptionPlan.BASIC);
        Student s2 = new Student("B", "b@b.com", SubscriptionPlan.PREMIUM);
        List<Student> lista = Arrays.asList(s1, s2);
        List<String> colunas = Arrays.asList("name", "email");

        String csv = GenericCsvExporter.exportToCsv(lista, colunas);

        assertTrue(csv.contains("name,email"));
        assertTrue(csv.contains("A,a@a.com"));
        assertTrue(csv.contains("B,b@b.com"));
    }

    @Test
    void deveRetornarVazioSeListaVazia() {
        String result = GenericCsvExporter.exportToCsv(Collections.emptyList(), Arrays.asList("name"));
        assertEquals("", result);
    }
    
@Test
    void deveTratarErroDeReflection() {
        Student s1 = new Student("A", "a@a.com", SubscriptionPlan.BASIC);
        

        String csv = GenericCsvExporter.exportToCsv(Arrays.asList(s1), Arrays.asList("telefone"));
        
        assertTrue(csv.contains("telefone"));
        assertTrue(csv.contains("ERR")); 
    }

   @Test
    void deveTratarCampoComValorNulo() {
        Student s1 = new Student(null, "nulo@a.com", SubscriptionPlan.BASIC);
        
        String csv = GenericCsvExporter.exportToCsv(Arrays.asList(s1), Arrays.asList("name"));
        
        assertTrue(csv.contains("name"));
        assertFalse(csv.contains("null")); 
    }
}