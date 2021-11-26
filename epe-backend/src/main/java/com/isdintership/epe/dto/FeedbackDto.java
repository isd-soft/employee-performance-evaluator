package com.isdintership.epe.dto;

import com.isdintership.epe.entity.Feedback;
import lombok.Data;

@Data
public class FeedbackDto {
    private String id;
    private String authorId;
    private String authorFullName;
    private String context;
    private String assessmentId;

    public static FeedbackDto fromFeedback(Feedback feedback) {

        FeedbackDto feedbackDto = new FeedbackDto();

        feedbackDto.setId(feedback.getId());
        feedbackDto.setAuthorId(feedback.getAuthorId());
        feedbackDto.setAuthorFullName(feedback.getAuthorFullName());
        feedbackDto.setContext(feedback.getContext());
        feedbackDto.setAssessmentId(feedback.getAssessment().getId());

        return feedbackDto;
    }
}
