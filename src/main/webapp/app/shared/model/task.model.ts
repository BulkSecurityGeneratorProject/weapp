import { IProject } from 'app/shared/model//project.model';
import { IPoint } from 'app/shared/model//point.model';

export interface ITask {
    id?: number;
    code?: string;
    name?: string;
    areaName?: string;
    project?: IProject;
    points?: IPoint[];
}

export class Task implements ITask {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public areaName?: string,
        public project?: IProject,
        public points?: IPoint[]
    ) {}
}
