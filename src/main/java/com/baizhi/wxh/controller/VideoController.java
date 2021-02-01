package com.baizhi.wxh.controller;

import com.baizhi.wxh.entity.Video;
import com.baizhi.wxh.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @RequestMapping("upload")
    @ResponseBody
    public void upload(MultipartFile videoPath, String id, HttpSession session){
        try {
            String realPath = session.getServletContext().getRealPath("/upload/video");
            System.out.println(realPath);
            File dir = new File(realPath);
            if(!dir.exists())
                dir.mkdir();

            String fileName = UUID.randomUUID().toString() + videoPath.getOriginalFilename();
            File NewFile = new File(realPath+"/"+fileName);
            videoPath.transferTo(NewFile);

            Video video = new Video();
            video.setId(id);
            video.setVideoPath(fileName);
            videoService.modify(video);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("option")
    @ResponseBody
    public String option(String oper,Video video,HttpSession session){
        try {
            if(oper.equals("add")){
                return videoService.register(video);
            }else if(oper.equals("edit")){
                return videoService.modify(video);
            }else {
                String videoPath = videoService.remove(video.getId());
                String realPath = session.getServletContext().getRealPath("/upload/video");
                File delFile = new File(realPath, videoPath);
                if(delFile.exists())
                    delFile.delete();
                return "";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public void updateStatus(Video video){
        videoService.modifyStatus(video);
    }

    @RequestMapping("queryByPage")
    @ResponseBody
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        return videoService.queryByPage(page,rows);
    }
}
