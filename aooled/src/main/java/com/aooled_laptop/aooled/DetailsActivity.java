package com.aooled_laptop.aooled;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aooled_laptop.aooled.adapter.OrderListAdapter;
import com.aooled_laptop.aooled.error.ParseError;
import com.aooled_laptop.aooled.error.TimeoutError;
import com.aooled_laptop.aooled.error.URLError;
import com.aooled_laptop.aooled.error.UnknowHostError;
import com.aooled_laptop.aooled.model.Order;
import com.aooled_laptop.aooled.task.FileBinary;
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.utils.Constants;
import com.aooled_laptop.aooled.utils.Logger;
import com.aooled_laptop.aooled.utils.TimestampUtil;
import com.aooled_laptop.aooled.utils.UriToFilePath;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, MenuItem.OnMenuItemClickListener {
    private TextView orderNumber, isDistribution, isSpecialOffer, fillDate, isSimpleOrder, contractNumber, isConstruction, isBorrowData, goodsCount, isAssurance, method;
    private TextView price, payer, alterAmount, isNoticeDelivery, sendTo, customer, contact, contactTel, recieptBank, isContainTax, deposit, assurance, assuranceDate, constructionAmount;
    private TextView constructionAccount, tail, tailDate, contractAmount, isAlterReciept;
    private LinearLayout linearLayout;
    private JSONArray imageArray;
    private String orderStatus = "";
    private Handler handler = new Handler();
    private String id, imagePath;
    private String code;
    MenuItem uploadOrder, editOrder, reUploadOrder, uploadDelivery, reUploadDelivery, modifyOrder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist_details);
        Toolbar toolbar = findViewById(R.id.toolbar_return);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(this);

        initView();
        id = getIntent().getExtras().getString("orderId");
        code = getIntent().getExtras().getString("code");
        setData("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        uploadOrder = menu.findItem(R.id.uploadOrder);
        uploadOrder.setOnMenuItemClickListener(this);
        reUploadOrder = menu.findItem(R.id.reUploadOrder);
        uploadDelivery = menu.findItem(R.id.uploadDelivery);
        reUploadDelivery = menu.findItem(R.id.reUploadDelivery);
        editOrder = menu.findItem(R.id.editOrder);
        modifyOrder = menu.findItem(R.id.modifyOrder);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (orderStatus){
            case "14":
                uploadOrder.setVisible(true);
                editOrder.setVisible(true);
                break;
            case "16":
                reUploadOrder.setVisible(true);
                editOrder.setVisible(true);
                break;
            case "21":
                reUploadOrder.setVisible(true);
                editOrder.setVisible(true);
                break;
            case "22":
                reUploadOrder.setVisible(true);
                editOrder.setVisible(true);
                break;
            case "23":
                break;
            case "24":
                break;
            case "25":
                break;
            case "26":
                break;
            default:
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void initView(){
        linearLayout = findViewById(R.id.linearLayout);
        orderNumber = findViewById(R.id.orderNumber);
        isDistribution = findViewById(R.id.isDistribution);
        isSpecialOffer = findViewById(R.id.isSpecialOffer);
        fillDate = findViewById(R.id.fillDate);
        isSimpleOrder = findViewById(R.id.isSimpleOrder);
        contractNumber = findViewById(R.id.contractNumber);
        isConstruction = findViewById(R.id.isConstruction);
        isBorrowData = findViewById(R.id.isBorrowData);
        price = findViewById(R.id.price);
        goodsCount = findViewById(R.id.goodsCount);
        isAssurance = findViewById(R.id.isAssurance);
        method = findViewById(R.id.method);
        payer = findViewById(R.id.payer);
        isAlterReciept = findViewById(R.id.isAlterReciept);
        alterAmount = findViewById(R.id.alterAmount);
        isNoticeDelivery = findViewById(R.id.isNoticeDelivery);
        sendTo = findViewById(R.id.sendTo);


        customer = findViewById(R.id.customer);
        contact = findViewById(R.id.contact);
        contactTel = findViewById(R.id.contactTel);
        recieptBank = findViewById(R.id.recieptBank);

        isContainTax = findViewById(R.id.isContainTax);
        deposit = findViewById(R.id.deposit);
        assurance = findViewById(R.id.assurance);
        assuranceDate = findViewById(R.id.assuranceDate);

        constructionAmount = findViewById(R.id.constructionAmount);
        constructionAccount = findViewById(R.id.constructionAccount);
        tail = findViewById(R.id.tail);
        tailDate = findViewById(R.id.tailDate);
        contractAmount = findViewById(R.id.contractAmount);




    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public void setData(String mark){
        RequestMethod requestMethod;
        String requestAddress;
        if("14".equals(mark)) {
            requestMethod = RequestMethod.POST;
            requestAddress = Constants.URL_UPLOAD;
        }
        else {
            requestMethod = RequestMethod.JSON;
            requestAddress = Constants.URL_SUBMIT;
        }
        Request<String> request = new StringRequest(requestAddress, requestMethod);

        request.add("orderId", id);
        switch(mark){
            case "14":
                request.add("code", 32);
                request.add("image", new FileBinary(new File(imagePath)));
                request.add("mark", code);
                break;
            default:
                request.add("code", 22);
                break;
        }


        RequestExecutor.INTANCE.execute(request, new HttpListener<String>() {
            @Override
            public void onSucceed(Response<String> response) {
                JSONObject jsonObject = null;
                Logger.i("Activity 接受到的响应码:" + response.getResponseCode());
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
                    case 0: Toast.makeText(DetailsActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        JSONObject jsonObject1 = jsonObject.optJSONObject("data");
                        orderStatus = jsonObject1.optString("orderStatus");
                        orderNumber.setText(jsonObject1.optString("orderNumber"));
                        isDistribution.setText(jsonObject1.optString("isDistribution"));
                        isSpecialOffer.setText(jsonObject1.optString("isSpecialOffer"));
                        fillDate.setText(TimestampUtil.getCurrentTime(jsonObject1.optString("fillDate")));
                        isSimpleOrder.setText(jsonObject1.optString("isSimpleOrder"));
                        contractNumber.setText(jsonObject1.optString("contractNumber"));
                        isConstruction.setText(jsonObject1.optString("isConstruction"));
                        isBorrowData.setText(jsonObject1.optString("isBorrowData"));
                        goodsCount.setText(jsonObject1.optString("goodsCount"));
                        isAssurance.setText(jsonObject1.optString("isAssurance"));
                        method.setText(jsonObject1.optString("method"));
                        price.setText(jsonObject1.optString("price"));
                        payer.setText(jsonObject1.optString("payer"));

                        isAlterReciept.setText(jsonObject1.optString("isAlterReciept"));
                        alterAmount.setText(jsonObject1.optString("alterAmount"));
                        isNoticeDelivery.setText(jsonObject1.optString("isNoticeDelivery"));
                        sendTo.setText(jsonObject1.optString("sendTo"));
                        customer.setText(jsonObject1.optString("customer"));
                        contact.setText(jsonObject1.optString("contact"));
                        contactTel.setText(jsonObject1.optString("contactTel"));
                        recieptBank.setText(jsonObject1.optString("recieptBank"));
                        isContainTax.setText(jsonObject1.optString("isContainTax"));
                        deposit.setText(jsonObject1.optString("deposit"));
                        assurance.setText("0.00".equals(jsonObject1.optString("assurance")) ? "/" : jsonObject1.optString("assurance"));
                        assuranceDate.setText("0".equals(jsonObject1.optString("assuranceDate")) ? "/" : TimestampUtil.getCurrentTime(jsonObject1.optString("assuranceDate")));
                        constructionAmount.setText(jsonObject1.optString("constructionAmount"));
                        constructionAccount.setText(jsonObject1.optString("constructionAccount"));
                        tail.setText(jsonObject1.optString("tail"));
                        tailDate.setText("0".equals(jsonObject1.optString("tailDate")) ? "/" : TimestampUtil.getCurrentTime(jsonObject1.optString("tailDate")));
                        contractAmount.setText(jsonObject1.optString("contractAmount"));

                        imageArray = jsonObject.optJSONArray("image");
                        invalidateOptionsMenu();
                        new ImageAscynTask().start();
                        break;
                    case 2:
                        Toast.makeText(DetailsActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(DetailsActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
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

    private Bitmap getImage(String urlString){
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100){
            if (resultCode == RESULT_OK ){
                imagePath = UriToFilePath.handleImageOnKitkat(this,data);
                if(imagePath == null)
                    Toast.makeText(this, "选择图片失败", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(this, "选择图片成功", Toast.LENGTH_LONG).show();
                    setData("14");
                }
            }
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.uploadOrder:
                Intent albumIntent= new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                this.startActivityForResult(albumIntent, 100);
                break;
            case R.id.reUploadOrder:
                albumIntent= new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                this.startActivityForResult(albumIntent, 100);
                break;
            default:
                break;
        }
        return true;
    }


    class ImageAscynTask extends Thread {

        @Override
        public void run() {
            final List<Bitmap> bitmaps = new ArrayList<Bitmap>();
            for(int i = 0; i < imageArray.length(); i++){
                try {
                    bitmaps.add(i, getImage(Constants.URL_IMAGE + "/storage/uploads/" + imageArray.getJSONObject(i).optString("imageName")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < bitmaps.size(); i++){
                            View view = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.orderlist_details_image, null);

                            TextView imageTitle = view.findViewById(R.id.imageTitle);
                            imageTitle.setText("图片名称：" + imageArray.getJSONObject(i).optString("imageTitle"));

                            TextView imageDescript = view.findViewById(R.id.imageDescript);
                            imageDescript.setText("图片描述：" + imageArray.getJSONObject(i).optString("imageDescript"));

                            TextView imageDate = view.findViewById(R.id.imageDate);
                            imageDate.setText("上传日期：" + TimestampUtil.getCurrentTime(imageArray.getJSONObject(i).optString("imageDate")));

                            ImageView imageView = view.findViewById(R.id.image);
                            imageView.setImageBitmap(bitmaps.get(i));

                            linearLayout.addView(view);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}

