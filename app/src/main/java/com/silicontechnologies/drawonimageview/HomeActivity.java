package com.silicontechnologies.drawonimageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.imageview)
    ImageView imageView;
    @BindView(R.id.save_image)
    Button saveImage;
    @BindView(R.id.action_camera)
    Button actionCamera;
    @BindView(R.id.action_gallery)
    Button actionGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    //    Button action
    @OnClick({R.id.action_gallery,
            R.id.action_camera,
            R.id.save_image})
    public void OnActionClick(View view) {
        switch (view.getId()) {
            case R.id.action_camera:
                break;
            case R.id.action_gallery:
                break;
            case R.id.save_image:
                break;
        }
    }
}
