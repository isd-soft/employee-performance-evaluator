import {EvaluationGroupView} from "./evaluation-group-view.interface";

export interface AssessmentTemplateView {

  id: string,
  title: string,
  description: string,
  jobTitle: string,
  evaluationGroups: EvaluationGroupView[]

}
