package wenran.com.baselibrary.bean;

/**
 * yangyoupeng 2018/4/22
 */
public class BaseApi {

    public enum HostType {
        TEST_150,
        TEST_138,
        TEST_146,
        TEST_245,
        TEST_10,
        TEST_145
    }

    private static String sHost;

    public static void host(HostType hostType, boolean isDebug) {
        if (isDebug) {
            switch (hostType) {
                case TEST_150:
                    //测试环境
                    sHost = "http://192.168.0.150:8185/";
                    break;
                default:
                    sHost = "http://192.168.0.150:8185/";
                    break;
            }
        } else {
            sHost = "http://192.168.99.105:8080/WenranApp/app/";
        }
    }

    public static String getBaseUrl() {
        return sHost == null ? "http://192.168.99.105:8080/WenranApp/app/" : sHost + "api/";
    }


}
