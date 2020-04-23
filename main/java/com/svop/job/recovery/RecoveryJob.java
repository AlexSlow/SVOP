package com.svop.job.recovery;

import com.svop.service.admin.Recovery;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class RecoveryJob implements InterruptableJob {
    @Autowired
    Recovery recovery;
    private Boolean stopFlag;

    public Boolean getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(Boolean stopFlag) {
        this.stopFlag = stopFlag;
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (stopFlag) return;
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

    @Override
    public void interrupt() throws UnableToInterruptJobException {
       stopFlag=!stopFlag;
    }
}