// src/main/java/com/mkcalculadora/ReportGenerator.java
package com.mkcalculadora;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Importações para Apache POI (Excel) - Necessário adicionar as JARs ao classpath
// import org.apache.poi.ss.usermodel.*;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// Importações para OpenPDF (PDF) - Necessário adicionar as JARs ao classpath
// import com.lowagie.text.Document;
// import com.lowagie.text.Paragraph;
// import com.lowagie.text.pdf.PdfWriter;

/**
 * Classe utilitária para gerar relatórios em PDF e Excel.
 */
public class ReportGenerator {

    /**
     * Exporta os dados da cronoanálise para um arquivo PDF.
     * Necessita da biblioteca OpenPDF (ou outra como iText).
     *
     * @param activities A lista de atividades para incluir no relatório.
     * @param responsible O nome do responsável.
     * @param sector O setor de produção.
     * @param productionLine A linha de produção.
     * @param totalStandardTime O tempo padrão total calculado.
     */
    public void exportToPdf(List<Activity> activities, String responsible, String sector, String productionLine, double totalStandardTime) {
        // TODO: Implementar a lógica de exportação para PDF usando OpenPDF.
        // Você precisará adicionar as bibliotecas OpenPDF (ou iText) ao seu projeto.
        // Exemplo básico (requer importações comentadas acima):
        /*
        Document document = new Document();
        try {
            String fileName = "Relatorio_Cronoanalise_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Cabeçalho
            document.add(new Paragraph("MK Calculadora MTM - Relatório de Cronoanálise"));
            document.add(new Paragraph("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
            document.add(new Paragraph("Cidade: Bragança Paulista")); // Você pode tornar isso configurável
            document.add(new Paragraph("Responsável: " + responsible));
            document.add(new Paragraph("Setor: " + sector));
            document.add(new Paragraph("Linha de Produção: " + productionLine));
            document.add(new Paragraph("\n--- Atividades ---"));

            // Lista de atividades
            for (Activity activity : activities) {
                document.add(new Paragraph("- " + activity.getDescription() + ": " + String.format("%.2f", activity.getStandardTime()) + " unidades de tempo"));
            }

            // Total
            document.add(new Paragraph("\n--------------------"));
            document.add(new Paragraph("Tempo Padrão Total: " + String.format("%.2f", totalStandardTime) + " unidades de tempo"));

            System.out.println("Relatório PDF gerado com sucesso: " + fileName);

        } catch (IOException e) {
            System.err.println("Erro ao exportar PDF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
        */
        System.out.println("DEBUG: Lógica de exportação para PDF será implementada aqui.");
    }

    /**
     * Exporta os dados da cronoanálise para um arquivo Excel.
     * Necessita da biblioteca Apache POI.
     *
     * @param activities A lista de atividades para incluir no relatório.
     * @param responsible O nome do responsável.
     * @param sector O setor de produção.
     * @param productionLine A linha de produção.
     * @param totalStandardTime O tempo padrão total calculado.
     */
    public void exportToExcel(List<Activity> activities, String responsible, String sector, String productionLine, double totalStandardTime) {
        // TODO: Implementar a lógica de exportação para Excel usando Apache POI.
        // Você precisará adicionar as bibliotecas Apache POI ao seu projeto.
        // Exemplo básico (requer importações comentadas acima):
        /*
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cronoanálise MTM");

        // Cabeçalho do relatório no Excel
        Row headerInfoRow1 = sheet.createRow(0);
        headerInfoRow1.createCell(0).setCellValue("MK Calculadora MTM - Relatório de Cronoanálise");

        Row headerInfoRow2 = sheet.createRow(1);
        headerInfoRow2.createCell(0).setCellValue("Data:");
        headerInfoRow2.createCell(1).setCellValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        Row headerInfoRow3 = sheet.createRow(2);
        headerInfoRow3.createCell(0).setCellValue("Responsável:");
        headerInfoRow3.createCell(1).setCellValue(responsible);

        Row headerInfoRow4 = sheet.createRow(3);
        headerInfoRow4.createCell(0).setCellValue("Setor:");
        headerInfoRow4.createCell(1).setCellValue(sector);

        Row headerInfoRow5 = sheet.createRow(4);
        headerInfoRow5.createCell(0).setCellValue("Linha de Produção:");
        headerInfoRow5.createCell(1).setCellValue(productionLine);

        // Cabeçalho da tabela de atividades
        Row tableHeaderRow = sheet.createRow(6);
        tableHeaderRow.createCell(0).setCellValue("Descrição da Atividade");
        tableHeaderRow.createCell(1).setCellValue("Tempo Padrão");

        // Dados das atividades
        int rowNum = 7;
        for (Activity activity : activities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(activity.getDescription());
            row.createCell(1).setCellValue(activity.getStandardTime());
        }

        // Total
        Row totalRow = sheet.createRow(rowNum);
        totalRow.createCell(0).setCellValue("Tempo Padrão Total:");
        totalRow.createCell(1).setCellValue(totalStandardTime);

        try {
            String fileName = "Relatorio_Cronoanalise_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("Relatório Excel gerado com sucesso: " + fileName);
        } catch (IOException e) {
            System.err.println("Erro ao exportar Excel: " + e.getMessage());
            e.printStackTrace();
        }
        */
        System.out.println("DEBUG: Lógica de exportação para Excel será implementada aqui.");
    }
}
