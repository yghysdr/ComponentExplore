package com.github.yghysdr.component.login;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yghysdr on 2018/12/6.
 */
public class Login {

    public Login() {
    }

    public void fun(Object o) {
        Toast.makeText((Context) o, "登入成功", Toast.LENGTH_SHORT).show();
        System.out.println("恭喜登入成功");
    }
}
