package com.tony.service;

import com.tony.pojo.Bgm;
import com.tony.utils.PagedResult;

public interface VideoService {

    public void addBgm(Bgm bgm);

    public PagedResult queryBgmList(Integer page, Integer pageSize);

    public void deleteBgm(String bgmId);

    public PagedResult queryReportList(Integer page, Integer pageSize);

    public void updateVideoStatus(String videoId, Integer status);
}
