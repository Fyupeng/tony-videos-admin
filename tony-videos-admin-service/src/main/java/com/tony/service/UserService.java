package com.tony.service;

import com.tony.pojo.UsersInfo;
import com.tony.utils.PagedResult;

public interface UserService {
    public PagedResult queryUsers(UsersInfo user, Integer page, Integer pageSize);
}
