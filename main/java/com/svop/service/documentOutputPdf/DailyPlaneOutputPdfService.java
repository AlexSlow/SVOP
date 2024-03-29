package com.svop.service.documentOutputPdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.svop.View.DailyScheduleViews.DailyScheduleView;
import com.svop.message.Period;
import com.svop.tables.Handbooks.TypeReys;
import com.svop.tables.daily_schedule.DailyDirection;
import com.svop.tables.journal.JournalProcedure;
import com.svop.tables.journal.ProcedureJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
public class DailyPlaneOutputPdfService {
    @Autowired @Qualifier("Daily")
    ProcedureJournalService procedureJournalService;
    public  ByteArrayInputStream generate(Period period, java.util.List<DailyScheduleView> dailyScheduleViewList) throws IOException, DocumentException {
        BaseFont times =
                BaseFont.createFont(OutputPdfParams.FONT, OutputPdfParams.CHARSET, BaseFont.EMBEDDED);
        Document document = new Document(PageSize.A2);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String username="AnonimUser";
            String dateFoming="Не было проведения";
            JournalProcedure journalProcedure = procedureJournalService.getLast();
            if (journalProcedure!=null) {
                username = journalProcedure.getName();
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru"));
                dateFoming = dateFormat.format(journalProcedure.getDate());
            }


            PdfWriter.getInstance(document, out);
            document.open();
            // Add Text to PDF file ->
            Font font = new Font(times, 24);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            Paragraph para = new Paragraph("Планируемое расписание на период " + dateFormat.format(period.getStart()) + " - " + dateFormat.format(period.getEnd()), font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            Paragraph date = new Paragraph("Дата формирования - " + dateFoming, new Font(times, 18));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(date);
            document.add(Chunk.NEWLINE);

            Paragraph autor = new Paragraph("Сформировано пользователем - " + username, new Font(times, 18));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(autor);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(20);
            table.setWidthPercentage(100);
            // Add PDF Table Header ->
            Font headFont = new Font(times, 16);
            Font bodyFont = new Font(times, 14);

            //Первая строка шапки таблицы
            PdfPCell day = new PdfPCell();
            day.setNoWrap(true);
            day.setHorizontalAlignment(Element.ALIGN_CENTER);
            day.setVerticalAlignment(Element.ALIGN_MIDDLE);
            day.setBorderWidth(2);
            day.setPhrase(new Phrase("День", headFont));
            day.setColspan(2);
            table.addCell(day);

            PdfPCell rout = new PdfPCell();
            rout.setNoWrap(true);
            rout.setHorizontalAlignment(Element.ALIGN_CENTER);
            rout.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rout.setBorderWidth(2);
            rout.setColspan(4);
            rout.setPhrase(new Phrase("Маршрут", headFont));
            table.addCell(rout);

            PdfPCell nomer = new PdfPCell();
            nomer.setNoWrap(true);
            nomer.setHorizontalAlignment(Element.ALIGN_CENTER);
            nomer.setVerticalAlignment(Element.ALIGN_MIDDLE);
            nomer.setBorderWidth(2);
            nomer.setColspan(2);
            nomer.setPhrase(new Phrase("Номер рейса", headFont));
            table.addCell(nomer);

            PdfPCell direction = new PdfPCell();
            direction.setNoWrap(true);
            direction.setHorizontalAlignment(Element.ALIGN_CENTER);
            direction.setVerticalAlignment(Element.ALIGN_MIDDLE);
            direction.setBorderWidth(2);
            direction.setColspan(2);
            direction.setPhrase(new Phrase("Прилет/Вылет", headFont));
            table.addCell(direction);

            PdfPCell timeDeporture = new PdfPCell();
            timeDeporture.setNoWrap(true);
            timeDeporture.setHorizontalAlignment(Element.ALIGN_CENTER);
            timeDeporture.setVerticalAlignment(Element.ALIGN_MIDDLE);
            timeDeporture.setBorderWidth(2);
            timeDeporture.setColspan(2);
            timeDeporture.setPhrase(new Phrase("Время вылета", headFont));
            table.addCell(timeDeporture);

            PdfPCell timePril = new PdfPCell();
            timePril.setNoWrap(true);
            timePril.setHorizontalAlignment(Element.ALIGN_CENTER);
            timePril.setVerticalAlignment(Element.ALIGN_MIDDLE);
            timePril.setBorderWidth(2);
            timePril.setColspan(2);
            timePril.setPhrase(new Phrase("Время прилета", headFont));
            table.addCell(timePril);
//16
            PdfPCell type = new PdfPCell();
            type.setNoWrap(true);
            type.setHorizontalAlignment(Element.ALIGN_CENTER);
            type.setVerticalAlignment(Element.ALIGN_MIDDLE);
            type.setBorderWidth(2);
            type.setColspan(2);
            type.setPhrase(new Phrase("Тип", headFont));
            table.addCell(timePril);

            PdfPCell typeVS = new PdfPCell();
            typeVS.setNoWrap(true);
            typeVS.setHorizontalAlignment(Element.ALIGN_CENTER);
            typeVS.setVerticalAlignment(Element.ALIGN_MIDDLE);
            typeVS.setBorderWidth(2);
            typeVS.setColspan(2);
            typeVS.setPhrase(new Phrase("Тип ВС", headFont));
            table.addCell(typeVS);

            PdfPCell Airline = new PdfPCell();
            Airline.setNoWrap(true);
            Airline.setHorizontalAlignment(Element.ALIGN_CENTER);
            Airline.setVerticalAlignment(Element.ALIGN_MIDDLE);
            Airline.setBorderWidth(2);
            Airline.setColspan(2);
            Airline.setPhrase(new Phrase("Авиалиния", headFont));
            table.addCell(Airline);


            //Теперь Тело таблицы
            for (DailyScheduleView dailyScheduleView : dailyScheduleViewList) {
                PdfPCell bDay = new PdfPCell();
                bDay.setNoWrap(true);
                bDay.setHorizontalAlignment(Element.ALIGN_CENTER);
                bDay.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bDay.setBorderWidth(1);
                bDay.setPhrase(new Phrase(dailyScheduleView.getDay(), bodyFont));
                bDay.setColspan(2);
                table.addCell(bDay);


                PdfPCell bRoute = new PdfPCell();
                bRoute.setNoWrap(true);
                bRoute.setHorizontalAlignment(Element.ALIGN_CENTER);
                bRoute.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bRoute.setBorderWidth(1);
                bRoute.setPhrase(new Phrase(dailyScheduleView.getRout(), bodyFont));
                bRoute.setColspan(4);
                table.addCell(bRoute);

                PdfPCell bNomer = new PdfPCell();
                bNomer.setNoWrap(true);
                bNomer.setHorizontalAlignment(Element.ALIGN_CENTER);
                bNomer.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bNomer.setBorderWidth(1);
                bNomer.setPhrase(new Phrase(dailyScheduleView.getNomer(), bodyFont));
                bNomer.setColspan(2);
                table.addCell(bNomer);
//12

                PdfPCell bDirection = new PdfPCell();
                bDirection.setNoWrap(true);
                bDirection.setHorizontalAlignment(Element.ALIGN_CENTER);
                bDirection.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bDirection.setBorderWidth(1);
                bDirection.setColspan(2);
                bDirection.setPhrase(new Phrase(dailyScheduleView.getDirection(),bodyFont));
                bDirection.setColspan(2);
                table.addCell(bDirection);


                PdfPCell bTimeDeporture = new PdfPCell();
                bTimeDeporture.setNoWrap(true);
                bTimeDeporture.setHorizontalAlignment(Element.ALIGN_CENTER);
                bTimeDeporture.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bTimeDeporture.setBorderWidth(1);
                bTimeDeporture.setPhrase(new Phrase(dailyScheduleView.getTimeDeporture(), bodyFont));
                bTimeDeporture.setColspan(2);
                table.addCell(bTimeDeporture);

                PdfPCell bTimeprilet = new PdfPCell();
                bTimeprilet.setNoWrap(true);
                bTimeprilet.setHorizontalAlignment(Element.ALIGN_CENTER);
                bTimeprilet.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bTimeprilet.setBorderWidth(1);
                bTimeprilet.setPhrase(new Phrase(dailyScheduleView.getTimePrilet(), bodyFont));
                bTimeprilet.setColspan(2);
                table.addCell(bTimeprilet);


                PdfPCell bType = new PdfPCell();
                bType.setNoWrap(true);
                bType.setHorizontalAlignment(Element.ALIGN_CENTER);
                bType.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bType.setBorderWidth(1);
                bType.setPhrase(new Phrase(dailyScheduleView.getType(),bodyFont));
                bType.setColspan(2);
                table.addCell(bType);

                PdfPCell bTipVS = new PdfPCell();
                bTipVS.setNoWrap(true);
                bTipVS.setHorizontalAlignment(Element.ALIGN_CENTER);
                bTipVS.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bTipVS.setBorderWidth(1);
                bTipVS.setPhrase(new Phrase(dailyScheduleView.getTipVs(), bodyFont));
                bTipVS.setColspan(2);
                table.addCell(bTipVS);

                PdfPCell bAirline = new PdfPCell();
                bAirline.setNoWrap(true);
                bAirline.setHorizontalAlignment(Element.ALIGN_CENTER);
                bAirline.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bAirline.setBorderWidth(1);
                bAirline.setPhrase(new Phrase(dailyScheduleView.getAirline(), bodyFont));
                bAirline.setColspan(2);
                table.addCell(bAirline);
            }
            try {
                document.add(table);
            } catch (DocumentException ex) {
                ex.printStackTrace();
            }
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    }




