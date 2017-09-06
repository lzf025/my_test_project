package com.li.food.bean;

import java.io.Serializable;

/**
 * 加工步骤
 * Created by lzf on 2017/8/8.
 */

public class ProcessBean implements Serializable {

    private String pcontent;
    private String pic;

    public String getPcontent() {
        return pcontent;
    }

    public void setPcontent(String pcontent) {
        this.pcontent = pcontent;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
