package com.svop.service.documentOutputPdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.svop.View.SeazonScheduleViews.SeazonScheduleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

@Service
public class SeazonOutputPdfService {

    public ByteArrayInputStream generate(List<SeazonScheduleView> seazonScheduleViewList) throws IOException, DocumentException {
         BaseFont times =
                BaseFont.createFont("c:\\windows\\fonts\\times.ttf","cp1251",BaseFont.EMBEDDED);
        Document document = new Document(PageSize.A3);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();
            // Add Text to PDF file ->
            Font font = new Font(times,18);
            Paragraph para = new Paragraph( "Расписание на сезон", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            Paragraph date = new Paragraph( "Дата формирования - 23.0.3.2020", new Font(times,14));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(date);
            document.add(Chunk.NEWLINE);

            Paragraph autor = new Paragraph( "Сформировано пользователем - АС", new Font(times,14));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(autor);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(27);
            table.setWidthPercentage (100);
            // Add PDF Table Header ->
            Font headFont  = new Font(times,12);
            Font bodyFont  = new Font(times,10);

            //Первая строка шапки таблицы
            PdfPCell start = new PdfPCell();
            start.setNoWrap(true);
            start.setHorizontalAlignment(Element.ALIGN_CENTER);
            start.setVerticalAlignment(Element.ALIGN_MIDDLE);
            start.setBorderWidth(2);
            start.setPhrase(new Phrase("Начало", headFont));
            start.setColspan(2);
            start.setRowspan(2);
            table.addCell(start);

            PdfPCell end = new PdfPCell();
            end.setNoWrap(true);
            end.setHorizontalAlignment(Element.ALIGN_CENTER);
            end.setVerticalAlignment(Element.ALIGN_MIDDLE);
            end.setBorderWidth(2);
            end.setPhrase(new Phrase("Конец" ,headFont));
            end.setColspan(2);
            end.setRowspan(2);
            table.addCell(end);

            PdfPCell rout = new PdfPCell();
            rout.setNoWrap(true);
            rout.setHorizontalAlignment(Element.ALIGN_CENTER);
            rout.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rout.setBorderWidth(2);
            rout.setColspan(5);
            rout.setPhrase(new Phrase("Маршрут" ,headFont));
            rout.setRowspan(2);
            table.addCell(rout);

            PdfPCell prilet = new PdfPCell();
            prilet.setNoWrap(true);
            prilet.setHorizontalAlignment(Element.ALIGN_CENTER);
            prilet.setVerticalAlignment(Element.ALIGN_MIDDLE);
            prilet.setBorderWidth(2);
            prilet.setPhrase(new Phrase("Прилет" ,new Font(times,16)));
            prilet.setColspan(7);
            table.addCell(prilet);

            PdfPCell vilet = new PdfPCell();
           vilet.setNoWrap(true);
            vilet.setHorizontalAlignment(Element.ALIGN_CENTER);
            vilet.setVerticalAlignment(Element.ALIGN_MIDDLE);
            vilet.setBorderWidth(2);
            vilet.setPhrase(new Phrase("Вылет" ,new Font(times,16)));
            vilet.setColspan(7);
            table.addCell(vilet);

            PdfPCell tip_vs = new PdfPCell();
           tip_vs.setNoWrap(true);
            tip_vs.setHorizontalAlignment(Element.ALIGN_CENTER);
            tip_vs.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tip_vs.setBorderWidth(2);
            tip_vs.setPhrase(new Phrase("Тип ВС" ,headFont));
            tip_vs.setColspan(2);
            tip_vs.setRowspan(2);
            table.addCell(tip_vs);

            PdfPCell airline = new PdfPCell();
            airline.setNoWrap(true);
            airline.setHorizontalAlignment(Element.ALIGN_CENTER);
            airline.setVerticalAlignment(Element.ALIGN_MIDDLE);
            airline.setBorderWidth(2);
            airline.setColspan(2);
            airline.setPhrase(new Phrase("Авиалиния" ,headFont));
            airline.setRowspan(2);
            table.addCell(airline);

            //Вторая строка


            PdfPCell nomerPrilet = new PdfPCell();
            nomerPrilet.setNoWrap(true);
            nomerPrilet.setHorizontalAlignment(Element.ALIGN_CENTER);
            nomerPrilet.setVerticalAlignment(Element.ALIGN_MIDDLE);
            nomerPrilet.setBorderWidth(1);
            nomerPrilet.setPhrase(new Phrase("Номер" ,headFont));
            nomerPrilet.setColspan(2);
            table.addCell(nomerPrilet);

            PdfPCell priletDays = new PdfPCell();
            priletDays.setNoWrap(true);
            priletDays.setHorizontalAlignment(Element.ALIGN_CENTER);
            priletDays.setVerticalAlignment(Element.ALIGN_MIDDLE);
            priletDays.setBorderWidth(1);
            priletDays.setPhrase(new Phrase("Дни" ,headFont));
            priletDays.setColspan(2);
            table.addCell(priletDays);

            PdfPCell PriletTimeOtpravl = new PdfPCell();
            PriletTimeOtpravl.setNoWrap(true);
            PriletTimeOtpravl.setHorizontalAlignment(Element.ALIGN_CENTER);
            PriletTimeOtpravl.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PriletTimeOtpravl.setBorderWidth(1);
            PriletTimeOtpravl.setColspan(2);
            PriletTimeOtpravl.setPhrase(new Phrase("Отправл." ,headFont));
            table.addCell(PriletTimeOtpravl);


            PdfPCell PriletTimePrib = new PdfPCell();
            PriletTimePrib.setNoWrap(true);
            PriletTimePrib.setHorizontalAlignment(Element.ALIGN_CENTER);
            PriletTimePrib.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PriletTimePrib.setBorderWidth(1);
            PriletTimePrib.setPhrase(new Phrase("Приб." ,headFont));
            table.addCell(PriletTimePrib);




            PdfPCell nomerVilet = new PdfPCell();
            nomerVilet.setNoWrap(true);
            nomerVilet.setHorizontalAlignment(Element.ALIGN_CENTER);
            nomerVilet.setVerticalAlignment(Element.ALIGN_MIDDLE);
            nomerVilet.setBorderWidth(1);
            nomerVilet.setPhrase(new Phrase("Номер" ,headFont));
            nomerVilet.setColspan(2);
            table.addCell(nomerVilet);

            PdfPCell ViletDays = new PdfPCell();
            //ViletDays.setNoWrap(true);
            ViletDays.setHorizontalAlignment(Element.ALIGN_CENTER);
            ViletDays.setVerticalAlignment(Element.ALIGN_MIDDLE);
            ViletDays.setBorderWidth(1);
            ViletDays.setPhrase(new Phrase("Дни" ,headFont));
            ViletDays.setColspan(2);
            table.addCell(ViletDays);

            PdfPCell ViletTimeOtpravl = new PdfPCell();
            ViletTimeOtpravl.setNoWrap(true);
            ViletTimeOtpravl.setHorizontalAlignment(Element.ALIGN_CENTER);
            ViletTimeOtpravl.setVerticalAlignment(Element.ALIGN_MIDDLE);
            ViletTimeOtpravl.setBorderWidth(1);
            ViletTimeOtpravl.setColspan(2);
            ViletTimeOtpravl.setPhrase(new Phrase("Отправл." ,headFont));
            table.addCell(ViletTimeOtpravl);


            PdfPCell ViletTimePrib = new PdfPCell();
            ViletTimePrib.setNoWrap(true);
            ViletTimePrib.setHorizontalAlignment(Element.ALIGN_CENTER);
            ViletTimePrib.setVerticalAlignment(Element.ALIGN_MIDDLE);
            ViletTimePrib.setBorderWidth(1);
            ViletTimePrib.setPhrase(new Phrase("Приб." ,headFont));
            table.addCell(ViletTimePrib);

           //Теперь Тело таблицы
            for(SeazonScheduleView seazonScheduleView:seazonScheduleViewList)
            {
                PdfPCell bBegin = new PdfPCell();
                bBegin.setNoWrap(true);
                bBegin.setHorizontalAlignment(Element.ALIGN_CENTER);
                bBegin.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bBegin.setBorderWidth(1);
                bBegin.setPhrase(new Phrase(seazonScheduleView.getPeriodStart() ,bodyFont));
                bBegin.setColspan(2);
                table.addCell(bBegin);

                PdfPCell bEnd = new PdfPCell();
                bEnd.setNoWrap(true);
                bEnd.setHorizontalAlignment(Element.ALIGN_CENTER);
                bEnd.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bEnd.setBorderWidth(1);
                bEnd.setPhrase(new Phrase(seazonScheduleView.getPeriodEnd() ,bodyFont));
                bEnd.setColspan(2);
                table.addCell(bEnd);

                PdfPCell bRoute = new PdfPCell();
                bRoute.setNoWrap(true);
                bRoute.setHorizontalAlignment(Element.ALIGN_CENTER);
                bRoute.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bRoute.setBorderWidth(1);
                bRoute.setPhrase(new Phrase(seazonScheduleView.getRoutRu() ,bodyFont));
                bRoute.setColspan(5);
                table.addCell(bRoute);

                PdfPCell bPriletNomer = new PdfPCell();
                bPriletNomer.setNoWrap(true);
                bPriletNomer.setHorizontalAlignment(Element.ALIGN_CENTER);
                bPriletNomer.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bPriletNomer.setBorderWidth(1);
                bPriletNomer.setPhrase(new Phrase(seazonScheduleView.getNomerPrilet(),bodyFont));
                bPriletNomer.setColspan(2);
                table.addCell(bPriletNomer);


                PdfPCell bPriletDays = new PdfPCell();
                bPriletDays.setNoWrap(true);
                bPriletDays.setHorizontalAlignment(Element.ALIGN_CENTER);
                bPriletDays.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bPriletDays.setBorderWidth(1);
                bPriletDays.setPhrase(new Phrase(seazonScheduleView.getPriletDays(),bodyFont));
                bPriletDays.setColspan(2);
                table.addCell(bPriletDays);

                PdfPCell bPriletOtpravl = new PdfPCell();
                bPriletOtpravl.setNoWrap(true);
                bPriletOtpravl.setHorizontalAlignment(Element.ALIGN_CENTER);
                bPriletOtpravl.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bPriletOtpravl.setBorderWidth(1);
                bPriletOtpravl.setPhrase(new Phrase(seazonScheduleView.getPrilet_time_otpravl(),bodyFont));
                bPriletOtpravl.setColspan(2);
                table.addCell(bPriletOtpravl);

                PdfPCell bPriletPrib = new PdfPCell();
                bPriletPrib.setNoWrap(true);
                bPriletPrib.setHorizontalAlignment(Element.ALIGN_CENTER);
                bPriletPrib.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bPriletPrib.setBorderWidth(1);
                bPriletPrib.setPhrase(new Phrase(seazonScheduleView.getPrilet_time_prib(),bodyFont));
                table.addCell(bPriletPrib);

                PdfPCell bViletNomer = new PdfPCell();
                bViletNomer.setNoWrap(true);
                bViletNomer.setHorizontalAlignment(Element.ALIGN_CENTER);
                bViletNomer.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bViletNomer.setBorderWidth(1);
                bViletNomer.setPhrase(new Phrase(seazonScheduleView.getNomerVilet(),bodyFont));
                bViletNomer.setColspan(2);
                table.addCell(bViletNomer);

                PdfPCell bViletDays = new PdfPCell();
                bViletDays.setNoWrap(true);
                bViletDays.setHorizontalAlignment(Element.ALIGN_CENTER);
                bViletDays.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bViletDays.setBorderWidth(1);
                bViletDays.setPhrase(new Phrase(seazonScheduleView.getViletDays(),bodyFont));
                bViletDays.setColspan(2);
                table.addCell(bViletDays);

                PdfPCell bViletOtpravl = new PdfPCell();
                bViletOtpravl.setNoWrap(true);
                bViletOtpravl.setHorizontalAlignment(Element.ALIGN_CENTER);
                bViletOtpravl.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bViletOtpravl.setBorderWidth(1);
                bViletOtpravl.setPhrase(new Phrase(seazonScheduleView.getVilet_time_otpravl(),bodyFont));
                bViletOtpravl.setColspan(2);
                table.addCell(bViletOtpravl);

                PdfPCell bViletPrib = new PdfPCell();
                bViletPrib.setNoWrap(true);
                bViletPrib.setHorizontalAlignment(Element.ALIGN_CENTER);
                bViletPrib.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bViletPrib.setBorderWidth(1);
                bViletPrib.setPhrase(new Phrase(seazonScheduleView.getVilet_time_prib(),bodyFont));
                table.addCell(bViletPrib);

                PdfPCell bTipVS = new PdfPCell();
                bTipVS.setNoWrap(true);
                bTipVS.setHorizontalAlignment(Element.ALIGN_CENTER);
                bTipVS.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bTipVS.setBorderWidth(1);
                bTipVS.setPhrase(new Phrase(seazonScheduleView.getTip_vs(),bodyFont));
                bTipVS.setColspan(2);
                table.addCell(bTipVS);

                PdfPCell bAirline = new PdfPCell();
                bAirline.setNoWrap(true);
                bAirline.setHorizontalAlignment(Element.ALIGN_CENTER);
                bAirline.setVerticalAlignment(Element.ALIGN_MIDDLE);
                bAirline.setBorderWidth(1);
                bAirline.setPhrase(new Phrase(seazonScheduleView.getAirline().name(),bodyFont));
                bAirline.setColspan(2);
                table.addCell(bAirline);

            }
            document.add(table);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }



}
