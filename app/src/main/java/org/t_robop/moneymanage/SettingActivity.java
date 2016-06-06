package org.t_robop.moneymanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    int money;
    int positionDate;

    EditText Tv;
    Button plus;
    Button minus;
    Button end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Tv=(EditText) findViewById(R.id.totalMoney) ;

        //Buttonオブジェクトplus取得
        plus=(Button)findViewById(R.id.plus);
        //Buttonオブジェクトminus取得
        minus=(Button)findViewById(R.id.minus);
        //Buttonオブジェクトend取得
        end=(Button)findViewById(R.id.end);

        Intent intent=getIntent();
        money=intent.getIntExtra("money",0);
        positionDate=intent.getIntExtra("position",0);

        //TODO 文字列を出力するのにintが入ってた
        Tv.setText(String.valueOf(money));
    }

    public void plus()
    {
        if(money<0)
        {
            money=money*-1;
            Tv.setText(money);
        }
    }

    public void minus()
    {
        if(money>0)
        {
            money=money*-1;
            Tv.setText(money);
        }
    }

    public void end()
    {
        Intent intent=new Intent(SettingActivity.this,MainActivity.class);
        intent.putExtra("Chmoney",money);
        intent.putExtra("Chposition",positionDate);
        startActivity(intent);
    }
}
