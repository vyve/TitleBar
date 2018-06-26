package com.wxc.titlebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wxc.library.TitleBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TitleBar titleBar = findViewById(R.id.titleBar);

        titleBar.setTitleBackgroundDrawable(R.mipmap.timg);

        View contentLayout = titleBar.getContentLayout();
        contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"哈哈",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
