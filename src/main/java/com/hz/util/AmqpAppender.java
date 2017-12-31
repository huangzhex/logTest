package com.hz.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;
import ch.qos.logback.core.status.ErrorStatus;
import com.alibaba.fastjson.JSON;
import com.hz.configurer.SpringContextHolder;
import org.apache.http.client.utils.DateUtils;
import org.springframework.amqp.core.AmqpTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzhe on 2017/12/28.
 */
public class AmqpAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {
    private String project;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        try {
            AmqpTemplate amqpTemplate = SpringContextHolder.getBean("rabbitTemplate");
            if (amqpTemplate != null) {
                Map<String,Object> jsonMap=new HashMap<String, Object>();
                jsonMap.put("project",getProject());
                jsonMap.put("timestamp", DateUtils.formatDate(new Date(eventObject.getTimeStamp()),"yyyy-MM-dd'T'HH:mm:ss,SSSZ"));
                jsonMap.put("message",eventObject.getFormattedMessage());
                jsonMap.put("LEVEL",eventObject.getLevel().levelStr);
                jsonMap.put("thread",eventObject.getThreadName());
                if(eventObject.getCallerData().length>0) {
                    jsonMap.put("class_name", eventObject.getCallerData()[0].getClassName());
                    jsonMap.put("line_number",eventObject.getCallerData()[0].getLineNumber());
                }
//                jsonMap.put("stack_trace",eventObject.getCallerData());
                long elapsedTime=(eventObject.getTimeStamp()-eventObject.getLoggerContextVO().getBirthTime())/1000;
                jsonMap.put("elapsed_time",elapsedTime);
                if(eventObject.getThrowableProxy() != null) {
                    jsonMap.put("throwable", eventObject.getThrowableProxy());
                }
                amqpTemplate.convertAndSend("elk_queue", JSON.toJSONString(jsonMap));
            }
        }catch (Exception ex){}
    }
}
