package com.aooled_laptop.aooled;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.task.FileBinary;
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.utils.Constants;
import com.aooled_laptop.aooled.utils.Logger;
import com.aooled_laptop.aooled.utils.StringUtils;
import com.aooled_laptop.aooled.utils.TimestampUtil;
import com.aooled_laptop.aooled.utils.UriToFilePath;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UploadDeliveryActivity extends AppCompatActivity implements View.OnClickListener {
    private String orderId, code, imagePath;
    private EditText deliveryNumber;
    private ImageView deliveryImage;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar_return);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(this);
        orderId = getIntent().getExtras().getString("orderId");
        code = getIntent().getExtras().getString("code");
        deliveryNumber = findViewById(R.id.deliveryNumber);
        findViewById(R.id.deliverySubmit).setOnClickListener(this);
        deliveryImage = findViewById(R.id.deliveryImage);
        deliveryImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case -1:
                finish();
                break;
            case R.id.deliveryImage:
                Intent albumIntent= new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                this.startActivityForResult(albumIntent, 14);
                break;
            case R.id.deliverySubmit:
                deliverySubmit();
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ){
            imagePath = UriToFilePath.handleImageOnKitkat(this,data);
            if(imagePath == null)
                Toast.makeText(this, "选择图片失败", Toast.LENGTH_SHORT).show();
            else
                showPicOptimize();
        }
    }

    private void deliverySubmit(){
        if(StringUtils.isEmpty(imagePath)){
            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if(StringUtils.isEmpty(deliveryNumber.getText().toString().trim())){
            Toast.makeText(this, "发货数量不能为空", Toast.LENGTH_SHORT).show();
            deliveryNumber.requestFocus();
            return;
        }
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.POST);
        request.add("orderId", orderId);
        request.add("code", 35);
        request.add("deliveryNumber", deliveryNumber.getText().toString().trim());
        request.add("image", new FileBinary(new File(imagePath)));
        request.add("mark", code);
        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {
                JSONObject jsonObject = null;
//                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
                String str = response.get();
                Logger.i("Activity 接受到的结果:" + str);
                int code = 0;
                try {
                    jsonObject = new JSONObject(str);
                    code = (int) jsonObject.opt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (code){
                    case 0: Toast.makeText(UploadDeliveryActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(UploadDeliveryActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(UploadDeliveryActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onFailed(Exception e) {
                if (e instanceof ParseError){
                    // 数据解析异常
                    Logger.d("数据解析异常");
                }else if (e instanceof TimeoutError){
                    // 超时
                    Logger.d("超时");
                }else if (e instanceof UnknowHostError){
                    // 没有找到服务器
                    Logger.d("没有找到服务器");
                }else if (e instanceof URLError){
                    // URL格式错误
                    Logger.d("URL格式错误");
                }

            }
        });
    }
    // 加载图片
//    private void loadOriginalSize(ImageView img){
//        String sdcard = Environment.getDownloadCacheDirectory().getPath();
//        String filePath = sdcard + "/11.jpg";
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//        img.setImageBitmap(bitmap);
//    }
    // 压缩图片
    private void showPicOptimize(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
//        int width = options.outWidth;
//        options.inSampleSize = width / 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        deliveryImage.setImageBitmap(bitmap);
    }

//    private void testInBitmap(ImageView secondImg, Bitmap bitmap){
//        String sdcard = Environment.getDownloadCacheDirectory().getPath();
//        String filePath = sdcard + "/11.jpg";
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        // 第二张图片复用了第一张图片的内存
//        options.inBitmap = bitmap;
//        Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);
//        secondImg.setImageBitmap(mBitmap);
//    }


}
