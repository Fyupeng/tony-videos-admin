package com.tony.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tony.enums.BGMOperatorTypeEnum;
import com.tony.mapper.BgmMapper;
import com.tony.mapper.UsersReportCustomMapper;
import com.tony.mapper.VideosMapper;
import com.tony.pojo.Bgm;
import com.tony.pojo.BgmExample;
import com.tony.pojo.Videos;
import com.tony.pojo.vo.Reports;
import com.tony.service.VideoService;
import com.tony.utils.JsonUtils;
import com.tony.utils.PagedResult;
import com.tony.web.util.ZKCurator;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private ZKCurator zkCurator;

    @Autowired
    private UsersReportCustomMapper usersReportCustomMapper;

    @Override
    public void addBgm(Bgm bgm) {

        String bgmId = sid.nextShort();
        bgm.setId(bgmId);
        bgmMapper.insert(bgm);

        Map<String, Object> map = new HashMap<>();
        map.put("operaType", BGMOperatorTypeEnum.ADD.type);
        map.put("path", bgm.getPath());

        zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        BgmExample example = new BgmExample();
        List<Bgm> list = bgmMapper.selectByExample(example);

        PageInfo<Bgm> pageList = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setTotal(pageList.getPages());
        result.setRows(list);
        result.setPage(page);
        result.setRecords(pageList.getTotal());

        return result;
    }

    @Override
    public void deleteBgm(String bgmId) {

        Bgm bgm = bgmMapper.selectByPrimaryKey(bgmId);

        bgmMapper.deleteByPrimaryKey(bgmId);

        Map<String, Object> map = new HashMap<>();
        map.put("operaType", BGMOperatorTypeEnum.DELETE.type);
        map.put("path", bgm.getPath());

        zkCurator.sendBgmOperator(bgmId, JsonUtils.objectToJson(map));

    }

    @Override
    public PagedResult queryReportList(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        List<Reports> reportsList = usersReportCustomMapper.selectAllVideoReport();

        PageInfo<Reports> pageList = new PageInfo<>(reportsList);

        PagedResult prid = new PagedResult();
        prid.setTotal(pageList.getPages());
        prid.setRows(reportsList);
        prid.setPage(page);
        prid.setRecords(pageList.getTotal());

        return prid;
    }

    @Override
    public void updateVideoStatus(String videoId, Integer status) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setStatus(status);

        videosMapper.updateByPrimaryKeySelective(video);
    }

}
