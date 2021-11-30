import {TraceRequest} from "./trace-request.interface";
import {TraceResponse} from "./trace-response.interface";

export interface HttpTrace {
  timestamp: Date;
  method: string;
  timeTaken: string;
  status: number;
  uri: string;

  request: TraceRequest;
  response: TraceResponse;
}
