package android.s305089.gameoflife;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by s305089 on 04.03.2016.
 */
public class MainActivity extends Activity implements Serializable {

    private Button btnToQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnToQRCode = (Button) findViewById(R.id.btnToQRCode);
    }

    public void textToQR(View view){

    }
}
