export interface AssessmentInformation {
  assessmentTitle: string,
  assessmentId: string,
  evaluatedUserId: string,
  status: string,
  performedOnUser: string,
  performedByUser: string,
  performedTime: Date
  reason: string
}
