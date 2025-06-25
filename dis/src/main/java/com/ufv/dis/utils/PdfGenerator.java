package com.ufv.dis.utils;

import com.github.librepdf.openpdf.text.*;
import com.github.librepdf.openpdf.text.pdf.PdfWriter;
import com.ufv.dis.model.Usuario;

import java.io.FileOutputStream;
import java.util.List;

public class PdfGenerator {

    public static void generarPdf(List<Usuario> usuarios) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream("info.pdf"));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Listado de Usuarios", titleFont));
            document.add(new Paragraph(" ")); // espacio

            for (Usuario u : usuarios) {
                document.add(new Paragraph("ID: " + u.getId(), normalFont));
                document.add(new Paragraph("Nombre: " + u.getNombre() + " " + u.getApellidos(), normalFont));
                document.add(new Paragraph("NIF: " + u.getNif(), normalFont));
                document.add(new Paragraph("Email: " + u.getEmail(), normalFont));

                if (u.getDireccion() != null) {
                    document.add(new Paragraph("Dirección: " +
                            u.getDireccion().getCalle() + " " + u.getDireccion().getNumero() + ", " +
                            u.getDireccion().getPisoLetra() + ", " + u.getDireccion().getCodigoPostal() + ", " +
                            u.getDireccion().getCiudad(), normalFont));
                }

                if (u.getMetodoPago() != null) {
                    document.add(new Paragraph("Método de pago: " +
                            u.getMetodoPago().getNumeroTarjeta() + " - " +
                            u.getMetodoPago().getNombreAsociado(), normalFont));
                }

                document.add(new Paragraph(" "));
                document.add(new LineSeparator());
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
