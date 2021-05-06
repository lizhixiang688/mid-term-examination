package com.example.timecube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_id,edit_password;
    private String id,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View decorview =getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        link();

    }
    private void link(){
        edit_id=(EditText)findViewById(R.id.edittext_login_ID);
        edit_password=(EditText)findViewById(R.id.edittext_login_password);
    }

    public void goregister(View view) {
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void Login(View view) {
        id=edit_id.getText().toString();
        password=edit_password.getText().toString();
        SharedPreferences pref=getSharedPreferences("TimeCube",MODE_PRIVATE);
        String realPassword=pref.getString("password","");
        String realid=pref.getString("id","");
        if(id.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else if(password.equals(realPassword)&&id.equals(realid)){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"密码或账号错误",Toast.LENGTH_SHORT).show();
        }
    }
}