package com.epam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.epam.entity.Ticket;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Component
public class PdfCreator {
    public void printTicketsPdf(List<Ticket> ticketList, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document pdfDocument = new Document(pdf);
        Paragraph title = new Paragraph("Tickets");
        title.setFontSize(30f);
        title.setItalic();
        pdfDocument.add(title);
        for (Ticket ticket : ticketList) {
            Paragraph content = new Paragraph();
            content.add(ticket.toString());
            pdfDocument.add(content);
        }
        pdfDocument.close();
    }
}
