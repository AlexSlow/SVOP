package com.svop.service.handbooks;

import com.svop.View.AircompanyView;
import com.svop.service.SeazonSchedule.SeazonScheduleInterface;
import com.svop.tables.Handbooks.Aircompany;
import com.svop.tables.Handbooks.AircompanyRepositpry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
@Transactional
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
    //Загрузка больших изображений
    @Value("${svop.size_lage_logo_x}")
    private Integer width_lage_x;
    @Value("${svop.size_lage_logo_y}")
    private Integer width_lage_y;

    @Value("${upload.path_server_lage}")
    private String uploadPathServerLage;
    //Каталог для отображения файлов для http
    @Value("${upload.lage_path}")
    private String uploadPathLage;
    @Autowired
    AircompanyRepositpry aircompanyRepositpry;
    @Autowired private SeazonScheduleInterface seazonSchedule;
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
                //Удалить лого
                Path oldFilePath = Paths.get(uploadPathServer, name);
                if (Files.exists(oldFilePath)) {
                    //Если изображение еще используется, тогда
                    if (!seazonSchedule.isImgExists(oldFilePath.toString()))
                    Files.delete(oldFilePath);
                }
                //Удалить большое лого
                Path oldFilePathLage = Paths.get(uploadPathServerLage, name);
                if (Files.exists(oldFilePathLage)) {
                    Files.delete(oldFilePathLage);
                }
            }
        }
        if (!aircompanyView.getFile().getOriginalFilename().isEmpty()) {
        createDirectory(uploadPathServer);
        createDirectory(uploadPathServerLage);


        //Универсальный номер
        String uuidFile= UUID.randomUUID().toString();
        String fileName=uuidFile+"_"+aircompanyView.getFile().getOriginalFilename();
        Path filePathUpload=Paths.get(uploadPathServer,fileName);
        aircompanyView.getFile().transferTo(filePathUpload);
        //Это путь для отбражение для Spring
        aircompany.setLogo(uploadPath+fileName);
        aircompany.setLogoLage(uploadPathLage+fileName);


            Path fileUploadLage=Paths.get(uploadPathServerLage,fileName);
            if (Files.exists(filePathUpload))
            {
                Files.copy(filePathUpload,fileUploadLage, StandardCopyOption.REPLACE_EXISTING);
            }
            BufferedImage imagelage = ImageIO.read(fileUploadLage.toFile());
            BufferedImage resizedLage = resize(imagelage, width_lage_y, width_lage_x);
            ImageIO.write(resizedLage, "png", fileUploadLage.toFile());

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
    private static void createDirectory(String uploadPathServer) throws IOException {
        //Если файл  был отправлен
        //Сохдадим директорию, если ее нет
        Path upload_dir = Paths.get(uploadPathServer);
        if (!Files.isDirectory(upload_dir, LinkOption.NOFOLLOW_LINKS)) {
            Files.createDirectory(upload_dir);
        }
    }

        private static Path getUUID(String uploadPathServer,String file){
            String uuidFile= UUID.randomUUID().toString();
            String fileName=uuidFile+"_"+file;
            Path filePathUpload=Paths.get(uploadPathServer,fileName);
            return filePathUpload;
        }

}
