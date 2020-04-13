package com.svop.service.admin;

import com.smattme.MysqlExportService;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.isRegularFile;
import static java.nio.file.Files.newDirectoryStream;

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
    @Value("${svop.backup_file_amout}")
    private Integer file_amout;
    @Value("${svop.eneble_backup}")
    private Integer enebleBackup;


    private @Autowired
    SpringBeanJobFactory jobFactory;
    public  boolean export() throws SQLException, IOException, ClassNotFoundException {
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
        /**
         * Если директория не создана, то создадим ее
         */
        createRecoveryDir();
       return backupDataWithDatabase(mysqldump,
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
public List<String> getDumps() throws IOException {
    List<String> dumps=new ArrayList<>();
    //  Получить все папки в каталоге
    try (DirectoryStream<Path> directoryStream = newDirectoryStream(Paths.get(expotrDbDir))) {
        for (Path path : directoryStream) {
            if (isRegularFile(path)) {
                dumps.add(path.getFileName().toString());
            }
        }
    }
    return dumps;
}

    /**
     * Если количество файлов в дампе Бд больше n,тогда удалить последний
     */
    private void dumpsAmoutControl() throws IOException {

    if (new File(expotrDbDir).list().length>file_amout)
    {
        logger.info("Превышено число сохранений БД.");
        List<Path> oldestFiles = findOldestFiles(expotrDbDir, 1);
        for (Path path:oldestFiles){Files.deleteIfExists(path);}
    }
}

    /**
     *
     * @param parentFolder Каталог для удаления
     * @param numberOfOldestFilesToFind Число, которое определяет сколько всего файлов искать
     * @return
     * @throws IOException
     */
    private static List<Path> findOldestFiles(String parentFolder, int numberOfOldestFilesToFind) throws IOException {
        Comparator<? super Path> lastModifiedComparator =
                (p1, p2) -> Long.compare(p1.toFile().lastModified(),
                        p2.toFile().lastModified());

        List<Path> oldestFiles = Collections.emptyList();

        try (Stream<Path> paths = Files.walk(Paths.get(parentFolder))) {
            oldestFiles = paths.filter(Files::isRegularFile)
                    .sorted(lastModifiedComparator)
                    .limit(numberOfOldestFilesToFind)
                    .collect(Collectors.toList());
        }

        return oldestFiles;
    }

    /**
     * Сделать дамп Базы данных
     * @param dumpExePath
     * @param host
     * @param port
     * @param user
     * @param password
     * @param database
     * @param backupPath
     * @return
     */
    private boolean backupDataWithDatabase(String dumpExePath, String host, String port,
                                          String user, String password, String database, String backupPath) throws IOException {
        dumpsAmoutControl();
        boolean status = false;
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
/*
    @Scheduled(cron = "0 15 10 15 * ?")
    public void scheduleTaskUsingCronExpression() {
        if (enebleBackup == 1) {
            try {
                export();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    */

}
