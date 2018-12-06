import { ISample } from 'app/shared/model//sample.model';

export interface ISampleUser {
    id?: number;
    name?: string;
    sample1S?: ISample[];
    sample2S?: ISample[];
}

export class SampleUser implements ISampleUser {
    constructor(public id?: number, public name?: string, public sample1S?: ISample[], public sample2S?: ISample[]) {}
}
