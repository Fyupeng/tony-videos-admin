package com.tony.mapper;

import com.tony.pojo.UsersInfo;
import com.tony.pojo.UsersInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersInfoMapper {
    int countByExample(UsersInfoExample example);

    int deleteByExample(UsersInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(UsersInfo record);

    int insertSelective(UsersInfo record);

    List<UsersInfo> selectByExample(UsersInfoExample example);

    UsersInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UsersInfo record, @Param("example") UsersInfoExample example);

    int updateByExample(@Param("record") UsersInfo record, @Param("example") UsersInfoExample example);

    int updateByPrimaryKeySelective(UsersInfo record);

    int updateByPrimaryKey(UsersInfo record);
}