import { ISample } from 'app/shared/model//sample.model';

export interface IParams {
    id?: number;
    type?: string;
    json?: any;
    sample?: ISample;
}

export class Params implements IParams {
    constructor(public id?: number, public type?: string, public json?: any, public sample?: ISample) {}
}
