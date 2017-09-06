package com.li.food.bean;

import java.io.Serializable;

/**
 * 食材
 * Created by lzf on 2017/8/8.
 */

public class MaterialBean implements Serializable {
    /*    "mname": "鸡蛋",
                "type": "0",
                "amount": "1个"*/
    private String mname;
    private String type;
    private String amount;

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
