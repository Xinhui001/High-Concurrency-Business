package com.youkeda.app.service;

import com.youkeda.app.model.Comment;

/**
 * @author 20891
 */
public interface CommentService {

    /**
     * 插入一条评价数据，返回成功的数量
     *
     * @param commentDO
     * @return 成功返回 1 / 失败返回 1
     */
    Long add(Comment commentDO);

}
