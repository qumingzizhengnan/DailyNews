package example.com.daliynews.until;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceCacheUtil {

    public static SharedPreferences.Editor editor;

    public static void init(Context context){
        editor = context.getSharedPreferences("NewsDetailData", MODE_PRIVATE).edit();

    }
}
