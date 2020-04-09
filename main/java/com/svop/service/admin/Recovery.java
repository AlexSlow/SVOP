package com.svop.service.admin;

import com.smattme.MysqlExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Service
public class Recovery {
    private static final Logger logger= LoggerFactory.getLogger(Recovery.class);
    private String dbName="svop";
    @Value("${spring.datasource.username}")
    private String Userneme;
    @Value("${spring.datasource.password}")
    private String Password;
    @Value("${svop.mysql_port}")
    private String MySqlPort;
    @Value("${svop.host}")
    private String Host;

    @Value("${svop.mysqldump}")
    private String mysqldump;

    @Value("${svop.exportdb}")
    private String expotrDbDir;
    public  void export() throws SQLException, IOException, ClassNotFoundException {
        /*
        //Установим параметры
        Properties properties=new Properties();
        properties.setProperty(MysqlExportService.DB_NAME,"svop");
        properties.setProperty(MysqlExportService.DB_USERNAME, "root");
        properties.setProperty(MysqlExportService.DB_PASSWORD, "543210");
        properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, "jdbc:mysql://localhost:3306/svop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
        createRecoveryDir();
        properties.setProperty(MysqlExportService.TEMP_DIR, expotrDbDir);
        MysqlExportService mysqlExportService = new MysqlExportService(properties);
        mysqlExportService.export();

    }
    */
        createRecoveryDir();
        backupDataWithDatabase(mysqldump,
                Host,
                MySqlPort,
                Userneme,
                Password,
                dbName,
                expotrDbDir);
    }

    //Создать каталог загрузки если его нет
    private void createRecoveryDir() throws IOException {

        Path export= Paths.get(expotrDbDir);
        if (Files.notExists(export))
        {

            Files.createDirectory(export);
        }
    }

    public boolean backupDataWithDatabase(String dumpExePath, String host, String port,
                                          String user, String password, String database, String backupPath) {
        boolean status = false;
        System.out.println(backupPath);
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand = "";
            if (password != "") {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
                logger.info("Backup created successfully for with DB " + database + " in " + host + ":" + port);
            } else {
                status = false;
                logger.info("Could not create the backup for with DB " + database + " in " + host + ":" + port);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            logger.error(ioe.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage());
        }
        return status;
    }
}
