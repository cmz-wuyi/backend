package com.sicnu.boot.mapper;

import com.sicnu.boot.pojo.Video;
import com.sicnu.boot.pojo.VideoExamine;
import com.sicnu.boot.vo.ExamineVideo;
import com.sicnu.boot.vo.VideoSelective;
import com.sicnu.boot.vo.VideoType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description:
 *
 * @author :  胡建华
 * Data:    2022/10/15 19:58
 */
@Mapper
public interface VideoMapper {
    /**
     * description: 通过选择，返回视频列表
     *
     * @return List<Video>
     * @author 胡建华
     * Date:  2022/10/15 20:04
     * @param videoSelective :
     */
    List<Video> getVideoListBySelective(VideoSelective videoSelective);

    /**
     * description: 通过id获取视频详细信息
     *
     * @param videoId:
     * @return Video
     * @author 胡建华
     * Date:  2022/10/16 16:27
     */
    Video getVideoByVideoId(Integer videoId);

    /**
     * description: 获取视频的类型
     *
     * @return List<VideoType>
     * @author 胡建华
     * Date:  2022/10/25 18:40
     */
    List<VideoType> getVideoType();

    /**
     * description: 收藏视频
     *
     * @param userId:
     * @param videoId:
     * @author 胡建华
     * Date:  2022/11/23 18:52
     */
    void collectVideo(Integer userId, Integer videoId);

    /**
     * description: 检查该视频是否被收藏了
     *
     * @param userId:
     * @param videoId:
     * @return int
     * @author 胡建华
     * Date:  2022/11/23 18:54
     */
    int checkCollectVideo(Integer userId, Integer videoId);

    /**
     * description: 取消收藏
     *
     * @param userId:
     * @param videoId:
     * @author 胡建华
     * Date:  2022/11/23 19:11
     */
    void deleteCollectVideo(Integer userId, Integer videoId);

    /**
     * description: 获取一个用户的收藏列表
     *
     * @param userId:
     * @return List<Video>
     * @author 胡建华
     * Date:  2022/11/23 19:32
     */
    List<Video> getCollectVideoList(Integer userId);

    /**
     * description: 获取视频类型的名字
     *
     * @param typeId:
     * @return String
     * @author 胡建华
     * Date:  2022/11/30 15:55
     */
    String getVideoTypeName(Integer typeId);

    /**
     * description: 上传视频
     *
     * @param videoExamine:
     * @author 胡建华
     * Date:  2022/11/30 16:03
     */
    void insertVideoExamine(VideoExamine videoExamine);

    /**
     * description: 删除指定视频
     *
     * @param videoId:
     * @author 胡建华
     * Date:  2022/11/30 16:34
     */
    void deleteVideoById(Integer videoId);

    /**
     * description: 获取审核列表
     *
     * @param examineStatus:
     * @return List<VideoExamine>
     * @author 胡建华
     * Date:  2022/11/30 17:08
     */
    List<VideoExamine> getVideoExamineList(Integer examineStatus);

    /**
     * description: 插入视频
     *
     * @param videoExamine:
     * @author 胡建华
     * Date:  2022/11/30 18:12
     */
    void insertVideo(VideoExamine videoExamine);

    /**
     * description: 插入资源
     *
     * @param videoExamine:
     * @author 胡建华
     * Date:  2022/11/30 18:16
     */
    void insertResource(VideoExamine videoExamine);

    /**
     * description: 审核视频
     *
     * @param examineVideo:
     * @author 胡建华
     * Date:  2022/11/30 18:23
     */
    void examineVideo(ExamineVideo examineVideo);

    /**
     * description: 获取审核信息
     *
     * @param examineId:
     * @return VideoExamine
     * @author 胡建华
     * Date:  2022/11/30 18:26
     */
    VideoExamine getVideoExamineById(Integer examineId);

    /**
     * description: 删除视频对应的资源
     *
     * @param resourceId:
     * @author 胡建华
     * Date:  2022/11/30 19:22
     */
    void deleteResource(Integer resourceId);

    /**
     * description: 通过名字模糊搜索视频
     *
     * @param name:
     * @return List<Video>
     * @author 胡建华
     * Date:  2022/12/13 20:06
     */
    List<Video> getVideoByName(String name);

    /**
     * description: 收藏数操作，
     *
     * @param videoId:
     * @param status:操作状态，0为加，1为减
     * @author 胡建华
     * Date:  2022/12/15 9:35
     */
    void updateCollectNum(Integer videoId,Integer status);

    /**
     * description: 视频浏览数加1
     *
     * @param videoId:
     * @author 胡建华
     * Date:  2022/12/15 9:41
     */
    void updateAddViewNum(Integer videoId);

    /**
     * description: 通过typeId获取视频
     *
     * @param typeId:
     * @return List<Video>
     * @author 胡建华
     * Date:  2022/12/15 19:37
     */
    List<Video> getVideoListByVideoType(Integer typeId);

    /**
     * description: 为评论数加1
     *
     * @param resourceId:
     * @author 胡建华
     * Date:  2022/12/16 12:07
     */
    void updateAddCommentNum(Integer resourceId);

    /**
     * description: 通过种类id获取数量
     *
     * @param typeId:
     * @return Integer
     * @author 胡建华
     * Date:  2022/12/21 9:19
     */
    Integer getVideoCountByVideoType(Integer typeId);

    /**
     * description: 通过资源id获取视频
     *
     * @param resourceId:
     * @return Video
     * @author 胡建华
     * Date:  2023/1/7 17:22
     */
    Video getVideoByResourceId(Integer resourceId);
}
