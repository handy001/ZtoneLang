package android;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.assist.Assert;
import android.collection.Datum;
import android.collection.Setting;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.io.FilePaths;
import android.log.Log;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.reflect.Clazz;

import androidx.annotation.NonNull;

import static android.C.file.app_root_path;
import static android.C.properties.root_path;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.pm.PackageManager.GET_META_DATA;

public class Args {
    private static final String TAG = "Args";

    public static final Class AtCls = Clazz.forName("android.app.ActivityThread");

    public static final Application App;
    public static final Context context;

    private static String mProcessName;

    static {
        App = Clazz.invoke(AtCls, "currentApplication");
        context = App.getApplicationContext();
    }

    /**
     * 获取进程号对应的进程名
     *
     * @return 进程名
     */
    public static String myProcessName() {
        return Assert.isEmpty(mProcessName) ? mProcessName = Clazz.invoke(AtCls, "currentProcessName") : mProcessName;
    }

    //////////////////////////
    //

    public static final Args Env = new Loader().load();

    public final Resources Res;

    public final PackageManager Pmgr;
    public final ActivityManager Amgr;
    public final AlarmManager Alarm;
    public final ConnectivityManager Cnn;
    public final AudioManager Audio;

    public final PackageInfo Package;
    public final ApplicationInfo Info;

    public final Setting Cfg;
    public final Datum Stors;

    public final FilePaths Paths;

    private Args(@NonNull Loader ldr) {
        assert ldr == null;

        Res = context.getResources();

        Pmgr = context.getPackageManager();
        Amgr = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        Alarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Cnn = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);

        Audio = (AudioManager) context.getSystemService(AUDIO_SERVICE);

        Info = getApplicationInfo(context.getPackageName());
        Package = getPackageInfo(Info.packageName);

        Cfg = new Setting(context, "app_config");
        Stors = new Datum(context, "");

        Paths = new FilePaths(findRootPath());
    }

    public final ApplicationInfo getApplicationInfo(String packageName) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = Pmgr.getApplicationInfo(packageName, GET_META_DATA);
        } catch (Exception e) {
            Log.e(e);
        }

        return appInfo != null ? appInfo : context.getApplicationInfo();
    }

    public final PackageInfo getPackageInfo(String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = Pmgr.getPackageInfo(packageName, 0);
        } catch (Exception e) {
            Log.e(e);
        }

        return packageInfo != null ? packageInfo : new PackageInfo();
    }

    public final boolean inPackageProcess() {
        return Package.packageName.equals(myProcessName());
    }

    public final String findRootPath() {
        String path = "";

        try {
            if (Info.metaData != null && Info.metaData.containsKey(root_path)) {
                path = Info.metaData.getString(root_path);
            } else {
                path = Info.packageName.substring(Info.packageName.lastIndexOf('.') + 1);
            }
        } catch (Exception e) {
            Log.e(e);
        } finally {
            if (Assert.isEmpty(path)) {
                path = app_root_path;
            }
        }

        return path;
    }

    private static class Loader {

        public Args load() {
            assert App == null;
            assert context == null;

            return new Args(this);
        }
    }
}