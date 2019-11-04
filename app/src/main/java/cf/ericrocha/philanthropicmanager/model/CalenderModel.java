package cf.ericrocha.philanthropicmanager.model;

import java.util.Date;

public class CalenderModel {
    public String title;
    public String extra;
    public Date date;
    public String desc;
    public Integer cor;
    public Integer id;



    public CalenderModel(){
    }

    public CalenderModel(Integer id , String title, String extra, Date date, String desc, Integer cor ) {
        this.id = id;
        this.title = title;
        this.extra = extra;
        this.date = date;
        this.desc = desc;
        this.cor = cor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCor() {
        return cor;
    }

    public void setCor(Integer cor) {
        this.cor = cor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
