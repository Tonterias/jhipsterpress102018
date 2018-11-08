import { ICommunity } from 'app/shared/model//community.model';
import { IUmxm } from 'app/shared/model//umxm.model';

export interface IInterest {
    id?: number;
    interestName?: string;
    communities?: ICommunity[];
    umxms?: IUmxm[];
}

export class Interest implements IInterest {
    constructor(public id?: number, public interestName?: string, public communities?: ICommunity[], public umxms?: IUmxm[]) {}
}
