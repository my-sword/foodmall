package com.xzb.dto;

import com.xzb.bean.Ad;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)//实体类与json互转的时候 属性值为null的不参与序列化（前端返回的数据有null则去除，可有为空串“” 或者 0 或者 []）
public class AdDto extends Ad {
    private String img;
    
    private MultipartFile imgFile;//这个类一般是用来接受前台传过来的文件

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }
    
}
