package com.hz.controller;

import com.alibaba.fastjson.JSON;
import com.hz.model.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by huangzhe on 2017/12/29.
 */
@Controller
public class IndexController {
    protected Logger log = Logger.getLogger(getClass());
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiIgnore()
    @ApiOperation(value = "重定向到api首页")
    public ModelAndView index() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @GetMapping(value = "/message_mq")
    @ApiOperation(value = "消息队列下发短信", httpMethod = "GET")
    public ResponseEntity<String> smSendMQ() {
        User u=null;
        log.info(u.getPwd());
        User user=new User();
        user.setUsername("hz");
        user.setPwd("123");
        rabbitTemplate.convertAndSend("elk_queue", JSON.toJSONString(user));
        log.info("系统调用了发送消息队列接口");
        return new ResponseEntity<String>("hello", HttpStatus.OK);
    }
}
