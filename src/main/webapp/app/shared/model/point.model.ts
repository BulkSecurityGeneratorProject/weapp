import { ITask } from 'app/shared/model//task.model';
import { ISample } from 'app/shared/model//sample.model';

export interface IPoint {
    id?: number;
    code?: string;
    name?: string;
    lng?: number;
    lat?: number;
    address?: string;
    task?: ITask;
    samples?: ISample[];
}

export class Point implements IPoint {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public lng?: number,
        public lat?: number,
        public address?: string,
        public task?: ITask,
        public samples?: ISample[]
    ) {}
}
