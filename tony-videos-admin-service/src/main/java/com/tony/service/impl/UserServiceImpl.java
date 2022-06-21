package com.tony.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tony.mapper.UsersInfoMapper;
import com.tony.pojo.UsersInfo;
import com.tony.pojo.UsersInfoExample;
import com.tony.pojo.UsersInfoExample.Criteria;
import com.tony.service.UserService;
import com.tony.utils.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersInfoMapper userMapper;

    @Override
    public PagedResult queryUsers(UsersInfo user, Integer page, Integer pageSize) {


        String username = "";
        String nickname = "";
        if(user != null){
            username = user.getUsername();
            nickname = user.getNickname();
        }

        PageHelper.startPage(page, pageSize);

        UsersInfoExample userExample = new UsersInfoExample();
        Criteria userCriteria = userExample.createCriteria();
        if(StringUtils.isNotBlank(username)){
            userCriteria.andUsernameLike("%" + username + "%");
        }
        if(StringUtils.isNotBlank(nickname)){
            userCriteria.andNicknameLike("%" + nickname + "%");
        }

        //分页后查询的数据
        List<UsersInfo> userList = userMapper.selectByExample(userExample);
        //通过分页查询的数据构造许多属性
        PageInfo<UsersInfo> pageList = new PageInfo<>(userList);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(userList);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
