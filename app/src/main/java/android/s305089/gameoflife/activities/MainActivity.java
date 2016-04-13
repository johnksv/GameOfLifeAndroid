package android.s305089.gameoflife.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.s305089.gameoflife.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.Serializable;

/**
 * Created by s305089 on 04.03.2016.
 */
public class MainActivity extends Activity implements Serializable {
    private static final int IMAGE_CAPTURE_REQUEST_CODE = 100;
    private Button btnToQRCode;
    private EditText editTexttoQR;
    private QRCode qrCode;
    private ImageView imgViewCamCapture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnToQRCode = (Button) findViewById(R.id.btnToQRCode);
        editTexttoQR = (EditText) findViewById(R.id.editTextToQR);
    }

    public void textToQR(View view) {
        Intent startGameActivity = new Intent(this, GameActivity.class);

        try {
            qrCode = Encoder.encode(editTexttoQR.getText().toString(), ErrorCorrectionLevel.Q);

        } catch (WriterException e) {
            System.out.println("There was an error converting the text to QR");
            e.printStackTrace();
        }

        //TODO: REMOVE COMMENT Serilaized Board first, but found out this was easier.
        startGameActivity.putExtra("qrGameBoard", qrCode.getMatrix().getArray());
        startActivity(startGameActivity);


    }

    public void QRFromCam(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Good practice to check that you are responding to correct intent
        if(requestCode == IMAGE_CAPTURE_REQUEST_CODE ){
            if (resultCode == Activity.RESULT_OK){
                Bitmap image = (Bitmap)  data.getExtras().get("data");
                setContentView(R.layout.activity_main_qrfromcam);
                imgViewCamCapture = (ImageView) findViewById(R.id.imgViewCamCapture);
                imgViewCamCapture.setImageBitmap(image);

                QRCodeReader qrReader = new QRCodeReader();

            }
        }
    }
}
