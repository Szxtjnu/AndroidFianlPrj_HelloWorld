package com.andorid.finalprj.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andorid.finalprj.R;
import com.andorid.finalprj.util.DBHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    private Button buttonRegister;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DBHelper(this);

        editTextUsername = (EditText) findViewById(R.id.et_username);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        buttonRegister = (Button) findViewById(R.id.btn_register_r);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 检查密码是否匹配
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 检查用户名是否已存在
                if (dbHelper.isUsernameExist(username)) {
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 添加用户
                if (dbHelper.addUser(username, password)) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }

                editTextUsername.setText("");
                editTextPassword.setText("");
                editTextConfirmPassword.setText("");
            }
        });
    }
}