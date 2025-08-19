package Recursos;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.mercadopago.resources.payment.Payment;

import java.io.*;

public class GeneradorComprobanteMP {

    public static ByteArrayOutputStream generarComprobante(Payment payment) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        document.addTitle("Comprobante de Pago");

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("Comprobante de Pago", titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("ID del pago: " + payment.getId()));
        document.add(new Paragraph("Estado: " + payment.getStatus()));
        document.add(new Paragraph("Monto: $" + payment.getTransactionAmount()));
        document.add(new Paragraph("Método de pago: " + payment.getPaymentMethodId()));
        document.add(new Paragraph("Fecha: " + payment.getDateCreated()));
        document.add(new Paragraph("Referencia externa: " + payment.getExternalReference()));

        if (payment.getPayer() != null) {
            document.add(new Paragraph("Email del pagador: " + payment.getPayer().getEmail()));
        }

        document.close();
        return out;
    }
}
