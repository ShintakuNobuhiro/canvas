package example.android.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    //駅名
    String station[] = {"新青森","七戸十和田","八戸","二戸","いわて沼宮内","盛岡","新花巻","北上","水沢江刺","一ノ関","くりこま高原","古川","仙台","白石蔵王","福島","郡山","新白河","那須塩原","宇都宮","小山","大宮","上野","東京"};
    String station_read[] = {"しんあおもり","しちのへとわだ","はちのへ","にのへ","ぬまくない","もりおか","しんはなまき","きたかみ","みずさわえさし","いちのせき","こうげん","ふるかわ","せんだい","しらいしざおう","ふくしま","こおりやま","しんしらかわ","なすしおばら","うつのみや","おやま","おおみや","うえの","とうきょう",};
    String badgeName[] ={"ねぶた","十和田市現代美術館","イカ","さくらんぼ","岩手銀河鉄道","冷麺","宮沢賢治","ごしょ芋","南部鉄器","金色堂","いわな","ササニシキ","牛タン","樹氷","桃","あかべこ","白河そば","牛","餃子","遊園地","鉄道博物館","スカイツリー","東京駅"};

    // 背景画像を格納する変数を宣言
    private Bitmap bgImageStart,bgImage,bgImageEnd;

    // アニメーションのフレーム数
    int frameIndex = 0;
    int startFrame = 0;
    int stopFrame = 0;

    private Bitmap train,board;
    private Bitmap badge[] = new Bitmap[station.length];
    private Bitmap badgeOriginal[] = new Bitmap[station.length];
    private Paint paint = new Paint();

    int offset=-1920/2+150;
    int offset_int = offset;
    int trainY = 728;
    int badgeSize = 150;
    int speed = 10;
    int recent_cell; //前回マス
    int cell; //現在マス
    int imageCell = 2; //画像1枚のマス数
    boolean start = false;
    boolean back = false;
    Context pContext;

    // コンストラクタ
    public GameView(Context context,int rc,int c) {
        super(context);
        if(rc>=station.length-1)
            recent_cell = station.length-1;
        else
            recent_cell = rc;
        if(c>=station.length-1)
            cell = station.length-1;
        else
            cell = c;

        // リソースオブジェクトを作成
        Resources res = this.getContext().getResources();
        pContext = context;

        // 背景画像をビットマップに変換して変数「bgImage」に入れる
        bgImageStart = BitmapFactory.decodeResource(res, R.drawable.background_start);
        bgImage = BitmapFactory.decodeResource(res, R.drawable.background);
        bgImageEnd = BitmapFactory.decodeResource(res, R.drawable.background_end);
        // 画面(Canvas)サイズに応じて背景画像を拡大する
        bgImageStart = Bitmap.createScaledBitmap(bgImageStart, 1920, 1056, true);
        bgImage = Bitmap.createScaledBitmap(bgImage, 1920, 1056, true);
        bgImageEnd = Bitmap.createScaledBitmap(bgImageEnd, 1920, 1056, true);
        //電車
        train = BitmapFactory.decodeResource(res, R.drawable.hayabusa);
        train = Bitmap.createScaledBitmap(train, 180, 90, true);
        //看板
        board = BitmapFactory.decodeResource(res, R.drawable.station_signboard);
        board = Bitmap.createScaledBitmap(board, 300, 250, true);

        for (int i = 1; i <= station.length; i++) {
            int resourceId;
            if (i < 10)
                resourceId = getContext().getResources().getIdentifier("station0" + i, "drawable", getContext().getPackageName());
            else
                resourceId = getContext().getResources().getIdentifier("station" + i, "drawable", getContext().getPackageName());
            if (resourceId != 0) {
                badge[i - 1] = BitmapFactory.decodeResource(res, resourceId);
                badge[i - 1] = Bitmap.createScaledBitmap(badge[i - 1], badgeSize, badgeSize, true);
                Log.d("badge", "set");
            } else {
                Log.e("resource", "error");
            }
        }
        int badgeOrgSize = 250;
        for (int i = 1; i <= station.length; i++) {
            int resourceId;
            if (i < 10)
                resourceId = getContext().getResources().getIdentifier("station0" + i +"_or", "drawable", getContext().getPackageName());
            else
                resourceId = getContext().getResources().getIdentifier("station" + i +"_or", "drawable", getContext().getPackageName());
            if (resourceId != 0) {
                badgeOriginal[i - 1] = BitmapFactory.decodeResource(res, resourceId);
                badgeOriginal[i - 1] = Bitmap.createScaledBitmap(badgeOriginal[i - 1], badgeOrgSize, badgeOrgSize, true);
                Log.d("badge", "set");
            } else {
                Log.e("resource", "error"+String.valueOf(i));
            }
        }
    }

    // スパークラス(継承元)の「onDraw」メソッドをオーバーライドする
    @Override
    public void onDraw(Canvas canvas){
        // 「playScene」メソッドを実行
        playScene(canvas);
        frameIndex++;
    }

    // 「playScene」メソッド
    public void playScene(Canvas canvas) {
        int width = canvas.getWidth();
        int distance = cell * (width / imageCell);

        if (offset - offset_int < distance) {
            Log.d("test", String.valueOf(frameIndex - stopFrame));
            if (start) {
                //recent_cell～cellは各駅停車しながら移動
                if (offset - offset_int >= recent_cell * (width / imageCell)) {
                    if (frameIndex - startFrame <= width / imageCell / speed) {
                        offset += speed;
                        stopFrame = frameIndex;
                    }
                    if (frameIndex - stopFrame >= 300) {
                        start = true;
                        startFrame = frameIndex;
                    }
                } else {
                    offset += speed;
                    stopFrame = frameIndex;
                }
            }
        } else
            start = false;


        //始点背景処理
        canvas.drawBitmap(bgImageStart, -width - offset, 0, null);
        //繰り返し背景描画処理
        for(int i=0;i<station.length/imageCell;i++) {
            canvas.drawBitmap(bgImage, width * i - offset, 0, null);
        }
        //終点背景処理
        if(station.length%imageCell != 0) {
            canvas.drawBitmap(bgImage, width * (station.length / imageCell) - (width / imageCell) * (station.length % imageCell + 1) - offset, 0, null);
            canvas.drawBitmap(bgImageEnd, width * (station.length / imageCell + 1) - (width / imageCell) * (station.length % imageCell + 1) - offset, 0, null);
        } else {
            canvas.drawBitmap(bgImageEnd, width * (station.length / imageCell) - (width / imageCell) * (station.length % imageCell + 1) - offset, 0, null);
        }

        //各駅に付随するものの表示
        for(int i=0;i<station.length;i++){
            //看板・駅名
            paint.setColor(Color.argb(255, 0, 0, 0));
            int pos = (width/imageCell)*i-offset;
            canvas.drawBitmap(board, pos - 30, 800, paint);
            paint.setAntiAlias(true);
            paint.setTextSize(40);
            canvas.drawText(station[i], pos, 860, paint);
            paint.setTextSize(32);
            canvas.drawText(station_read[i], pos, 900, paint);
            paint.setAntiAlias(false);

            //吹き出し
            if(i==recent_cell)
                paint.setColor(Color.argb(190,0,0,240));
            else if(i<recent_cell)
                paint.setColor(Color.argb(190, 255, 255, 255));
            else if(i<cell)
                paint.setColor(Color.argb(190, 255, 255, 0));
            else if(i==cell)
                paint.setColor(Color.argb(190,240,0,0));
            else
                paint.setColor(Color.argb(190, 255, 255, 255));
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            RectF balloon = new RectF(0, 0, 300, 280);
            balloon.offset(pos-15, 390);
            canvas.drawRoundRect(balloon, 10, 10, paint);
            paint.setColor(Color.argb(255,0,0,0));
            canvas.drawBitmap(badgeOriginal[i],pos+10,380,paint);
        }
        //電車振動
        int trainFrame = 15;
        if(start) {
            if (frameIndex % (trainFrame * 2) == 0)
                trainY -= 5;
            else if (frameIndex % (trainFrame * 2) == trainFrame)
                trainY += 5;
        }
        canvas.drawBitmap(train,20-offset_int,trainY,null); //電車描画

        //バッジ描画
        int columnX = -badgeSize;
        int columnY = 0;
        for(int i=0;i<badge.length;i++){
            if (badge[i] != null) {
                if(columnX<getWidth()-2*badgeSize) {
                    columnX += badgeSize;
                } else {
                    columnY += badgeSize;
                    columnX = 0;
                }
                int dis = (i-1) * (width/imageCell)-offset_int;
                RectF rect = new RectF(0,0,badgeSize,badgeSize);
                paint.setColor(Color.argb(255, 40, 40, 40));
                rect.offset(columnX, columnY);
                canvas.drawRect(rect, paint);

                if(i<=recent_cell)
                    canvas.drawBitmap(badge[i], columnX, columnY, null);
                else if(i<=cell && offset-offset_int>dis) {
                    rect = new RectF(0,0,badgeSize,badgeSize);
                    rect.offset(columnX, columnY);
                    paint.setColor(Color.argb(255, 255, 255, 0));
                    canvas.drawRect(rect, paint);
                    canvas.drawBitmap(badge[i], columnX, columnY, null);
                }
            } else {
                Log.e("null", String.valueOf(i) + "," + String.valueOf(station.length));
            }
        }

        if(!start && cell == station.length-1 && offset >= distance){
            paint.setColor(Color.argb(190, 255, 255, 255));
            canvas.drawRect(500, 500, getWidth() - 100, getHeight() - 100, paint);
            paint.setColor(Color.argb(255, 0, 0, 0));
            paint.setTextSize(70);
            paint.setAntiAlias(true);
            canvas.drawText("よくがんばりました！ゴールしたよ！", 580, 700, paint);
            canvas.drawText("これからもがんばろう！",580,800,paint);
            paint.setAntiAlias(false);
        }

        RectF log = new RectF(0, 0, width, 265);
        log.offset(0, badgeSize*2);
        paint.setColor(Color.argb(190, 255, 255, 255));
        canvas.drawRect(log, paint);
        paint.setColor(Color.argb(190, 0, 0, 0));
        paint.setTextSize(45);
        paint.setAntiAlias(true);
        paint.setAntiAlias(false);

        RectF back = new RectF(0, 0, 350, 150);
        back.offset(0, getHeight()-150);
        paint.setColor(Color.parseColor("#2196F3"));
        canvas.drawRect(back, paint);
        paint.setColor(Color.argb(255, 255, 255, 255));
        paint.setTextSize(60);
        paint.setAntiAlias(true);
        canvas.drawText("もどる",80,getHeight()-55,paint);
        paint.setAntiAlias(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:

                if(event.getX() >= 0 && event.getX() <= 300 && event.getY() >= 240 && event.getY() <= 340) {
                    back = true;
                    Log.d("back","touched!");
                    ((Activity)pContext).finish(); //Context経由でActivityを終了
                }
                else if(!start) {
                    start = true;
                    Log.d("start","touched!");
                    startFrame = frameIndex;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return true;
    }



}
