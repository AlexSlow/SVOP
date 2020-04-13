package com.svop.job.recovery;

import com.svop.service.admin.Recovery;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class RecoveryJob implements Job {
    @Autowired
    Recovery recovery;

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            recovery.export();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}