package android.s305089.gameoflife.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.s305089.gameoflife.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.WriterException;
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
        Intent startGameActivity = new Intent(this, SimulationActivity.class);

        try {
            qrCode = Encoder.encode(editTexttoQR.getText().toString(), ErrorCorrectionLevel.Q);

        } catch (WriterException e) {
            System.out.println("There was an error converting the text to QR");
            e.printStackTrace();
        }

        startGameActivity.putExtra("qrGameBoard", qrCode.getMatrix().getArray());
        startActivity(startGameActivity);


    }
}
