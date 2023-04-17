package Utility;

import org.json.JSONObject;

public class Statics {

//    public final static String STATICS_SERVER = "http://localstaticgach.com/";
        public final static String STATICS_SERVER = "https://statics.irysc.com/";

    public final static String GACH_SERVER = "http://127.0.0.1:8080/api/";
//    public final static String GACH_SERVER = "https://e.irysc.com/api/";


    public final static boolean DEV_MODE = true;
    public final static boolean LINUX = true;

    public final static int TOKEN_EXPIRATION = 60 * 60 * 24 * 7;

    public final static int USER_LIMIT_CACHE_SIZE = 3000;
    public final static int USER_EXPIRATION_SEC = 60 * 60 * 24 * 7;
    public final static int ONE_MB = 1024 * 1024;
    public final static int MAX_FILE_SIZE = ONE_MB * 2;

    public static final String JSON_NOT_VALID_PARAMS = new JSONObject().put("status", "nok").put("msg", "params is not valid").toString();
    public static final String JSON_NOT_ACCESS = new JSONObject().put("status", "nok").put("msg", "no access to this method").toString();
    public static final String JSON_NOT_VALID_ID = new JSONObject().put("status", "nok").put("msg", "id is not valid").toString();
    public static final String JSON_OK = new JSONObject().put("status", "ok").toString();

}
