package com.svop.View;

import org.springframework.web.multipart.MultipartFile;

public class AircompanyView {
    private Integer id;
    private String longName;
    private String shortName;
    private MultipartFile file;

     public AircompanyView(){}
    public AircompanyView(Integer id, String longName, String shortName, MultipartFile file, String isFileLoad) {
        this.id = id;
        this.longName = longName;
        this.shortName = shortName;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    @Override
    public String toString() {
        return "AircompanyView{" +
                "id=" + id +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", file=" + file.getSize()+
                ", file=" + file.getOriginalFilename()+
                '}';
    }
}
