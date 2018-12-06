import { IUser } from 'app/core/user/user.model';
import { ITask } from 'app/shared/model//task.model';

export interface IProject {
    id?: number;
    name?: string;
    user?: IUser;
    names?: ITask[];
}

export class Project implements IProject {
    constructor(public id?: number, public name?: string, public user?: IUser, public names?: ITask[]) {}
}
