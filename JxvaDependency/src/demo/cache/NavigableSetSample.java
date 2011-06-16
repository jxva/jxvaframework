package demo.cache;

import java.util.NavigableMap;
import java.util.TreeMap;

public class NavigableSetSample {
    public NavigableSetSample() {
        NavigableMap<String, String> dic = init();
        String key = "010";
        if (dic.containsKey(key)) {
            System.out.printf("%s 的城市是： %s%n", key, dic.get(key));
        } else {
            String lowerKey = dic.lowerKey(key);
            String higherKey = dic.higherKey(key);
            System.out.printf("%s 区号相近的城市是： %n", key);
            if (lowerKey != null) {
                System.out.printf("%s(%s)%n", dic.get(lowerKey), lowerKey);
            }
            if (higherKey != null) {
                System.out.printf("%s(%s)%n", dic.get(higherKey), higherKey);
            }
        }
       
        NavigableMap<String, String> subdic
            = dic.subMap("020", true, "029", true);
 
        System.out.printf("%n02X 区号的城市是：%n%s", subdic);
    }
    private NavigableMap<String, String> init() {
        NavigableMap<String, String> dic = new TreeMap<String, String>();
        dic.put("010", "北京市");
        dic.put("020", "广州市");
        dic.put("021", "上海市");
        dic.put("022", "天津市");
        dic.put("024", "沈阳市");
        dic.put("025", "南京市");
        dic.put("027", "武汉市");
        dic.put("028", "成都市");
        dic.put("029", "西安市");
        return dic;
    }
    public static void main(String[] args) {
        new NavigableSetSample();
    }
}