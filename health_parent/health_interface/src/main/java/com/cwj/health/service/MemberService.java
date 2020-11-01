package com.cwj.health.service;

import com.cwj.health.pojo.Member;

public interface MemberService {
    /**
     * 根据手机号码查询会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);
}
