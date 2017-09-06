package com.li.food.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ggxueche.utils.GlideImgManager;
import com.li.food.R;
import com.li.food.bean.FoodDetailBean;
import com.li.food.global.Const;
import com.li.food.presenter.GourmetDetailsPresenter;
import com.li.food.view.inter.IGourmetDetailsView;

import butterknife.BindView;

@Route(path = Const.ACTIVITY_GOURMETDETAILS)
public class GourmetDetailsActivity extends BaseActivity<GourmetDetailsPresenter, IGourmetDetailsView> implements IGourmetDetailsView {
    @Autowired
    FoodDetailBean foodDetail;

    @BindView(R.id.cookName)
    TextView cookName;

    @BindView(R.id.cookPic)
    ImageView cookPic;

    @BindView(R.id.cookPeopleNum)
    TextView cookPeopleNum;

    @BindView(R.id.cookPreTime)
    TextView cookPreTime;


    @BindView(R.id.cookIngTime)
    TextView cookIngTime;

    @BindView(R.id.cookContent)
    TextView cookContent;

    @BindView(R.id.cookTag)
    TextView cookTag;

    @Override
    public void initGourmetDetail() {
        if (null != foodDetail) {
            cookName.setText(foodDetail.getName() + "id:" + foodDetail.getId());
            cookPeopleNum.setText("适合人数：" + foodDetail.getPeoplenum());
            cookPreTime.setText("准备时间：" + foodDetail.getPreparetime());
            cookIngTime.setText("烹饪时间：" + foodDetail.getCookingtime());
            cookContent.setText("描述：" + foodDetail.getContent());
            cookTag.setText("适合：" + foodDetail.getTag());

        }
        //TODO 测试
        presenter.aa();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gourmet_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        initGourmetDetail();
        loadImage();
    }

    @Override
    public void loadImage() {
        if (null != foodDetail) {
            GlideImgManager.getInstance().loadImageView(this, foodDetail.getPic(), cookPic);
        }
    }

}
