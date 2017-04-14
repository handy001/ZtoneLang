package android.json;

import android.assist.Assert;
import android.concurrent.ThreadPool;
import android.io.FileUtils;
import android.io.StreamUtils;
import android.log.Log;
import android.math.Maths;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

import static android.Const.CHARSET_ENCODING;

public class JSONUtils {
    private static final String TAG = "JSONUtils";

    private static final int LENGTH_BUFFER = 2048;

    public static JSONObject from(String json) {
        JSONObject jsonObject = null;

        if (Assert.notEmpty(json)) {
            try {
                jsonObject = new JSONObject(json);
            } catch (Exception e) {
                Log.i(TAG, "new JSONObject error: %s", json);
            }
        }

        return jsonObject;
    }

    public static JSONObject getJSONObject(JSONObject jObj, String strJPath) {

        return getJSONObject(jObj, JPath.createJPath(strJPath));
    }

    public static JSONObject getJSONObject(JSONObject jObj, JPath jPath) {
        JSONObject rObj = null;
        if (jObj != null && jPath != null) {
            JNodeArray jNodes = jPath.getJNodeArray();

            if (jNodes != null) {
                rObj = jObj;

                int len = jNodes.length();
                for (int i = 0; i < len; i++) {
                    rObj = rObj.optJSONObject(jNodes.get(i));
                }
            }
        }

        return rObj;
    }

    public static JSONArray getJSONArray(JSONObject jObj, String strJPath) {

        return getJSONArray(jObj, JPath.createJPath(strJPath));
    }

    public static JSONArray getJSONArray(JSONObject jObj, JPath jPath) {
        JSONArray rArray = null;
        if (jObj != null && jPath != null) {
            JNodeArray jNodes = jPath.getJNodeArray();

            if (jNodes != null) {
                JSONObject rObj = jObj;

                int len = jNodes.length();
                for (int i = 0; i < len; i++) {
                    String node = jNodes.get(i);
                    if (i == len - 1) {
                        rArray = rObj.optJSONArray(node);
                    } else {
                        rObj = rObj.optJSONObject(node);
                    }
                }
            }
        }

        return rArray;
    }

    public static boolean optBoolean(JSONObject jObj, String strJPath, boolean dValue) {
        boolean result = dValue;

        if (jObj != null) {
            JPath jPath = JPath.createJPath(strJPath);
            if (jPath != null) {
                JSONObject rObj = getJSONObject(jObj, jPath);
                if (rObj != null) {
                    result = Maths.valueOf(rObj.opt(jPath.getKey()), dValue);
                }
            }
        }

        return result;
    }

    public static boolean optBoolean(JSONObject jObj, String strJPath) {

        return optBoolean(jObj, strJPath, false);
    }

    public static double optDouble(JSONObject jObj, String strJPath, double dValue) {
        double result = dValue;

        if (jObj != null) {
            JPath jPath = JPath.createJPath(strJPath);
            if (jPath != null) {
                JSONObject rObj = getJSONObject(jObj, jPath);
                if (rObj != null) {
                    result = rObj.optDouble(jPath.getKey(), dValue);
                }
            }
        }

        return result;
    }

    public static double optDouble(JSONObject jObj, String strJPath) {

        return optDouble(jObj, strJPath, 0);
    }

    public static int optInt(JSONObject jObj, String strJPath, int dValue) {
        int result = dValue;

        if (jObj != null) {
            JPath jPath = JPath.createJPath(strJPath);
            if (jPath != null) {
                JSONObject rObj = getJSONObject(jObj, jPath);
                if (rObj != null) {
                    result = Maths.valueOf(rObj.opt(jPath.getKey()), dValue);
                }
            }
        }

        return result;
    }

    public static int optInt(JSONObject jObj, String strJPath) {

        return optInt(jObj, strJPath, 0);
    }

    public static long optLong(JSONObject jObj, String strJPath, long dValue) {
        long result = dValue;

        if (jObj != null) {
            JPath jPath = JPath.createJPath(strJPath);
            if (jPath != null) {
                JSONObject rObj = getJSONObject(jObj, jPath);
                if (rObj != null) {
                    result = rObj.optLong(jPath.getKey(), dValue);
                }
            }
        }

        return result;
    }

    public static long optLong(JSONObject jObj, String strJPath) {

        return optLong(jObj, strJPath, 0);
    }

    public static float optFloat(JSONObject jObj, String strJPath, float dValue) {
        float result = dValue;

        if (jObj != null) {
            JPath jPath = JPath.createJPath(strJPath);
            if (jPath != null) {
                JSONObject rObj = getJSONObject(jObj, jPath);
                if (rObj != null) {
                    result = (float) rObj.optDouble(jPath.getKey(), dValue);
                }
            }
        }

        return result;
    }

    public static float optFloat(JSONObject jObj, String strJPath) {

        return optFloat(jObj, strJPath, 0);
    }

    public static String optString(JSONObject jObj, String strJPath, String dValue) {
        String result = dValue;

        if (jObj != null) {
            JPath jPath = JPath.createJPath(strJPath);
            if (jPath != null) {
                JSONObject rObj = getJSONObject(jObj, jPath);
                if (rObj != null) {
                    Object obj = rObj.opt(jPath.getKey());
                    result = obj != null && obj instanceof String ? (String) obj : dValue;
                }
            }
        }

        return result;
    }

    public static String optString(JSONObject jObj, String strJPath) {

        return optString(jObj, strJPath, "");
    }

    public static boolean writeJSONObject(JSONObject json, String filePath) {
        boolean result = false;

        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            result = writeJSONObject(json, file);
        }

        return result;
    }

    public static boolean writeJSONObject(JSONObject json, File file) {
        boolean result = false;
        if (json != null && file != null) {
            if (!file.exists()) {
                try {
                    result = file.createNewFile();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            } else {
                result = true;
            }

            if (result) {
                String strJson = json.toString();
                if (!TextUtils.isEmpty(strJson)) {
                    BufferedWriter writer = null;

                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        writer = new BufferedWriter(fileWriter);
                        writer.append(strJson);
                        writer.flush();
                    } catch (Exception e) {
                        result = false;
                        Log.e(TAG, e.getMessage(), e);
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (Exception e) {
                                Log.v(TAG, e.getMessage(), e);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 异步保存
     *
     * @param json
     * @param jsonFile
     */
    public static void asyncWriteJSONObject(final JSONObject json, final File jsonFile) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                writeJSONObject(json, jsonFile);
            }
        };

        ThreadPool.Impl.execute(runnable);
    }

    public static JSONObject getJSONObject(InputStream inStream) {
        JSONObject jsonObject = null;

        if (inStream != null) {
            String strJson = StreamUtils.getContent(inStream);
            if (Assert.notEmpty(strJson)) {
                try {
                    jsonObject = new JSONObject(strJson);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }

        return jsonObject;
    }

    public static JSONObject getJSONObject(String absFilePath) {
        JSONObject jsonObject = null;

        if (Assert.notEmpty(absFilePath)) {
            jsonObject = getJSONObject(new File(absFilePath));
        }

        return jsonObject;
    }

    public static JSONObject getJSONObject(File absFile) {
        JSONObject jsonObject = null;

        if (absFile != null && absFile.exists()) {
            String tmpContent = FileUtils.read(absFile, CHARSET_ENCODING);
            if (Assert.notEmpty(tmpContent)) {
                try {
                    jsonObject = new JSONObject(tmpContent);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }

        return jsonObject;
    }
}
