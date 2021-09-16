package com.example.demo2;

import java.util.HashMap;
import java.util.Map;

public class DBHelper {
    private static Map<String, Mobile> mobiles=new HashMap<String,Mobile>();
    static{
        mobiles.put("1",new Mobile(1,"iPhone 13 mini",5199));
        mobiles.put("2",new Mobile(2,"iPhone 13",5999));
        mobiles.put("3",new Mobile(3,"iPhone 13 Pro",7999));
        mobiles.put("4",new Mobile(4,"iPhone 13 Pro Max",8999));
    }

    public static Map<String,Mobile> getMobiles(){
        return mobiles;
    }

    public static Mobile getMobileById(String id){
        return mobiles.get(id);
    }
}
