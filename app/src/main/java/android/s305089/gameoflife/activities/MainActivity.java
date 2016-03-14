package android.s305089.gameoflife.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.s305089.gameoflife.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.Serializable;

/**
 * Created by s305089 on 04.03.2016.
 */
public class MainActivity extends Activity implements Serializable {

    private Button btnToQRCode;
    private EditText editTexttoQR;
    private QRCode qrCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnToQRCode = (Button) findViewById(R.id.btnToQRCode);
        editTexttoQR = (EditText) findViewById(R.id.editTextToQR);

    }

    public void textToQR(View view) {

        try {
            qrCode = Encoder.encode(editTexttoQR.getText().toString(), ErrorCorrectionLevel.Q);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        for(byte[] b :qrCode.getMatrix().getArray() ){
            for(byte a : b){
                System.out.print(a);
            }
            System.out.println();
        }

    }
}
