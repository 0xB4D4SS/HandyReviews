package com.oxb4d4ss.kursa4ik;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "MyLog";
    final String yaUrl = "https://market.yandex.ru/search?cvredirect=0&text=";
    Button scanButton;
    WebView webView;

    /**
     * Runs zxing barcode scanner
     */
    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureA.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning...");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                switch (result.getFormatName()) {
                    case "QR_CODE":
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(result.getContents());
                        break;
                    default:
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(yaUrl + result.getContents());
                        break;
                }
            }
            else {
                Toast.makeText(this, "No result!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanButton = findViewById(R.id.scanButton);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        scanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        scanCode();
    }
}