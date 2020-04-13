package com.svop.config.qurtz;

import com.svop.job.recovery.RecoveryJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * Это точка входа в приложение с quartz
 */
@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";
    /**Дни период бекапа
     *
     */
    @Value("${svop.backup_period}")
    private Integer period;
    //Активировать при выпуске
    /*
    @Bean("recoveryJobDetail")
    public JobDetailFactoryBean recoveryJobDetail() {
        return QuartzConfig.createJobDetail(RecoveryJob.class, "Recovery Job");
    }

    @Bean("RecoveryTrigger")
    public SimpleTriggerFactoryBean triggerRecoveryJob(@Qualifier("recoveryJobDetail") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, period*1000*60*60*24, "Recovery Trigger");
    }
*/
}
