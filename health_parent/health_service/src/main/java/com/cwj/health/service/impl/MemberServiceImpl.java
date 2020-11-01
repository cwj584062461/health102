package com.cwj.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cwj.health.dao.MemberDao;
import com.cwj.health.pojo.Member;
import com.cwj.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;


@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 根据手机号码查询会员
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
