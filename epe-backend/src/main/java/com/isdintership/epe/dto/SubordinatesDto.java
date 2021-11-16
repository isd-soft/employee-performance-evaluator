package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Assessment;
import com.isdintership.epe.entity.User;
import com.isdintership.epe.util.AssessmentUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SubordinatesDto {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private List<AssessmentDto> assessmentsDtoList;

    public static SubordinatesDto fromUserTo(User user, List<Assessment> assessments) {
        SubordinatesDto subordinates = new SubordinatesDto();
        subordinates.setId(user.getId());
        subordinates.setEmail(user.getEmail());
        subordinates.setFirstname(user.getFirstname());
        subordinates.setLastname(user.getLastname());
        List<AssessmentDto> collect = assessments.stream()
                .map(AssessmentUtil::toAssessmentDto)
                .collect(Collectors.toList());
        subordinates.setAssessmentsDtoList(collect);

        return subordinates;
    }


}
