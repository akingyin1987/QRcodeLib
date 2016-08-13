package com.akingyin.qrcodesimple;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.akingyin.qrcodelib.QRCode;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/8/13 17:37
 * @ Version V1.0
 */
public class MainActivity  extends AppCompatActivity {

  ImageView  iv_orc;
  Button   btn_orc;
  EditText  edit_name;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    iv_orc = (ImageView)findViewById(R.id.iv_orc);
    btn_orc = (Button)findViewById(R.id.btn_orc);
    edit_name = (EditText)findViewById(R.id.edit_name);
    btn_orc.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String   demo = edit_name.getText().toString().trim();
        Bitmap  bitmap = QRCode.from(TextUtils.isEmpty(demo)?"name":demo)
            .withSize(300,300).bitmap();
        iv_orc.setImageBitmap(bitmap);
      }
    });
  }
}
