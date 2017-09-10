package com.li.food.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ggxueche.utils.CustomLinearLayoutManager;
import com.ggxueche.utils.FullyLinearLayoutManager;
import com.ggxueche.utils.GlideImgManager;
import com.li.food.R;
import com.li.food.bean.FoodDetailBean;
import com.li.food.bean.MaterialBean;
import com.li.food.bean.ProcessBean;
import com.li.food.global.Const;
import com.li.food.presenter.GourmetDetailsPresenter;
import com.li.food.view.adapter.MaterialAdapter;
import com.li.food.view.adapter.ProcessAdapter;
import com.li.food.view.inter.IGourmetDetailsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = Const.ACTIVITY_GOURMETDETAILS)
public class GourmetDetailsActivity extends BaseActivity<GourmetDetailsPresenter, IGourmetDetailsView> implements IGourmetDetailsView {
    @Autowired
    FoodDetailBean foodDetail;

    private List<MaterialBean> mMaterials = new ArrayList<>();
    private List<ProcessBean> mProcess = new ArrayList<>();
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
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.recyclerMaterial)
    RecyclerView recyclerMaterial;

    @BindView(R.id.recyclerProcess)
    RecyclerView recyclerProcess;

    MaterialAdapter materialAdapter;
    ProcessAdapter processAdapter;

    @Override
    public void initGourmetDetail() {
        if (null != foodDetail) {
            cookName.setText(foodDetail.getName());
            cookPeopleNum.setText("适合人数：" + foodDetail.getPeoplenum());
            cookPreTime.setText("准备时间：" + foodDetail.getPreparetime());
            cookIngTime.setText("烹饪时间：" + foodDetail.getCookingtime());
            cookContent.setText("描述：" + foodDetail.getContent().replaceAll("<br />",""));
            cookTag.setText("适合：" + foodDetail.getTag());
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gourmet_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        recyclerMaterial.setLayoutManager(new CustomLinearLayoutManager(this, false));
        recyclerProcess.setLayoutManager(new CustomLinearLayoutManager(this, false));
    }

    @Override
    protected void initData() {
        if (null != foodDetail.getMaterial()) {
            mMaterials.addAll(0, foodDetail.getMaterial());
        }
        if (null != foodDetail.getProcess()) {
            mProcess.addAll(0, foodDetail.getProcess());
        }

        materialAdapter = new MaterialAdapter(this, R.layout.list_material_item, mMaterials);
        recyclerMaterial.setAdapter(materialAdapter);

        processAdapter = new ProcessAdapter(this, R.layout.list_process_item, mProcess);
        recyclerProcess.setAdapter(processAdapter);
        initGourmetDetail();
        loadImage();
        scrollView.scrollTo(0,0);
    }

    @Override
    public void loadImage() {
        if (null != foodDetail) {
            GlideImgManager.getInstance().loadImageView(this, foodDetail.getPic(), cookPic);
        }
    }

}
