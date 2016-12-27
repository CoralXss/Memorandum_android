
package com.xss.com.memorandum.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @类描述： 常用工具类
 * @创建人：龙章煌
 * @创建时间：2014-03-24
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class Utility {
    private static final String TAG  = "Utility";
    private Utility() {
    }
    public static class UilityHolder {
        private static  Utility utility = new Utility();
    }

    public static Utility getInstance() {
        return UilityHolder.utility;
    }
    /**
     * 获取当前版本信息对
     *
     * @param ctt 上下文对象
     * @return 当前版本信息
     */
    public static String getVerstionStr(Context ctt) {
        StringBuffer sb = new StringBuffer();
        PackageManager manager = ctt.getPackageManager();
        String pkgName = ctt.getPackageName();
        try {
            PackageInfo info = manager.getPackageInfo(pkgName, 0);
            //sb.append(info.packageName);
            sb.append(info.versionName);// 版本名
            //sb.append(info.versionCode);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * 获取AndroidManifest.xml文件中的节点meta-data
     *
     * @param ctt
     * @param name
     * @return
     */
    public static String getMetaOfApplication(Context ctt, String name) {
        ApplicationInfo appInfo = null;
        String msg = null;
        try {
            appInfo = ctt.getPackageManager().getApplicationInfo(ctt.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appInfo != null) {
            msg = appInfo.metaData.getString(name);
            if (msg == null || msg.length() == 0 || msg.equals("null")) {
                msg = String.valueOf(appInfo.metaData.getInt(name));
            }
        }
        return msg;
    }


    /**
     * 逗号金额显示
     *
     * @param money
     * @return
     */
    public static String MMoney(double money) {
        DecimalFormat df = new DecimalFormat();
        String style = "0,000";
        df.applyPattern(style);
        return df.format((int) Math.floor(money));
    }

    /**
     * 银行卡号加密显示处理
     *
     * @param bankcardaccount
     * @return
     */
    public static String MCard(String bankcardaccount) {
        if (bankcardaccount.length() > 10) {
            int len = bankcardaccount.length() - 8;
            StringBuffer sbBuffer = new StringBuffer();
            sbBuffer.append(bankcardaccount.substring(0, 4))
                    .append(replaceSubString(bankcardaccount, 8, len))
                    .append(bankcardaccount.substring((bankcardaccount.length() - 4), (bankcardaccount.length())));
            return sbBuffer.toString();
        } else {
            return bankcardaccount.toString();
        }

    }

    /**
     * 手机号加密显示处理
     *
     * @param bankcardaccount
     * @return
     */
    public static String MPhone(String bankcardaccount) {
        if (bankcardaccount.length() > 10) {
            int len = bankcardaccount.length() - 7;
            StringBuffer sbBuffer = new StringBuffer();
            sbBuffer.append(bankcardaccount.substring(0, 3))
                    .append(replaceSubString(bankcardaccount, 7, len))
                    .append(bankcardaccount.substring((bankcardaccount.length() - 4), (bankcardaccount.length())));
            return sbBuffer.toString();
        } else {
            return bankcardaccount.toString();
        }

    }

    public static String replaceSubString(String str, int s, int n) {
        String sub = "";
        try {
            sub = str.substring(s, str.length() - n);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < n; i++) {
                sb = sb.append("*");
            }
            sub += sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /**
     * 获取当前客户端版本号
     *
     * @param context
     * @return
     * @throws Exception
     */
    private String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 数据显示不以科学计算法的形式
     *
     * @param d
     * @return
     */
    public String getNoGroupingUsed(Double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        return nf.format(d);
    }


    /**
     * 手机号
     */
    public final static String PHONE_REGULAR = "^[1][3-8]\\d{9}$";
    /**
     * 邮箱
     */
    public final static String EMAIL_REGULAR = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    /**
     * <br>
     * 正则表达式匹配</br>
     *
     * @param str        匹配串
     * @param regularStr 匹配规则
     * @return true if mathced
     */
    public static boolean strMatch(String str, String regularStr) {
        Pattern pattern = Pattern.compile(regularStr);
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * Json 解析
     * @param obj
     * @param key
     * @return
     */
    public static String parseJson(String obj,String key){
        String result = "";
        if(obj != null){
            if(obj.indexOf("{") >= 0){
                try {
                    JSONObject jsonObject = new JSONObject(obj);
                    result = jsonObject.getString(key);
                    if(result == null || result.equals("null")){
                        result = "";
                    }
                } catch (JSONException e){
//                    MyLog.getInstance().logError(TAG , e.getMessage());
                }
            }
        }
        return  result;
    }


    /**
     * 提供精确的小数位四舍五入处理。
     */
    public static String divide(String v,int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal(10000);
        String tmp = b.divide(one,scale, BigDecimal.ROUND_HALF_UP).toString();
        if(tmp.contains(".0000")){
            return tmp.replace(".0000","");
        }else if(tmp.contains(".000")){
            return tmp.replace(".000","");
        }else if(tmp.contains(".00")){
            return tmp.replace(".00","");
        }else if(tmp.contains(".0")){
            return tmp.replace(".0","");
        }
        return tmp;

    }

    /**
     *
     * @param v
     * @return
     */
    public static String multiply(String v){
        if(TextUtils.isEmpty(v)){
            throw new IllegalArgumentException("The value must be dont blank string");
        }
        BigDecimal x = new BigDecimal(v);
        BigDecimal y = new BigDecimal(10000);
        return x.multiply(y).longValue()+"";//乘
    }

    /**
     * 分金额转换成0.00
     * @param money
     * @return
     */
   public static String stringToMoney(String money){
       /* if(money == null||money.length()==0||money.equals("0")||money.equals("0.00")){
            return "0.00";
        }
        if(money.length()<3){
           return  money;
        }
        if(money.length()>3){
           money = money.substring(0,money.length()-2)+"."+money.substring(money.length()-2,money.length());
        }*/
       DecimalFormat df   = new DecimalFormat("######0.00");
       return df.format(Double.valueOf(money));
   }

    /**
     * 将金额三位一组用逗号分隔，并且保留两位小数
     * @param money
     * @return
     */
    public static String splitMoneyAndKeepTwoValidDigits(double money) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(money);
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    public static String doubleKeepTwo(double d) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(d);
    }


    //正则表达式验证只能输入数字
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

    public static boolean isNumericAndDot(String str)
    {
        Pattern pattern = Pattern.compile("[\\d]*[\\.]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getRGB() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }
}
