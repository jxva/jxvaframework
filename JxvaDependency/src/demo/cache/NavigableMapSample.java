package demo.cache;

import java.util.NavigableMap;
import java.util.TreeMap;

public class NavigableMapSample {
    public NavigableMapSample() {
        NavigableMap<String, String> map = new TreeMap<String, String>();
        map.put("E", "Echo");
        map.put("M", "Mike");
        map.put("A", "Alpha");
        map.put("S", "Sierra");
        map.put("K", "Kilo");
        map.put("I", "India");
        map.put("Q", "Quebec");
        map.put("C", "Charlie");
        map.put("G", "Golf");
        map.put("O", "Oscar");
        System.out.println("lower G: " + map.lowerKey("G"));
        System.out.println("floor G: " + map.floorKey("G"));
 
        System.out.println("lower F: " + map.lowerKey("F"));
        System.out.println("floor F: " + map.floorKey("F"));
        System.out.println("cieling I: " + map.ceilingKey("I"));
        System.out.println("heigher I: " + map.higherKey("I"));
 
        System.out.println("cieling J: " + map.ceilingKey("J"));
        System.out.println("heigher J: " + map.higherKey("J"));
        System.out.println("headMap: E不包含");
        System.out.println(map.headMap("E", false));
        System.out.println("headMap: E包含");
        System.out.println(map.headMap("E", true));
        System.out.println("tailMap: O不包含");
        System.out.println(map.tailMap("O", false));
        System.out.println("tailMap: O包含");
        System.out.println(map.tailMap("O", true));
        System.out.println("subMap: K, O不包含");
        System.out.println(map.subMap("K", false, "O", false));
        System.out.println("subMap: K, O包含");
        System.out.println(map.subMap("K", true, "O", true));
    }
 
    public static void main(String[] args) {
        new NavigableMapSample();
    }
}