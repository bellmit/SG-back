package cn.com.inhand.common.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    /**
     * @desc check char whether in the string
     * @param str
     * @param cr
     * @return
     */
    public static boolean checkChar(String str, char cr) {
        boolean has_ = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == cr) {
                has_ = true;
                continue;
            }
        }
        return has_;
    }
    
    public static boolean checkStringByArray(String str,String [] array){
        boolean has_ = false;
        for(int i=0;i<array.length;i++){
            if(str != null && str.equals(array[i])){
                has_ = true;
            }
        }
        return has_;
    }
    
    public static Map<String,String> makeParameToMap(String parames){
        Map<String,String> paramesMap = new HashMap<String,String>();
        String [] paramesA = parames.split("&");
        for(int i=0;i<paramesA.length;i++){
            paramesMap.put(paramesA[i].split("=")[0], paramesA[i].split("=")[1]);
        }
        return paramesMap;
    }
}
