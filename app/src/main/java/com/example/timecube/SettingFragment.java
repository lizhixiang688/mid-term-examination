package com.example.timecube;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;


public class SettingFragment extends Fragment {
   private Button button;
    private TextView txt_user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_user=(TextView)getActivity().findViewById(R.id.txt_user);
        SharedPreferences pref=getActivity().getSharedPreferences("TimeCube",Context.MODE_PRIVATE);
        String name=pref.getString("username","");
        if(name.isEmpty()){
            txt_user.setText("请输入名字");
        }else {
            txt_user.setText(name);
        }
        txt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=new EditText(getContext());
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("请输入名字");
                dialog.setView(editText);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String input=editText.getText().toString();
                        if(input.isEmpty()){
                            Toast.makeText(getActivity(),"名字不能为空",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            SharedPreferences.Editor editor=getActivity().getSharedPreferences("TimeCube",Context.MODE_PRIVATE).edit();
                            editor.putString("username",input);
                            editor.apply();
                            txt_user.setText(input);
                        }
                    }
                });
                dialog.show();
            }
        });

        button=(Button)getActivity().findViewById(R.id.login_out);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog builder = new AlertDialog.Builder(getContext())
                        .setTitle("确定退出当前账号吗？")
                        .setCancelable(false)
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor=getActivity().getSharedPreferences("TimeCube", Context.MODE_PRIVATE).edit();
                                editor.clear();
                                editor.commit();
                                DataSupport.deleteAll(Note.class);
                                Intent intent=new Intent(getActivity(),LoginActivity.class);
                                getActivity().startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                builder.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
                builder.getWindow().setBackgroundDrawableResource(android.R.color.white);
                try {
                    //通过反射获取mAlert对象
                    Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                    mAlert.setAccessible(true);
                    Object mAlertController = mAlert.get(builder);
                    //获取mTitleView并设置大小颜色
                    Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                    mTitle.setAccessible(true);
                    TextView mTitleView = (TextView) mTitle.get(mAlertController);
                    mTitleView.setTextSize(15);
                    mTitleView.setTextColor(Color.BLACK);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}