package com.liuruichao.controller;

import com.liuruichao.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CommonController
 * 
 * @author liuruichao
 * Created on 2016-01-06 13:11
 */
@Controller
@RequestMapping("")
public class CommonController extends BaseController {
    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile[] file) {
        for (MultipartFile f : file) {
            System.out.println(f.getSize());
        }
        return "success";
    }

    @RequestMapping("/test2")
    @ResponseBody
    public String test2() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("hello");
        return "liuruichao";
    }

    @RequestMapping("/sse")
    public void sse(HttpServletResponse response) throws IOException {
        System.out.println(Thread.currentThread().getName());
        response.setContentType("text/event-stream");
        response.getWriter().write("data: liuruichao\n\n");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
