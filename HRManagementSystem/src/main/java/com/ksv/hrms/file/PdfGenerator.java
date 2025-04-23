package com.ksv.hrms.file;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ksv.hrms.dao.AuditlogDao;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {
	
	@Autowired
	private AuditlogDao auditDao;
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;filename=auditlog.pdf");
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		Paragraph paragraph=new Paragraph();
		addFileHeader(paragraph);
		PdfPTable table=new PdfPTable(6);
		addTableHeader(table);
		document.add(paragraph);
		document.add(table);
		document.close();
	}


	private void addFileHeader(Paragraph paragraph) {
		paragraph.setAlignment(Element.ALIGN_CENTER);
		Font font=FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
		paragraph.add(new Phrase("Logs",font));	
	}
	private void addTableHeader(PdfPTable table) throws DocumentException {
		table.setWidthPercentage(110);
		table.setWidths(new float[] {3,2,2,3,2,4});
		table.setSpacingBefore(10);
		PdfPCell cell=new PdfPCell();
		cell.setBackgroundColor(BaseColor.GRAY);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10);
		Font font=FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE,15 ,BaseColor.WHITE);
		Stream.of("Username","Role","Method","URI","Status","Date & Time")
		.forEach(header->{
			cell.setPhrase(new Phrase(header,font));
			table.addCell(cell);
		});
		addTableData(table);	
	}


	private void addTableData(PdfPTable table) {
		auditDao.getAuditlog().forEach(log->{
			table.addCell(log.getUsername());
			table.addCell(String.valueOf(log.getRole()));
			table.addCell(log.getMethod());
			table.addCell(log.getUri());
			table.addCell(String.valueOf(log.getStatus()));
			table.addCell(String.valueOf(log.getTimestamp()));
		});
		
	}
}
