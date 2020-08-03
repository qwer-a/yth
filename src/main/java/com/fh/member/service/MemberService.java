package com.fh.member.service;

import com.fh.common.ServerResponse;
import com.fh.member.model.Member;

import java.util.List;

public interface MemberService {
    ServerResponse checkMemberName(String name);

    ServerResponse checkPhone(String phone);

    ServerResponse register(Member member);

    ServerResponse login(Member member);

    ServerResponse queryMemberList();

    ServerResponse toUpdate(Integer id);

    ServerResponse updateMember(Member member);

    ServerResponse deleteMember(Integer id);

    Member queryMemberById(Integer id);

    ServerResponse updateAddress(Integer id);

    ServerResponse moAddress(Integer id);
}
