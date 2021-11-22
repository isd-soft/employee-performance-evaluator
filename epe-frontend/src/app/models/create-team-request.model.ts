import { TeamMemberShort } from "./team-member-short.model";

export interface CreateTeamRequest {
    name: string
    teamLeader: TeamMemberShort
    teamMembers?: TeamMemberShort[]
}
