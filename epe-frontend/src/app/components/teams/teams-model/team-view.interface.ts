import { ShortUser } from './short-user.interface';

export interface TeamView {
    id: string
    name: string
    teamLeader: ShortUser
    members: ShortUser[]
}
