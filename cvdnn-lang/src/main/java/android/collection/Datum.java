package android.collection;

import android.assist.Assert;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;

import java.util.Map;

public class Datum {
    private static final String TAG = "Datum";

    private static final String DEFAULT_SHARED_PREFERENCES = "storage_of_data";

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public Datum(Context context, String fileName) {
        if (context != null) {
            mPreferences = context.getApplicationContext().getSharedPreferences( //
                    Assert.notEmpty(fileName) ? fileName : DEFAULT_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        }
    }

    public boolean get(String tag, boolean defValue) {

        return Assert.notEmpty(tag) && mPreferences != null ? mPreferences.getBoolean(tag, defValue) : defValue;
    }

    public Datum put(String tag, boolean value) {
        if (Assert.notEmpty(tag)) {
            if (mEditor != null) {
                mEditor.putBoolean(tag, value);

            } else if (mPreferences != null) {
                mPreferences.edit().putBoolean(tag, value).apply();
            }
        }

        return this;
    }

    public int get(String tag, int defValue) {

        return Assert.notEmpty(tag) && mPreferences != null ? mPreferences.getInt(tag, defValue) : defValue;
    }

    public Datum put(String tag, int value) {
        if (Assert.notEmpty(tag)) {
            if (mEditor != null) {
                mEditor.putInt(tag, value);

            } else if (mPreferences != null) {
                mPreferences.edit().putInt(tag, value).apply();
            }
        }

        return this;
    }

    public long get(String tag, long defValue) {

        return Assert.notEmpty(tag) && mPreferences != null ? mPreferences.getLong(tag, defValue) : defValue;
    }

    public Datum put(String tag, long value) {
        if (Assert.notEmpty(tag)) {
            if (mEditor != null) {
                mEditor.putLong(tag, value);

            } else if (mPreferences != null) {
                mPreferences.edit().putLong(tag, value).apply();
            }
        }

        return this;
    }

    public float get(String tag, float defValue) {

        return Assert.notEmpty(tag) && mPreferences != null ? mPreferences.getFloat(tag, defValue) : defValue;
    }

    public Datum put(String tag, float value) {
        if (Assert.notEmpty(tag)) {
            if (mEditor != null) {
                mEditor.putFloat(tag, value);

            } else if (mPreferences != null) {
                mPreferences.edit().putFloat(tag, value).apply();
            }
        }

        return this;
    }

    public String get(String tag, String defValue) {

        return Assert.notEmpty(tag) && mPreferences != null ? mPreferences.getString(tag, defValue) : defValue;
    }

    public Datum put(String tag, String value) {
        if (Assert.notEmpty(tag)) {
            if (mEditor != null) {
                mEditor.putString(tag, value);

            } else if (mPreferences != null) {
                mPreferences.edit().putString(tag, value).apply();
            }
        }

        return this;
    }

    public Map<String, ?> all() {

        return mPreferences != null ? mPreferences.getAll() : new ArrayMap();
    }

    public Datum edit() {
        if (mPreferences != null) {
            mEditor = mPreferences.edit();
        }

        return this;
    }

    public Datum remove(String tag) {
        if (Assert.notEmpty(tag)) {
            if (mEditor != null) {
                mEditor.remove(tag);

            } else if (mPreferences != null) {
                mPreferences.edit().remove(tag).apply();
            }
        }

        return this;
    }

    public Datum clear() {
        if (mEditor != null) {
            mEditor.clear();

            mEditor = null;
        } else {
            mPreferences.edit().clear().apply();
        }

        return this;
    }

    public Datum apply() {
        if (mEditor != null) {
            mEditor.apply();

            mEditor = null;
        }

        return this;
    }

    public Datum commit() {
        if (mEditor != null) {
            mEditor.commit();

            mEditor = null;
        }

        return this;
    }
}
