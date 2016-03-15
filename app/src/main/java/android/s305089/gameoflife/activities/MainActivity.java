package android.s305089.gameoflife.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.s305089.gameoflife.R;
import android.s305089.gameoflife.board.GameBoard;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by s305089 on 04.03.2016.
 */
public class MainActivity extends Activity implements Serializable {

    private Button btnToQRCode;
    private EditText editTexttoQR;
    private QRCode qrCode;
    private Intent intent;


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

        GameBoard gameBoard = new GameBoard(qrCode.getMatrix().getArray());

        try(FileOutputStream outputStream = new FileOutputStream("gameboard.board")){
            ObjectOutputStream writeObject = new ObjectOutputStream( outputStream);

            writeObject.writeObject(gameBoard);

            writeObject.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        intent = new Intent(this, GameActivity.class);
        intent.putExtra("gameboard", gameBoard);
        startActivity(intent);

    }
}
