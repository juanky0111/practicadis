package com.ufv.dis.utils;

import com.ufv.dis.model.Usuario;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    private static final float MARGIN = 50;
    private static final float LINE_HEIGHT = 14.5f;

    public static void generarPdf(List<Usuario> usuarios) {
        System.out.println("→ Iniciando generación de PDF con " + usuarios.size() + " usuarios...");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(LINE_HEIGHT);
            contentStream.newLineAtOffset(MARGIN, PDRectangle.A4.getHeight() - MARGIN);

            contentStream.showText("Listado de usuarios:");
            contentStream.newLine();
            contentStream.newLine();

            float yPosition = PDRectangle.A4.getHeight() - MARGIN;

            for (Usuario u : usuarios) {
                if (u == null) continue;

                // Aseguramos que ningún campo sea null
                String nombre = (u.getNombre() != null) ? u.getNombre() : "(sin nombre)";
                String apellidos = (u.getApellidos() != null) ? u.getApellidos() : "";
                String email = (u.getEmail() != null) ? u.getEmail() : "(sin email)";

                contentStream.showText("Usuario: " + nombre + " " + apellidos);
                contentStream.newLine();
                contentStream.showText("Email: " + email);
                contentStream.newLine();
                contentStream.newLine();
            }


            contentStream.endText();
            contentStream.close();

            File outputFile = new File("info.pdf");
            document.save(outputFile);

            System.out.println("✔ PDF generado correctamente en: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("✖ Error al generar el PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
