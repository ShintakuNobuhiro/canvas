package example.android.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class GameView extends View {

    //駅名
    String station[] = {"新青森","七戸十和田","八戸","いわて沼宮内","盛岡","新花巻","北上","水沢江刺","一ノ関","くりこま高原","古川","仙台","白石蔵王","福島","郡山","新白河","那須塩原","宇都宮","小山","大宮","上野","東京"};

    // 背景画像を格納する変数を宣言
    private Bitmap bgImage,bgImageEnd;

    // アニメーションのフレーム数
    int frameIndex = 0;

    private Bitmap train,board;
    private Paint paint = new Paint();

    int offset=0;
    int trainY = 728;
    int speed = 10;
    int cell = station.length-1; //進むマス

    // コンストラクタ
    public GameView(Context context) {
        super(context);
        // リソースオブジェクトを作成
        Resources res = this.getContext().getResources();

        // 背景画像をビットマップに変換して変数「bgImage」に入れる
        bgImage = BitmapFactory.decodeResource(res, R.drawable.background);
        bgImageEnd = BitmapFactory.decodeResource(res, R.drawable.background_end);
        // 画面(Canvas)サイズに応じて背景画像を拡大する
        bgImage = Bitmap.createScaledBitmap(bgImage, 1920, 1056, true);
        bgImageEnd = Bitmap.createScaledBitmap(bgImageEnd, 1920, 1056, true);
        //電車
        train = BitmapFactory.decodeResource(res, R.drawable.hayabusa);
        train = Bitmap.createScaledBitmap(train, 180, 90, true);
        //看板
        board = BitmapFactory.decodeResource(res, R.drawable.station_signboard);
        board = Bitmap.createScaledBitmap(board, 300, 250, true);
    }

    // スパークラス(継承元)の「onDraw」メソッドをオーバーライドする
    @Override
    public void onDraw(Canvas canvas){
        // 「playScene」メソッドを実行
        playScene(canvas);
        frameIndex++;
    }

    // 「playScene」メソッド
    public void playScene(Canvas canvas){
        int width = canvas.getWidth();

        int distance = cell * (width/4)-20;
        if(offset<distance)
            offset+=speed;//移動処理

        //背景描画処理
        for(int i=0;i<station.length/4;i++) {
            canvas.drawBitmap(bgImage, width * i - offset, 0, null);
        }
        canvas.drawBitmap(bgImage, width * (station.length/4) - (width/4)*(station.length%4+1) - offset, 0, null);
        canvas.drawBitmap(bgImageEnd, width * (station.length/4+1) - (width/4)*(station.length%4+1) - offset, 0, null);

        //各駅に付随するものの表示
        paint.setTextSize(40);
        for(int i=0;i<station.length;i++){
            //看板・駅名
            paint.setColor(Color.argb(255, 0, 0, 0));
            int pos = (width/4)*i-offset;
            canvas.drawBitmap(board, pos - 30, 800, paint);
            canvas.drawText(station[i], pos, 880, paint);

            //吹き出し
            paint.setColor(Color.argb(190, 255, 255, 255));
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            RectF balloon = new RectF(0, 0, 300, 200);
            balloon.offset(pos-15, 390);
            canvas.drawRoundRect(balloon, 10, 10, paint);
        }
/*        //電車振動
        if(frameIndex % 2 == 0)
            trainY -= 5;
        else
            trainY += 5;*/
        canvas.drawBitmap(train,20,trainY,null); //電車描画
    }
}
