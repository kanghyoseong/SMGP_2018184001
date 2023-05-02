package kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.www.smgp2018184001.vampiresurvivors.framework.interfaces.IRecyclable;

public class RecycleBin {
    private static final String TAG = RecycleBin.class.getSimpleName();
    protected static HashMap<Class, ArrayList<IRecyclable>> recycleBin = new HashMap<>();

    public static void collect(IRecyclable object) {
        Class clazz = object.getClass();
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null) {
            bin = new ArrayList<>();
            recycleBin.put(clazz, bin);
        }
        if (bin.indexOf(object) >= 0) { // 있으면 인덱스 리턴, 없으면 -1 리턴
            //하나의 update 내에서 두 개 이상의 조건에 의해 두 번 이상 삭제되는 일이 있을 수 있다.
            Log.w(TAG, "Already exists: " + object);
            return;
        }
        object.onRecycle();//객체가 재활용 되기 전에 해야할 일이 있다면 여기서 한다.
        bin.add(object);
        //Log.w(TAG, "Collected " + object.getClass().getSimpleName());
    }

    public static IRecyclable get(Class clazz) {
        ArrayList<IRecyclable> bin = recycleBin.get(clazz);
        if (bin == null) return null;
        if (bin.size() == 0) return null;
        //Log.w(TAG, "Recycled " + clazz.getSimpleName());
        return bin.remove(0);
    }
}
