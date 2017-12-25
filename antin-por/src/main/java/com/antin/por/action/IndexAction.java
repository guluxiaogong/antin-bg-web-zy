package com.antin.por.action;

/**
 * Created by jichangjin on 2017/11/29.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/6/21.
 * demo类
 */
@Controller
public class IndexAction {
    private static final Logger log = LoggerFactory.getLogger(IndexAction.class);

    @RequestMapping("/index")
    public String index() {
        log.info("request index page success...");
        return "/portrait/html/index";
    }

}

