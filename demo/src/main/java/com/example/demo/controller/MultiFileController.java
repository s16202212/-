package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.impl.QuestionServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
public class MultiFileController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    QuestionServiceImpl questionService;

    @GetMapping("/multiUpload/{id}")
    public String multiUpload(@PathVariable("id") Long id,
                         HttpServletRequest request,
                         HttpSession session,
                         Model model){
        User user = (User) request.getSession().getAttribute("user");
        session.setAttribute("user", user);
        Question question = questionService.getById(id);
        model.addAttribute("question",question);
        return "upload";
    }

    @RequestMapping(value="multifileUpload",method=RequestMethod.POST)
    /**public @ResponseBody String multifileUpload(@RequestParam("fileName")List<MultipartFile> files) */

    public 	@ResponseBody String multifileUpload(HttpServletRequest request,
                                                    HttpSession session,
                                                    @RequestParam("id") Long id) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileName");
        if (files.isEmpty()) {
            return "false";
        }
        String paths = "E:\\homework\\demo\\src\\main\\resources\\static\\data";
        int i = 0;
        User user = (User) session.getAttribute("user");
        Question question = questionService.getById(id);
        String path1 = null, path2 = null, path3 = " ";
        String fileName1 = null, fileName2 = null, fileName = null;
        for (MultipartFile file : files) {
            fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            System.out.println(fileName + "-->" + size);
            if (i == 0) {
                path1 = paths + "\\files";
                fileName1 = fileName;
            } else {
                path2 = paths + "\\images";
                fileName2 = fileName;
                path3 = "../data//images//" + fileName2;
            }

            if (file.isEmpty()) {
                return "false";
            } else {
                File dest;
                if (i == 0) {
                    dest = new File(path1 + "/" + fileName1);
                } else {
                    dest = new File(path2 + "/" + fileName2);
                }
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "false";
                }
            }
            i++;
        }
        user.setAvatarUrl(path3);
        userService.createOrUpdate(user);
        question.setId(id);
        question.setFileUrl(path1);
        question.setFileName(fileName1);
        questionService.createOrUpdate(question);
        return "上传成功！";
    }

    //下载
    @ResponseBody
    @GetMapping("/commodifydownload")
    public String materialDownLoad(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(name = "id", required = true) Long id) throws IOException {
        Question questionFiles = questionService.getById(id);
        String filepath = questionFiles.getFileUrl()+"//"+questionFiles.getFileName();
        System.out.println(filepath);
        String fileName = questionFiles.getFileName();
        System.out.println(fileName);
        if (fileName != null) {
            File file = new File(filepath);
            if (file.exists()) {
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                System.out.println("1");
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    System.out.println("2");
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "下载成功！";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "下载失败！";
    }
}


