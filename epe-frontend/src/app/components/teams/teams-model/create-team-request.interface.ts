import { CreateTeamUser } from './create-team-user.interface';

export interface CreateTeamRequest {
    name: string
    teamLeader: CreateTeamUser
}
