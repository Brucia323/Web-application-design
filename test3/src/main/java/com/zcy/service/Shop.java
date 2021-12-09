package com.zcy.service;

import com.zcy.dao.GetSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    /**
     * 提交订单
     * @param wareIdList 商品id列表
     * @param wareNumList 商品数量列表
     */
    public void order(String wareIdList, String wareNumList) throws IOException {
        SqlSession sqlSession= GetSession.getSession();
        int id=sqlSession.selectOne("ShopMapper.selectOrderId")==null?1:sqlSession.selectOne("ShopMapper" +
                ".selectOrderId");
        id+=1;
        List<String> wareIdList1=strToList(wareIdList);
        List<String> wareNumList1=strToList(wareNumList);
        Map<String,String> map=new HashMap<>();
        for(int i=0;i<wareIdList.length();i++) {
            map.put("subId", String.valueOf(id));
            map.put("wareName", wareIdList1.get(i));
            map.put("wsum", wareNumList1.get(i));
            sqlSession.insert("ShopMapper.insetWare",map);
        }
        sqlSession.commit();
        sqlSession.close();
    }
    /**
     * 将字符串转换成列表
     * @param str 字符串
     */
    public List<String> strToList(String str){
        str=str.substring(1,str.length()-1);
        return Arrays.stream(str.split(",")).toList();
    }
}
