import { ICommunity } from 'app/shared/model//community.model';
import { IUmxm } from 'app/shared/model//umxm.model';

export interface IActivity {
    id?: number;
    activityName?: string;
    communities?: ICommunity[];
    umxms?: IUmxm[];
}

export class Activity implements IActivity {
    constructor(public id?: number, public activityName?: string, public communities?: ICommunity[], public umxms?: IUmxm[]) {}
}
