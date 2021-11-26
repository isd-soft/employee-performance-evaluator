import {EvaluationGroupView} from "./evaluation-group-view.interface";
import {PersonalGoalView} from "./personal-goal-view.interface";
import {DepartmentGoalView} from "./department-goal-view.interface";
import {FeedbackView} from "./feedback-view.interface";

export interface AssessmentView {

  id: string,
  title: string,
  description: string,
  jobTitle: string,
  startDate: string,
  endDate: string,
  status: string,
  overallScore: number,
  evaluationGroups: EvaluationGroupView[],
  personalGoals: PersonalGoalView[],
  departmentGoals: DepartmentGoalView[],
  feedbacks: FeedbackView[],

  userId: string

}
