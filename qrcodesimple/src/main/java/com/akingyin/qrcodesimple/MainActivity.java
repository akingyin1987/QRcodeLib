package com.akingyin.qrcodesimple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.akingyin.qrcodelib.QRCode;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/8/13 17:37
 * @ Version V1.0
 */
public class MainActivity extends AppCompatActivity {


  @BindView(R.id.iv_orc) ImageView ivOrc;
  @BindView(R.id.edit_name) EditText editName;
  @BindView(R.id.btn_orc) Button btnOrc;
  @BindView(R.id.btn_scan) Button btnScan;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    ButterKnife.bind(this);


  }

  @OnClick(R.id.btn_orc)
  public  void  btn_orc(){
    String demo = editName.getText().toString().trim();
    Bitmap bitmap =
        QRCode.from(TextUtils.isEmpty(demo) ? "name" : demo).withSize(300, 300).bitmap();
    ivOrc.setImageBitmap(bitmap);
  }

  @OnClick(R.id.btn_scan)
  public  void  btn_scan(){
    Intent   intent = new Intent(this,ScanActivity.class);
    startActivity(intent);
  }
}
