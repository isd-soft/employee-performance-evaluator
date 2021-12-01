export interface AssessmentInformation {
  assessmentTitle: string,
  assessmentId: string,
  evaluatedUserId: string,
  evaluatedUserFullName: string,
  status: string,
  currentStatus: string,
  performedOnUser: string,
  performedByUser: string,
  performedTime: Date,
  reason: string,
  feedbackAuthorsIds: string[]
}
