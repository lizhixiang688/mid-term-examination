package com.example.timecube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
       private EditText edit_id,edit_password;
       private String id,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View decorview =getWindow().getDecorView();
        decorview.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        link();

    }

    private void link(){
        edit_id=(EditText)findViewById(R.id.edittext_register_ID);
        edit_password=(EditText)findViewById(R.id.edittext_register_password);
    }

    public void Register(View view) {
        id=edit_id.getText().toString();
        password=edit_password.getText().toString();
        if(id.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences.Editor editor=getSharedPreferences("TimeCube",MODE_PRIVATE).edit();
            editor.putString("id",id);
            editor.putString("password",password);
            editor.apply();
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}