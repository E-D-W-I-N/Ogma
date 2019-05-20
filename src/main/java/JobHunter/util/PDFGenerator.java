package JobHunter.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PDFGenerator {

	private static Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

	public static ByteArrayInputStream customerPDFReport(String title, List<String> headers, List<List<String>> contents) {
		Document document = new Document(PageSize.A2.rotate());
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfWriter.getInstance(document, out);
			document.open();

			// Add Text to PDF file ->
			BaseFont times = BaseFont.createFont("c:/windows/fonts/times.ttf", "cp1251", BaseFont.EMBEDDED);
			Paragraph para = new Paragraph(title, new Font(times, 18));
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(headers.size());
			// Add PDF Table Header ->
			Arrays.stream(headers.toArray())
					.forEach(headerTitle -> {
						PdfPCell header = new PdfPCell();
						Font headFont = new Font(times);
						header.setBackgroundColor(BaseColor.LIGHT_GRAY);
						header.setHorizontalAlignment(Element.ALIGN_CENTER);
						header.setBorderWidth(2);
						header.setPhrase(new Phrase(headerTitle.toString(), headFont));
						table.addCell(header);
					});

			for (List<String> content : contents) {
				for (String value : content) {
					PdfPCell idCell = new PdfPCell(new Phrase(value, new Font(times)));
					idCell.setPaddingLeft(4);
					idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(idCell);
				}
			}
			document.add(table);

			document.close();
		} catch (DocumentException | IOException e) {
			logger.error(e.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
