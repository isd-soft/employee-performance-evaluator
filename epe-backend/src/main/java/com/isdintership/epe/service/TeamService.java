package com.isdintership.epe.service;

import com.isdintership.epe.dto.SuccessResponse;

import java.util.List;

public interface TeamService {

    SuccessResponse createTeam(String name, String teamLeaderId, List<String> membersId);

}
