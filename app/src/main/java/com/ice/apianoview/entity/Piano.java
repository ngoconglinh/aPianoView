package com.ice.apianoview.entity;

/**
 * Created by ChengTao on 2016-11-25.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.google.gson.annotations.SerializedName;
import com.ice.apianoview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 钢琴实体
 */
public class Piano {
    //钢琴键数目
    public final static int PIANO_NUMS = 88;
    //黑白键的组数
    private final static int BLACK_PIANO_KEY_GROUPS = 8;
    private final static int WHITE_PIANO_KEY_GROUPS = 9;
    //黑白键集合
    private ArrayList<PianoKey[]> blackPianoKeys = new ArrayList<>(BLACK_PIANO_KEY_GROUPS);
    private ArrayList<PianoKey[]> whitePianoKeys = new ArrayList<>(WHITE_PIANO_KEY_GROUPS);
    //黑白键高度和宽度
    private int blackKeyWidth;
    private int blackKeyHeight;
    private int whiteKeyWidth;
    private int whiteKeyHeight;
    //钢琴总宽度
    private int pianoWith = 0;
    /**
     * 承载钢琴的布局的高度,用于初始化黑白键的高度和宽度
     */
    private float scaleX;
    private float scaleY;
    //上下文
    private Context context;

    private HashMap<String, Pair<Drawable, Drawable>> wHm = new HashMap<>();
    private HashMap<String, Pair<Drawable, Drawable>> bHm = new HashMap<>();


    private void performStep1(Pair<Object, Object> blackKey, Pair<Object, Object> whiteKey) {
        bHm.clear();
        wHm.clear();

        for (int i = 0; i < BLACK_PIANO_KEY_GROUPS; i++) {
            if (Thread.currentThread().isInterrupted()) return;
            PianoKey[] keys;
            switch (i) {
                case 0:
                    keys = new PianoKey[1];
                    break;
                default:
                    keys = new PianoKey[5];
                    break;
            }
            for (int j = 0; j < keys.length; j++) {
                if (Thread.currentThread().isInterrupted()) return;
                bHm.put(i + "" + j, Pair.create(getDrawable(blackKey).first, getDrawable(blackKey).second));
            }
        }

        for (int i = 0; i < WHITE_PIANO_KEY_GROUPS; i++) {
            if (Thread.currentThread().isInterrupted()) return;
            PianoKey[] mKeys;
            switch (i) {
                case 0:
                    mKeys = new PianoKey[2];
                    break;
                case 8:
                    mKeys = new PianoKey[1];
                    break;
                default:
                    mKeys = new PianoKey[7];
                    break;
            }
            for (int j = 0; j < mKeys.length; j++) {
                if (Thread.currentThread().isInterrupted()) return;
                wHm.put(i + "" + j, Pair.create(getDrawable(whiteKey).first, getDrawable(whiteKey).second));
            }
        }
    }
    public interface PianoCallback {
        void onInitFinished(Canvas canvas);
    }
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    //构造函数
    public Piano(
            Context context,
            Pair<Float, Float> scale,
            Pair<Object, Object> blackKey,
            Pair<Object, Object> whiteKey,
            Canvas canvas,
            PianoCallback callback
    ) {
        this.context = context;
        this.scaleX = scale.first;
        this.scaleY = scale.second;
        pianoWith = 0;
        blackKeyWidth = 0;
        blackKeyHeight = 0;
        whiteKeyWidth = 0;
        whiteKeyHeight = 0;

        executor.execute(() -> {
            performStep1(blackKey, whiteKey);
            mainHandler.post(() -> {
                initPiano(callback, canvas);
            });
        });
    }

    public void cancelInit() {
        executor.shutdownNow();
    }

    private void initPiano(PianoCallback callback, Canvas canvas) {
        try {

            Drawable mBlackDrawable = ContextCompat.getDrawable(context, R.drawable.black_down);
            Drawable mWhiteDrawable = ContextCompat.getDrawable(context, R.drawable.white_up);
            if (scaleX > 0 && scaleY > 0) {
                //获取黑键和白键的高度和宽度
                blackKeyWidth = (int) ((float) mBlackDrawable.getIntrinsicWidth() * scaleX);
                blackKeyHeight = (int) ((float) mBlackDrawable.getIntrinsicHeight() * scaleY);
                whiteKeyWidth = (int) ((float) mWhiteDrawable.getIntrinsicWidth() * scaleX);
                whiteKeyHeight = (int) ((float) mWhiteDrawable.getIntrinsicHeight() * scaleY);
                Log.d("6666666323", "initPiano: " + whiteKeyWidth + " whiteKeyHeight: " + whiteKeyHeight);
                //初始化黑键
                for (int i = 0; i < BLACK_PIANO_KEY_GROUPS; i++) {
                    PianoKey keys[];
                    switch (i) {
                        case 0:
                            keys = new PianoKey[1];
                            break;
                        default:
                            keys = new PianoKey[5];
                            break;
                    }
                    for (int j = 0; j < keys.length; j++) {
                        keys[j] = new PianoKey();
                        Rect areaOfKey[] = new Rect[1];
                        keys[j].setType(PianoKeyType.BLACK);
                        keys[j].setGroup(i);
                        keys[j].setPositionOfGroup(j);
                        keys[j].setVoiceId(getVoiceFromResources("b" + i + j));
                        keys[j].setPressed(false);

                        StateListDrawable drawable = new StateListDrawable();
                        drawable.addState(new int[]{android.R.attr.state_pressed}, bHm.get(i + "" + j).first);
                        drawable.addState(new int[]{-android.R.attr.state_pressed}, bHm.get(i + "" + j).second);
                        drawable.setState(new int[]{-android.R.attr.state_pressed});

                        keys[j].setKeyDrawable(
                                new ScaleDrawable(drawable,
                                        Gravity.NO_GRAVITY, scaleX, scaleY).getDrawable());
                        setBlackKeyDrawableBounds(i, j, keys[j].getKeyDrawable());
                        areaOfKey[0] = keys[j].getKeyDrawable().getBounds();
                        keys[j].setAreaOfKey(areaOfKey);
                        if (i == 0) {
                            keys[j].setVoice(PianoVoice.LA);
                            break;
                        }
                        switch (j) {
                            case 0:
                                keys[j].setVoice(PianoVoice.DO);
                                break;
                            case 1:
                                keys[j].setVoice(PianoVoice.RE);
                                break;
                            case 2:
                                keys[j].setVoice(PianoVoice.FA);
                                break;
                            case 3:
                                keys[j].setVoice(PianoVoice.SO);
                                break;
                            case 4:
                                keys[j].setVoice(PianoVoice.LA);
                                break;
                        }
                    }
                    blackPianoKeys.add(keys);
                }
                //初始化白键
                for (int i = 0; i < WHITE_PIANO_KEY_GROUPS; i++) {
                    PianoKey[] mKeys;
                    switch (i) {
                        case 0:
                            mKeys = new PianoKey[2];
                            break;
                        case 8:
                            mKeys = new PianoKey[1];
                            break;
                        default:
                            mKeys = new PianoKey[7];
                            break;
                    }
                    for (int j = 0; j < mKeys.length; j++) {
                        mKeys[j] = new PianoKey();
                        //固定属性
                        mKeys[j].setType(PianoKeyType.WHITE);
                        mKeys[j].setGroup(i);
                        mKeys[j].setPositionOfGroup(j);
                        mKeys[j].setVoiceId(getVoiceFromResources("w" + i + j));
                        mKeys[j].setPressed(false);

                        StateListDrawable drawable = new StateListDrawable();
                        drawable.addState(new int[]{android.R.attr.state_pressed}, wHm.get(i + "" + j).first);
                        drawable.addState(new int[]{-android.R.attr.state_pressed}, wHm.get(i + "" + j).second);
                        drawable.setState(new int[]{-android.R.attr.state_pressed});
                        mKeys[j].setKeyDrawable(
                                new ScaleDrawable(drawable,
                                        Gravity.NO_GRAVITY, scaleX, scaleY).getDrawable());
                        setWhiteKeyDrawableBounds(i, j, mKeys[j].getKeyDrawable());
                        pianoWith += whiteKeyWidth;
                        if (i == 0) {
                            switch (j) {
                                case 0:
                                    mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.RIGHT));
                                    mKeys[j].setVoice(PianoVoice.LA);
                                    mKeys[j].setLetterName("A0");
                                    break;
                                case 1:
                                    mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT));
                                    mKeys[j].setVoice(PianoVoice.SI);
                                    mKeys[j].setLetterName("B0");
                                    break;
                            }
                            continue;
                        }
                        if (i == 8) {
                            Rect areaOfKey[] = new Rect[1];
                            areaOfKey[0] = mKeys[j].getKeyDrawable().getBounds();
                            mKeys[j].setAreaOfKey(areaOfKey);
                            mKeys[j].setVoice(PianoVoice.DO);
                            mKeys[j].setLetterName("C8");
                            break;
                        }
                        //非固定属性
                        switch (j) {
                            case 0:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.RIGHT));
                                mKeys[j].setVoice(PianoVoice.DO);
                                mKeys[j].setLetterName("C" + i);
                                break;
                            case 1:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT_RIGHT));
                                mKeys[j].setVoice(PianoVoice.RE);
                                mKeys[j].setLetterName("D" + i);
                                break;
                            case 2:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT));
                                mKeys[j].setVoice(PianoVoice.MI);
                                mKeys[j].setLetterName("E" + i);
                                break;
                            case 3:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.RIGHT));
                                mKeys[j].setVoice(PianoVoice.FA);
                                mKeys[j].setLetterName("F" + i);
                                break;
                            case 4:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT_RIGHT));
                                mKeys[j].setVoice(PianoVoice.SO);
                                mKeys[j].setLetterName("G" + i);
                                break;
                            case 5:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT_RIGHT));
                                mKeys[j].setVoice(PianoVoice.LA);
                                mKeys[j].setLetterName("A" + i);
                                break;
                            case 6:
                                mKeys[j].setAreaOfKey(getWhitePianoKeyArea(i, j, BlackKeyPosition.LEFT));
                                mKeys[j].setVoice(PianoVoice.SI);
                                mKeys[j].setLetterName("B" + i);
                                break;
                        }
                    }
                    whitePianoKeys.add(mKeys);
                }
            }

            callback.onInitFinished(canvas);
        } catch (NullPointerException ignored) {}
    }

    public enum PianoVoice {
        DO, RE, MI, FA, SO, LA, SI
    }

    public enum PianoKeyType {
        @SerializedName("0")
        BLACK(0), @SerializedName("1")
        WHITE(1);
        private int value;

        PianoKeyType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "PianoKeyType{" + "value=" + value + '}';
        }
    }

    private enum BlackKeyPosition {
        LEFT, LEFT_RIGHT, RIGHT
    }

    /**
     * 从资源文件中获取声音ID
     *
     * @param voiceName 声音的文件名
     * @return 声音ID
     */
    private int getVoiceFromResources(String voiceName) {
        return context.getResources().getIdentifier(voiceName, "raw", context.getPackageName());
    }

    /**
     * 设置白色键的点击区域
     *
     * @param group            组数，从0开始
     * @param positionOfGroup  本组数内的位置
     * @param blackKeyPosition 黑键占白键的位置
     * @return 矩形数组
     */
    private Rect[] getWhitePianoKeyArea(int group, int positionOfGroup,
                                        BlackKeyPosition blackKeyPosition) {
        int offset = 0;
        if (group == 0) {
            offset = 5;
        }
        switch (blackKeyPosition) {
            case LEFT:
                Rect left[] = new Rect[2];
                left[0] =
                        new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, blackKeyHeight,
                                (7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2,
                                whiteKeyHeight);
                left[1] =
                        new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2,
                                0, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth, whiteKeyHeight);
                return left;
            case LEFT_RIGHT:
                Rect leftRight[] = new Rect[3];
                leftRight[0] =
                        new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, blackKeyHeight,
                                (7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2,
                                whiteKeyHeight);
                leftRight[1] =
                        new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth + blackKeyWidth / 2,
                                0, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2,
                                whiteKeyHeight);
                leftRight[2] =
                        new Rect((7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2,
                                blackKeyHeight, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth,
                                whiteKeyHeight);
                return leftRight;
            case RIGHT:
                Rect right[] = new Rect[2];
                right[0] = new Rect((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, 0,
                        (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2,
                        whiteKeyHeight);
                right[1] =
                        new Rect((7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth - blackKeyWidth / 2,
                                blackKeyHeight, (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth,
                                whiteKeyHeight);
                return right;
        }
        return null;
    }

    /**
     * 设置白色键图案的位置
     *
     * @param group           组数，从0开始
     * @param positionOfGroup 在本组中的位置
     * @param drawable        要设置的Drawale对象
     */
    private void setWhiteKeyDrawableBounds(int group, int positionOfGroup, Drawable drawable) {
        int offset = 0;
        if (group == 0) {
            offset = 5;
        }
        drawable.setBounds((7 * group - 5 + offset + positionOfGroup) * whiteKeyWidth, 0,
                (7 * group - 4 + offset + positionOfGroup) * whiteKeyWidth, whiteKeyHeight);
    }

    /**
     * 设置黑色键图案的位置
     *
     * @param group           组数，从0开始
     * @param positionOfGroup 组内的位置
     * @param drawable        要设置的Drawale对象
     */
    private void setBlackKeyDrawableBounds(int group, int positionOfGroup, Drawable drawable) {
        int whiteOffset = 0;
        int blackOffset = 0;
        if (group == 0) {
            whiteOffset = 5;
        }
        if (positionOfGroup == 2 || positionOfGroup == 3 || positionOfGroup == 4) {
            blackOffset = 1;
        }
        drawable.setBounds((7 * group - 4 + whiteOffset + blackOffset + positionOfGroup) * whiteKeyWidth
                        - blackKeyWidth / 2, 0,
                (7 * group - 4 + whiteOffset + blackOffset + positionOfGroup) * whiteKeyWidth
                        + blackKeyWidth / 2, blackKeyHeight);
    }

    public ArrayList<PianoKey[]> getWhitePianoKeys() {
        return whitePianoKeys;
    }

    public ArrayList<PianoKey[]> getBlackPianoKeys() {
        return blackPianoKeys;
    }

    public int getPianoWith() {
        return pianoWith;
    }


    public Pair<Drawable, Drawable> getDrawable(Pair<Object, Object> mDrawable) {
        Log.d("555124141", "getDrawable: " + mDrawable);
        Drawable downDrawable;
        if (mDrawable.first instanceof String) {
            downDrawable = Drawable.createFromPath((String) mDrawable.first);
        } else if (mDrawable.first instanceof Integer) {
            downDrawable = ContextCompat.getDrawable(context, (Integer) mDrawable.first);
        } else {
            downDrawable = null;
        }

        Drawable upDrawable;
        if (mDrawable.second instanceof String) {
            upDrawable = Drawable.createFromPath((String) mDrawable.second);
        } else if (mDrawable.second instanceof Integer) {
            upDrawable = ContextCompat.getDrawable(context, (Integer) mDrawable.second);
        } else {
            upDrawable = null;
        }

        Pair<Drawable, Drawable> result;
        if (downDrawable == null || upDrawable == null) {
            result = null;
        } else {
            result = Pair.create(downDrawable, upDrawable);
        }
        return result;
    }
}
