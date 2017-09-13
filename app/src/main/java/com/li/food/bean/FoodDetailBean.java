package com.li.food.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 菜谱详情
 * Created by lzf on 2017/8/5.
 */

public class FoodDetailBean implements Serializable {
    /*" "id": "5",
        "classid": "2",
        "name": "翡翠彩蔬卷",
        "peoplenum": "1-2人",
        "preparetime": "10分钟内",
        "cookingtime": "10分钟内",
        "content": "春天是为夏天做准备的刮油季，为了夏天能够美美的穿上漂亮的花裙子，让我们一起来狠狠的刮油吧。<br>这个色彩缤纷的彩蔬卷，低热量，高营养，是一道秀色可餐的减肥餐~",
        "pic": "http://api.jisuapi.com/recipe/upload/20160719/115138_19423.jpg",
        "tag": "减肥,咸香,宴请,抗氧化,抗衰老,私房菜,聚会","*/
    private String id;
    private String classid;
    private String name;
    private String peoplenum;
    private String preparetime;
    private String cookingtime;
    private String pic;
    private String content;
    private String tag;

    private List<MaterialBean> material;
    private List<ProcessBean> process;

    public List<MaterialBean> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialBean> material) {
        this.material = material;
    }

    public List<ProcessBean> getProcess() {
        return process;
    }

    public void setProcess(List<ProcessBean> process) {
        this.process = process;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeoplenum() {
        return peoplenum;
    }

    public void setPeoplenum(String peoplenum) {
        this.peoplenum = peoplenum;
    }

    public String getPreparetime() {
        return preparetime;
    }

    public void setPreparetime(String preparetime) {
        this.preparetime = preparetime;
    }

    public String getCookingtime() {
        return cookingtime;
    }

    public void setCookingtime(String cookingtime) {
        this.cookingtime = cookingtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


}
