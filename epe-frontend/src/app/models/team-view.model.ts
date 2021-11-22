import { ShortUser } from "./short-user.model";

export interface TeamView {
    id?: string
    name: string
    teamLeader: ShortUser
    members: ShortUser[]
}
