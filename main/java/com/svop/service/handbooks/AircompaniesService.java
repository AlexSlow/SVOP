package com.svop.service.handbooks;

import com.svop.View.AircompanyView;
import com.svop.tables.Handbooks.Aircompany;
import com.svop.tables.Handbooks.AircompanyRepositpry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AircompaniesService {
    private static Logger logger= LoggerFactory.getLogger(AircompaniesService.class.getName());
    //Кталог для сохранения файлов на сервере
    @Value("${upload.path_server}")
    private String uploadPathServer;
    //Каталог для отображения файлов для http
    @Value("${upload.path}")
    private String uploadPath;
    @Value("${svop.size_logo_x}")
    private Integer width_x;
    @Value("${svop.size_logo_y}")
    private Integer width_y;
    @Autowired
    AircompanyRepositpry aircompanyRepositpry;
    public List<Aircompany> getAircompanies()
    {
        return aircompanyRepositpry.findAll();
    }
    public Page<Aircompany> getAircompanies(Pageable pageable)
    {
        return aircompanyRepositpry.findAll(pageable);
    }
    public void save(AircompanyView aircompanyView) throws IOException {
        logger.info("Сохранение авиакоммпании");
        Aircompany aircompany=new Aircompany(aircompanyView);
        //Если файл существует, то необходимо удалить его
        if (aircompanyView.getId()!=null) {
            Optional<Aircompany>  aircompanyOldData = aircompanyRepositpry.findById(aircompanyView.getId());
            if (aircompanyOldData.get().getLogo()!=null) {
                String[] nameItems = aircompanyOldData.get().getLogo().split(Pattern.quote("/"));
                String name = nameItems[nameItems.length - 1];
                Path oldFilePath = Paths.get(uploadPathServer, name);
                if (Files.exists(oldFilePath)) {
                    Files.delete(oldFilePath);
                }
            }
        }
        //Если файл  был отправлен
        //Сохдадим директорию, если ее нет
        if (!aircompanyView.getFile().getOriginalFilename().isEmpty()){
        Path upload_dir= Paths.get(uploadPathServer);
        if (!Files.isDirectory(upload_dir,LinkOption.NOFOLLOW_LINKS)){
                Files.createDirectory(upload_dir);
        }

        //Универсальный номер
        String uuidFile= UUID.randomUUID().toString();
        String fileName=uuidFile+"_"+aircompanyView.getFile().getOriginalFilename();
        Path filePathUpload=Paths.get(uploadPathServer,fileName);
        aircompanyView.getFile().transferTo(filePathUpload);
        //Это путь для отбражение для Spring
        aircompany.setLogo(uploadPath+fileName);



        //Поптыаемся маштабировать файл

            BufferedImage image = ImageIO.read(filePathUpload.toFile());
            BufferedImage resized = resize(image, width_y, width_x);
            ImageIO.write(resized, "png", filePathUpload.toFile());
        }
        aircompanyRepositpry.save(aircompany);
    }


    public void delete(List<Integer>idl)
    {
        aircompanyRepositpry.deleteByIdIn(idl);
    }
    public Optional<Aircompany>  getByid(Integer id)
    {
        return aircompanyRepositpry.findById(id);
    }

    //Преобразование Буффера изображения
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

}
