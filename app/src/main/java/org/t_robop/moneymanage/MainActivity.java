package org.t_robop.moneymanage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;

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

    //EditText用変数群
    View viewV;
    View viewList;
    LayoutInflater inflater;
    EditText editText;
    EditText editText_list;

    //Arrayだと幅が広がります
    ArrayList<String> strList = null;

    //ListViewの場所確認用変数
    int positionDate=0;

    //全額用変数
    int total=0;

    //使用額用変数
    int cost=0;

    //DiaLog宣言
    private AlertDialog Listdialog;
    private AlertDialog Adddialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActiveAndroid.initialize(getApplication());



        ///////////////各宣言///////////////

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

        //DiaLog用設定
        inflater = LayoutInflater.from(MainActivity.this);
        viewV = inflater.inflate(R.layout.dialog_edit, null);
        viewList = inflater.inflate(R.layout.listdialog_edit, null);
        editText = (EditText)viewV.findViewById(R.id.editText1);
        editText_list = (EditText)viewList.findViewById(R.id.editText2);

        //////////////////////////////




        ///////////////listタップしたとき///////////////

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //タップ時の処理

                positionDate=position;//positionデータをpositionDateへ

                //ListDialog設定
                ListDiaLogSettings();
                Listdialog.show();//DiaLog表示
            }
        });

        //////////////////////////////




        //ArrayAdapterオブジェクト生成
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);



        ///////////////「追加」ボタン生成///////////////

        //クリックイベントの通知先指定
        btn.setOnClickListener(new View.OnClickListener() {

            //クリックイベント
            @Override
            public void onClick(View v) {
                //クリック時の処理

                //追加用DiaLog設定呼び出し
                AddDiaLogSettings();
                //追加用DiaLog表示
                Adddialog.show();
            }
        });

        //////////////////////////////



        //Adapterのセット
        listView.setAdapter(adapter);

    }



    public void cle(View v)
    {
        total=0;//合計値を0に

        cost=0;//コストを0に

        strList.clear();//list全消し

        AdapterReload(adapter, strList);//adapter更新

        totalView.setText(String.valueOf(total));//totalを出力

        costView.setText(String.valueOf(cost));//costの出力
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



    //Listタップ時のDiaLog設定
    public void ListDiaLogSettings()
    {

        editText_list.getEditableText().clear();//editTextの初期化

        editText_list.setInputType( InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);//editTextの入力を数値のみに




        ///////////////Listdialog作成///////////////

        if(Listdialog == null) //Listdialogが作成されていない時
        {

            Listdialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("金額入力")//DiaLogタイトル
                    .setView(viewList)//View指定

                    //DiaLog内の決定を押した時の処理
                    .setPositiveButton("決定", new DialogInterface.OnClickListener() //ボタン作成
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //クリック時の処理

                            //金額設定用変数
                            int money;

                            //入力値取得用変数
                            String text;

                            // エディットテキストのテキストを全選択します
                            editText_list.selectAll();

                            //editTextに何も入力されてない時
                            if (editText_list.getText().toString().equals("")) {
                                text = "0";//0が入る
                            } else {
                                // エディットテキストのテキストを取得します
                                text = editText_list.getText().toString();
                            }

                            money = Integer.valueOf(text);//入力値を数値に変換してmoneyに代入


                            strList.set(positionDate, String.valueOf(money));//strListのタップされた場所（position管理）に入力された物を入れる

                            AdapterReload(adapter, strList);//adapter更新

                            listView.setAdapter(adapter);//listViewにadapterを出力


                            //計算用変数の初期化
                            total = 0;
                            cost = 0;

                            for (int i = 0; i < strList.size(); i++)//現在のリスト数だけループさせたい
                            {
                                int num = Integer.parseInt(strList.get(i));//リストの内容をint型に変えてnumに
                                total = total + num;//totalに加算

                                if (num < 0) //コストのみ加算
                                {
                                    cost = cost + num;
                                }
                            }
                            cost = cost * -1;//コストを通常表示に

                            totalView.setText(String.valueOf(total));//totalを出力

                            costView.setText(String.valueOf(cost));//costを出力
                        }
                    })
                    .create();//初回ListDiaLog制作
        }

        //////////////////////////////

    }



    //追加時のAddDiaLog設定
    public void AddDiaLogSettings()
    {

        editText.getEditableText().clear();//editTextの初期化

        editText.setInputType( InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);//editTextの入力を数値のみに




        ///////////////Adddialog作成///////////////

        if(Adddialog == null)//Adddialogが作成されていない時
        {


            Adddialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("金額入力")//DiaLogタイトル
                    .setView(viewV)//View指定
                    //DiaLog内の決定を押した時の処理
                    .setPositiveButton("決定", new DialogInterface.OnClickListener() //ボタン作成
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //クリック時の処理

                            //金額設定用変数
                            int money;

                            //入力値取得用変数
                            String text;

                            // エディットテキストのテキストを全選択します
                            editText.selectAll();

                            //editTextに何も入力されてない時
                            if (editText.getText().toString().equals("")) {
                                text = "0";//0が入る
                            } else {
                                // エディットテキストのテキストを取得します
                                text = editText.getText().toString();
                            }

                            money = Integer.valueOf(text);//入力値を数値に変換してmoneyに代入

                            //要素追加
                            //リストとadaptorに入力値を入れる
                            strList.add(String.valueOf(money));
                            adapter.add(String.valueOf(money));

                            //adapter更新
                            adapter.notifyDataSetChanged();

                            //計算用変数の初期化
                            total = 0;
                            cost = 0;

                            for (int i = 0; i < strList.size(); i++)//現在のリスト数だけループさせたい
                            {
                                int num = Integer.parseInt(strList.get(i));//リストの内容をint型に変えてnumに
                                total = total + num;//totalに加算

                                if (num < 0)//コストのみ加算
                                {
                                    cost = cost + num;
                                }
                            }
                            cost = cost * -1;//コストの通常表示

                            totalView.setText(String.valueOf(total));//totalを出力

                            costView.setText(String.valueOf(cost));//costを出力
                        }
                    })
                    .create();//初回AddDiaLog制作
        }

        //////////////////////////////

    }



}