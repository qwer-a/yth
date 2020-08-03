package com.fh.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import java.util.List;

public interface MemberMapper extends BaseMapper<Member> {
    List<Member> queryMemberList();

    Member toUpdate(Integer id);

    void updateMember(Member member);

    void deleteMember(Integer id);

    void updateAddress(Integer id);

    void moAddress(Integer id);
}
