package com.akingyin.qrcodesimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.akingyin.qrcscanlib.core.QRCodeView;
import com.akingyin.qrcscanlib.zxing.ZXingView;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/8/15 18:11
 * @ Version V1.0
 */
public class ScanActivity extends AppCompatActivity   implements QRCodeView.Delegate {

  @BindView(R.id.zxingview) ZXingView zxingview;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.start_spot) TextView startSpot;
  @BindView(R.id.stop_spot) TextView stopSpot;
  @BindView(R.id.show_rect) TextView showRect;
  @BindView(R.id.hidden_rect) TextView hiddenRect;
  @BindView(R.id.start_spot_showrect) TextView startSpotShowrect;
  @BindView(R.id.stop_spot_hiddenrect) TextView stopSpotHiddenrect;
  @BindView(R.id.start_preview) TextView startPreview;
  @BindView(R.id.stop_preview) TextView stopPreview;
  @BindView(R.id.scan_barcode) TextView scanBarcode;
  @BindView(R.id.scan_qrcode) TextView scanQrcode;
  @BindView(R.id.open_flashlight) TextView openFlashlight;
  @BindView(R.id.close_flashlight) TextView closeFlashlight;
  @BindView(R.id.choose_qrcde_from_gallery) TextView chooseQrcdeFromGallery;


  private static final String TAG = "QRC";
  private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scandemo);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    zxingview.setDelegate(this);

  }


  @OnClick(R.id.start_spot)
   public   void  start_spot(){
     zxingview.startSpot();
   }


  @OnClick(R.id.stop_spot)
  public   void   stopSpot(){
    zxingview.stopSpot();
  }

  @Override
  protected void onStart() {
    super.onStart();
    zxingview.startCamera();
    //        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
  }

  @Override
  protected void onStop() {
    zxingview.stopCamera();
    super.onStop();
  }

  @Override protected void onDestroy() {
    zxingview.onDestroy();
    super.onDestroy();

  }

  @Override public void onScanQRCodeSuccess(String result) {
    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
  }

  @Override public void onScanQRCodeOpenCameraError() {

  }
}
