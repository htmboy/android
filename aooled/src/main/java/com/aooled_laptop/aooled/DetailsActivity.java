package com.aooled_laptop.aooled;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
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
import com.aooled_laptop.aooled.task.HttpListener;
import com.aooled_laptop.aooled.task.Request;
import com.aooled_laptop.aooled.task.RequestExecutor;
import com.aooled_laptop.aooled.task.RequestMethod;
import com.aooled_laptop.aooled.task.Response;
import com.aooled_laptop.aooled.task.StringRequest;
import com.aooled_laptop.aooled.utils.Constants;
import com.aooled_laptop.aooled.utils.Logger;
import com.aooled_laptop.aooled.utils.TimestampUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    TextView orderNumber, isDistribution, isSpecialOffer, fillDate, isSimpleOrder, contractNumber, isConstruction, isBorrowData, goodsCount, isAssurance, method;
    TextView price, payer, alterAmount, isNoticeDelivery, sendTo, customer, contact, contactTel, recieptBank, isContainTax, deposit, assurance, assuranceDate, constructionAmount;
    TextView constructionAccount, tail, tailDate, contractAmount, isAlterReciept;
    LinearLayout linearLayout;
    JSONArray imageArray;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.orderlist_details);
        findViewById(R.id.back).setOnClickListener(this);
        initView();
        String str = getIntent().getExtras().getString("orderId");
        setData(str);

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

    public void setData(String id){
        Request<String> request = new StringRequest(Constants.URL_UPLOAD, RequestMethod.JSON);
        request.add("code", 22);
        request.add("orderId", id);
//        orders = new ArrayList<Order>();
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
                        new ImageAscynTask().start();
                        break;
                    default:
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

    private void loadImage(JSONArray jsonArray){
        for(int i = 0; i < jsonArray.length(); i++){
            ImageView imageView = new ImageView(this);

            try {
//                Logger.i(Constants.URL_IMAGE + "/storage/uploads/" + jsonArray.getJSONObject(i).optString("imageName"));
//                imageView.setMaxWidth(300);
//                imageView.setMaxHeight(900);
//                imageView.setScaleType(ImageView.ScaleType.CENTER);
//                imageView.setAdjustViewBounds(true);

                getImage(Constants.URL_IMAGE + "/storage/uploads/" + jsonArray.getJSONObject(i).optString("imageName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

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

    // 加载图片
    private void loadOriginalSize(ImageView img){
        String sdcard = Environment.getDownloadCacheDirectory().getPath();
        String filePath = sdcard + "/11.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        img.setImageBitmap(bitmap);
    }
    // 压缩图片
    private void testPicOptimize(ImageView img){
        String sdcard = Environment.getDownloadCacheDirectory().getPath();
        String filePath = sdcard + "/11.jpg";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int width = options.outWidth;
        options.inSampleSize = width / 200;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        img.setImageBitmap(bitmap);
    }

    private void testInBitmap(ImageView secondImg, Bitmap bitmap){
        String sdcard = Environment.getDownloadCacheDirectory().getPath();
        String filePath = sdcard + "/11.jpg";
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 第二张图片复用了第一张图片的内存
        options.inBitmap = bitmap;
        Bitmap mBitmap = BitmapFactory.decodeFile(filePath, options);
        secondImg.setImageBitmap(mBitmap);
    }

    class ImageAscynTask extends Thread {

        @Override
        public void run() {
            final List<Bitmap> bitmaps = new ArrayList<Bitmap>();
            for(int i = 0; i < imageArray.length(); i++){
                try {
//                    imageView.setImageBitmap(getImage(Constants.URL_IMAGE + "/storage/uploads/" + jsonArrays[0].getJSONObject(i).optString("imageName")));
                    bitmaps.add(i, getImage(Constants.URL_IMAGE + "/storage/uploads/" + imageArray.getJSONObject(i).optString("imageName")));
//                    imageView.setVisibility(View.VISIBLE);
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

