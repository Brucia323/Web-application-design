package com.zcy.service;

import com.google.gson.Gson;
import com.zcy.dao.GetSession;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

public class Ware {
    public String getList() throws IOException {
        SqlSession sqlSession= GetSession.getSession();
        List<com.zcy.bean.Ware> wares=sqlSession.selectList("WareMapper.selectWare");
        sqlSession.close();
        Gson gson=new Gson();
        return gson.toJson(wares);
    }
}
