package com.nnxy.entity;

import com.guoxiaoxing.phoenix.core.model.MediaEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 问题卡实体类
 */
public class QuesCard implements Serializable {
    private Integer quesCardId;//问题卡id
    private Integer userId;//用户id
    private String title;//问题卡标题
    private List<String> imgBase64;//图片
    private String putoutTime;//发布时间
    private int likeNum;//点赞数
    private int commentNum;//评论数


    @Override
    public String toString() {
        return "QuesCard{" +
                "quesCardId=" + quesCardId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", putoutTime='" + putoutTime + '\'' +
                ", likeNum=" + likeNum +
                ", commentNum=" + commentNum +
                ", imgBase64=" + imgBase64 +
                '}';
    }

    public Integer getQuesCardId() {
        return quesCardId;
    }

    public void setQuesCardId(Integer quesCardId) {
        this.quesCardId = quesCardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(List<String> imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getPutoutTime() {
        return putoutTime;
    }

    public void setPutoutTime(String putoutTime) {
        this.putoutTime = putoutTime;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }



}
