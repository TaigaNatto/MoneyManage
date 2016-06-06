package org.t_robop.moneymanage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    //Arrayだと幅が広がります
    ArrayAdapter<String> adapter;

    //全額の表示
    TextView totalView;
    TextView costView;
    ListView listView;
    Button btn;
    Button cle;

    //Arrayだと幅が広がります
    ArrayList<String> strList = null;

    //金額設定用変数
    int money=0;

    //ListViewの場所確認用変数
    int positionDate=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全額用変数
        int total=0;

        //設定画面から入力された金額（Chmoney）と設定していた物の番号（Chposition）を貰う
        Intent intent=getIntent();
        money=intent.getIntExtra("Chmoney",0);
        positionDate=intent.getIntExtra("Chposition",0);

        //
        setContentView(R.layout.activity_main);

        //
        strList = new ArrayList<String>();

        //TextViewオブジェクト取得
        totalView=(TextView)findViewById(R.id.totalMoney);
        //TextViewオブジェクト取得
        costView=(TextView)findViewById(R.id.cost);
        //Buttonオブジェクトbtn取得
        btn=(Button)findViewById(R.id.btn);
        //Buttonオブジェクトcle取得
        cle=(Button)findViewById(R.id.cle);
        //ListViewオブジェクトの取得
        listView=(ListView)findViewById(R.id.list_view);


        //listタップしたとき
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ここに処理を書く

//               TextView tV=(TextView)view;//tVにタップした場所のviewを入れる
//                tV.setText("new");//子に出力

                //strList.set(position, String.valueOf(money));//strListのタップされた場所（position管理）にtestを入れる/////////

                //adapter更新
                //AdapterReload(adapter,strList);/////////

                //adapter.set
                //listView.setAdapter(adapter);//listViewにadapterを出力//////////

                positionDate=position;//positionデータをpositionDateへ


                //設定画面へmoneyとpositionDateを移動
                Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                intent.putExtra("money",money);
                intent.putExtra("position",positionDate);
                startActivity(intent);


            }
        });


        //ArrayAdapterオブジェクト生成
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //クリックイベントの通知先指定
        btn.setOnClickListener(new View.OnClickListener() {

            //クリックイベント
            @Override
            public void onClick(View v) {
                //要素追加
                addStringData();
            }
        });
        //Adapterのセット
        listView.setAdapter(adapter);


        for (int i = 0;i<strList.size();i++)//現在のリスト数だけループさせたい
        {
            int num= Integer.parseInt(strList.get(i));//リストの内容をint型に変えてnumに
            total=total+num;//totalに加算
        }

        //TODO 文字列を出力するのにintが入ってた
        totalView.setText(String.valueOf(total));//totalを出力

        //TODO Buttonをおした時の処理じゃないのに、おした時のことを書いてあって不明
//        if(strList.size()>0) {
//
//        }
//        else {
//            strList.set(positionDate, String.valueOf(money));//strListのタップされた場所（position管理）に入力された物を入れる
//
//            AdapterReload(adapter, strList);//adapter更新
//
//            listView.setAdapter(adapter);//listViewにadapterを出力
//        }

    }
    //要素追加処理
    private void addStringData(){

        //EditText(テキスト)を取得し、アダプタとリストに追加
//        strList.add(edit.getText().toString());
//        adapter.add(edit.getText().toString());

        //リストとadaptorに０を入れる
        strList.add("0");
        adapter.add("0");

        //adapter更新
        adapter.notifyDataSetChanged();
    }

    //adaptor更新
    public void AdapterReload(ArrayAdapter<String> x,ArrayList<String> y)
    {
        x.clear();//adapterを全消し

        for (int i = 0;i<y.size();i++)//strListの数だけループ
        {
            x.add(y.get(i));//一つ一つ作り直す（さっきpositionで指定した場所にはtestが入ってるので、それも含めて作られる）
        }

    }


}