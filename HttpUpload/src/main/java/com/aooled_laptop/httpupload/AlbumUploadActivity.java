package com.aooled_laptop.httpupload;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertController;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aooled_laptop.httpupload.task.FileBinary;
import com.aooled_laptop.httpupload.task.HttpListener;
import com.aooled_laptop.httpupload.task.Request;
import com.aooled_laptop.httpupload.task.RequestExecutor;
import com.aooled_laptop.httpupload.task.RequestMethod;
import com.aooled_laptop.httpupload.task.Response;
import com.aooled_laptop.httpupload.task.StringRequest;
import com.aooled_laptop.httpupload.util.Constants;
import com.aooled_laptop.httpupload.util.Logger;
import com.aooled_laptop.httpupload.util.UriToFilePath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumUploadActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSelectImage, mBtnUploadImage;
//    private AlertController.RecycleListView mRvContent;

    private List<String> mImagePathList;
    private String mImagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_upload);
        mBtnSelectImage = findViewById(R.id.btn_select_image);
        mBtnUploadImage = findViewById(R.id.btn_upload);
        mBtnSelectImage.setOnClickListener(this);
        mBtnUploadImage.setOnClickListener(this);

    }

    private void uploadImage(){
        if (mImagePathList == null || mImagePathList.size() <= 0)
            Toast.makeText(this, "还没有选择图片", Toast.LENGTH_LONG).show();
        else{
            Request<String> stringRequest = new StringRequest(Constants.URL_UPLOAD, RequestMethod.POST);
            for (String s : mImagePathList){
                stringRequest.add("image", new FileBinary(new File(s)));
                Logger.i(s);
            }
//            stringRequest.add("image", new FileBinary(new File(mImagePath)));
            RequestExecutor.INTANCE.execute(stringRequest, new HttpListener() {
                @Override
                public void onSucceed(Response response) {
                    Logger.i("Activity 接受到的响应码:" + response.getResponseCode());

                    String str = (String) response.get();
                    Logger.i("Activity 接受到的结果:" + str);
                    Toast.makeText(AlbumUploadActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailed(Exception e) {
                    Toast.makeText(AlbumUploadActivity.this, "上传失败", Toast.LENGTH_LONG).show();
                }
            });
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100){
            if (resultCode == RESULT_OK ){
                mImagePathList = new ArrayList<String>();
                String str = UriToFilePath.handleImageOnKitkat(this,data);
                mImagePathList.add(str);

//                mImagePathList = UriToFilePath.parseResult(data);
                if(mImagePathList.size() > 0)
                    Toast.makeText(AlbumUploadActivity.this, "选择图片成功", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AlbumUploadActivity.this, "选择图片失败", Toast.LENGTH_LONG).show();
//                    Logger.i("size() <= 0");


//                mImagePath = UriToFilePath.getFilePathByUri(this,data.getData());
//                Logger.i(mImagePath);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSelectImage){
            Intent albumIntent= new Intent(Intent.ACTION_PICK, null);
            albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            this.startActivityForResult(albumIntent, 100);
        } else if (v == mBtnUploadImage){
            Logger.i("mBtnUploadImage");
            uploadImage();
        }

    }
}
