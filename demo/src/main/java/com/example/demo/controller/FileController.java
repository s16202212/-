package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.impl.QuestionServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @Author: lai
 * @DateTime: 2020/6/5 9:59
 */
@Controller
public class FileController {

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@Param("id") Long id,
                         @RequestParam("file") MultipartFile file) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePath = "E:\\homework\\demo\\src\\main\\resources\\static\\data\\files";
        Question question = questionService.getById(id);
        // 文件重命名，防止重复
//        fileName = filePath + UUID.randomUUID() + fileName;
        // 文件对象
        File dest = new File(filePath + "/" + fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            question.setFileUrl(filePath);
            question.setFileName(fileName);
            questionService.createOrUpdate(question);
            return "redirect:/question";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response,@RequestParam("id") Long id) throws Exception {
        Question questionFiles = questionService.getById(id);
        String filepath = questionFiles.getFileUrl() + "//" + questionFiles.getFileName();
        String fileName = questionFiles.getFileName();
        // 文件地址，真实环境是存放在数据库中的
        File file = new File(filepath);
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + fileName);
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }

    @RequestMapping("/imgUpload")
    @ResponseBody
    public String updateUser(@Param("userId") Long userId,
                             @Param("email") String email,
                             @Param("name") String name,
                             @Param("password") String password,
                             @RequestParam("file") MultipartFile file) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 获取后缀名
        // String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件保存路径
        String filePath = "E:\\homework\\demo\\src\\main\\resources\\static\\images";
        User user = userService.findById(userId);
        // 文件重命名，防止重复
        //        fileName = filePath + UUID.randomUUID() + fileName;
        // 文件对象
        File dest = new File(filePath + "/" + fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            user.setAvatarUrl(filePath);
            user.setFileName(fileName);
            userService.createOrUpdate(user);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

}