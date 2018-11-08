import { ICommunity } from 'app/shared/model//community.model';
import { IUmxm } from 'app/shared/model//umxm.model';

export interface ICeleb {
    id?: number;
    celebName?: string;
    communities?: ICommunity[];
    umxms?: IUmxm[];
}

export class Celeb implements ICeleb {
    constructor(public id?: number, public celebName?: string, public communities?: ICommunity[], public umxms?: IUmxm[]) {}
}
