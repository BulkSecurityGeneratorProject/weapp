import { Moment } from 'moment';
import { ISampleUser } from 'app/shared/model//sample-user.model';
import { IPoint } from 'app/shared/model//point.model';
import { IParams } from 'app/shared/model//params.model';

export const enum Status {
    PLAN = 'PLAN',
    PROCESS = 'PROCESS',
    COMPLATE = 'COMPLATE'
}

export interface ISample {
    id?: number;
    code?: string;
    name?: string;
    type?: string;
    status?: Status;
    sampleDate?: Moment;
    dqy?: number;
    fx?: string;
    fs?: number;
    wd?: number;
    xdsd?: number;
    user1?: ISampleUser;
    user2?: ISampleUser;
    point?: IPoint;
    params?: IParams[];
}

export class Sample implements ISample {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public type?: string,
        public status?: Status,
        public sampleDate?: Moment,
        public dqy?: number,
        public fx?: string,
        public fs?: number,
        public wd?: number,
        public xdsd?: number,
        public user1?: ISampleUser,
        public user2?: ISampleUser,
        public point?: IPoint,
        public params?: IParams[]
    ) {}
}
