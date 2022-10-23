package com.starnft.star.admin.process;

import com.starnft.star.business.domain.StarNftSnapshot;
import com.starnft.star.business.service.IStarNftSnapshotService;
import com.starnft.star.common.constant.Constants;
import com.starnft.star.common.constant.ScheduleConstants;
import com.starnft.star.common.constant.StarConstants;
import com.starnft.star.common.exception.StarError;
import com.starnft.star.common.exception.StarException;
import com.starnft.star.common.utils.DateUtil;
import com.starnft.star.common.utils.SnowflakeWorker;
import com.starnft.star.common.utils.StringUtils;
import com.starnft.star.quartz.domain.SysJob;
import com.starnft.star.quartz.service.ISysJobService;
import com.starnft.star.quartz.util.CronUtils;
import com.starnft.star.quartz.util.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Slf4j
@Service
public class NftSnapshotProcess {

    @Autowired
    private IStarNftSnapshotService snapshotService;
    @Autowired
    private ISysJobService sysJobService;


    //insert
    @Transactional
    public boolean saveSnapshot2job(StarNftSnapshot starNftSnapshot){
        try{
            starNftSnapshot.setId(SnowflakeWorker.generateId());
            starNftSnapshot.setSuccessful(StarConstants.SnapshotState.INIT.getCode());
            //先存Job
            SysJob sysJob = new SysJob();
            sysJob.setJobName(starNftSnapshot.getTaskName());
            sysJob.setInvokeTarget(String.format("snapshotTask.snKuai('%s')",starNftSnapshot.getId()));
            String cron = DateUtil.getCron(starNftSnapshot.getExecutionTime());
            sysJob.setCronExpression(cron);
            sysJob.setStatus(ScheduleConstants.Status.NORMAL.getValue());
            sysJob.setConcurrent("1");
            sysJob.setMisfirePolicy(ScheduleConstants.MISFIRE_FIRE_AND_PROCEED);
            sysJob.setCreateBy(starNftSnapshot.getCreateBy());

            int job = sysJobService.saveJob(sysJob);


            starNftSnapshot.setTaskId(sysJob.getJobId());
            int snapshot = snapshotService.insertStarNftSnapshot(starNftSnapshot);
            return job + snapshot == 2;
        }catch (Exception e) {
            log.error("查询快照定时任务失败",e);
            throw new StarException(e.getMessage());
        }
    }

    public void downloadExcel(HttpServletResponse response, StarNftSnapshot snapshot) {
        StarNftSnapshot result = snapshotService.selectStarNftSnapshotById(snapshot.getId());

        if (!result.getSuccessful().equals(StarConstants.SnapshotState.COMPLETE.getCode())) {
            throw new StarException(StarError.SYSTEM_ERROR, "快照任务未执行或执行失败");
        }
        File file = new File(result.getFilePath());
        if (!file.exists()) throw new StarException(StarError.SYSTEM_ERROR, "文件不存在");

        byte[] b = new byte[1024];
        int len = 0;
        FileInputStream fis = null;
        OutputStream ops = null;
        try {
            fis = new FileInputStream(file);
            ops = response.getOutputStream();
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setContentLength((int) file.length());
            while ((len = fis.read(b)) != -1) {
                //输出缓冲区的内容到浏览器，实现文件下载
                ops.write(b, 0, len);
            }
        } catch (Exception e) {
            throw new StarException(StarError.SYSTEM_ERROR, e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                }catch (Exception e){
                    log.error("文件流关闭失败",e);
                }
            }
            if (ops != null){
                try{
                    ops.close();
                    ops = null;
                }catch (Exception e){
                    log.error("文件流关闭失败",e);
                }
            }
        }

    }
    //update
    @Transactional
    public boolean updateSnapshot2Job(StarNftSnapshot starNftSnapshot){

        try{
            int snapshot = snapshotService.updateStarNftSnapshot(starNftSnapshot);

            SysJob sysJob = sysJobService.selectJobById(starNftSnapshot.getTaskId());
            sysJob.setCronExpression(DateUtil.getCron(starNftSnapshot.getExecutionTime()));
            if (!verifyJob(sysJob)) throw new StarException("job格式不符合");
            int job = sysJobService.updateJob(sysJob);
            return  snapshot + job == 2;
        }catch (Exception e){
            log.error("更新数据数据",e);
            throw new StarException(StarError.SYSTEM_ERROR,"更新任务失败");
        }
    }

    private boolean verifyJob(SysJob sysJob){
        if (!CronUtils.isValid(sysJob.getCronExpression()))
        {
            return false;
        }
        else if (StringUtils.containsIgnoreCase(sysJob.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return false;
        }
        else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return false;
        }
        else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return false;
        }
        else if (StringUtils.containsAnyIgnoreCase(sysJob.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return false;
        }
        else if (!ScheduleUtils.whiteList(sysJob.getInvokeTarget()))
        {
            return false;
        }
        return true;
    }
}
