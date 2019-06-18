package wenran.com.baselibrary.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created by crowhine on 2018/7/6.
 * description:操纵String
 *
 * @author crowhine
 */

public class StringUtil {
    /**
     * 把mac地址98:07:2D:DE:36:10切割成字符串
     */
    public static String splitMacToString(String macString) {
        String str = "";
        if (macString == null || "".equals(macString)) {
            return str;
        } else {
            String[] split = macString.split(":");
            StringBuffer sb = new StringBuffer();
            if (split != null && split.length != 0) {
                for (String s :
                        split) {
                    sb.append(s);
                }
            }
            str = sb.toString();
        }
        return str;
    }

    /**
     * 把字符串集合转换成字符串
     */
    public static String strListToString(List<String> stringList) {
        String needStr = "";
        if (stringList == null || stringList.size() == 0) {
            return needStr;
        } else {
            StringBuffer sb = new StringBuffer();
            for (String string :
                    stringList) {
                sb.append(string);
            }
            needStr = sb.toString();
        }
        return needStr;
    }

    /**
     * 把mac地址98:07:2D:DE:36:10切割成字符串数组
     */
    public static String[] splitMacToArr(String macString) {
        String[] split;
        if (macString == null || "".equals(macString)) {
            return null;
        } else {
            split = macString.split(":");
        }
        return split;
    }

    /**
     * 把字符串数组转化成集合
     */
    public static List<String> arrToList(String[] strArr) {
        List<String> stringList = new ArrayList<>();
        if (strArr == null || strArr.length == 0) {
            return stringList;
        } else {
            for (String s :
                    strArr) {
                stringList.add(s);
            }
        }
        return stringList;
    }


    public List<String> byteArrToString(byte[] bytes) {
        List<String> strList = new ArrayList();
        for (byte b :
                bytes) {
            strList.add(b + "");
        }
        return strList;
    }

    /**
     * 切割校验和
     */
    public static String subCheckSum(String str) {
        String needStr;
        int leastLength = 2;
        if (str == null || "".equals(str)) {
            return "00";
        } else {
            if (str.length() >= leastLength) {
                needStr = str.substring(str.length() - 2, str.length());
            } else {
                needStr = 0 + str;
            }
        }
        return needStr;
    }

    /**
     * 把十六位的字符串转换成字节数组
     **/
    public static byte[] hexStrToBytes(String str) {
        if (str == null || "".equals(str.trim())) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        int divideNum = 2;
        for (int i = 0; i < str.length() / divideNum; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }


    /**
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexFun(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        // 使用String的format方法进行转换
        for (byte b : bytes) {
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    /***
     *
     * 是否是空字符串**/
    public static boolean isEmptyStr(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符串是否都为数字
     */
    public static boolean isDigit(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.valueOf(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * 截取数字
     */
    public static String getNumbers(String content) {
        Pattern pattern = compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 截取非数字
     */
    public static String splitNotNumber(String content) {
        Pattern pattern = compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}

