import {EvaluationFieldView} from "./evaluation-field-view.interface";

export interface EvaluationGroupView {

  id: string,
  title: string,
  overallScore: string,
  evaluationFields: EvaluationFieldView[]

}
