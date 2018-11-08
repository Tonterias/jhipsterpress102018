import { IInterest } from 'app/shared/model//interest.model';
import { IActivity } from 'app/shared/model//activity.model';
import { ICeleb } from 'app/shared/model//celeb.model';

export interface IUmxm {
    id?: number;
    userId?: number;
    interests?: IInterest[];
    activities?: IActivity[];
    celebs?: ICeleb[];
}

export class Umxm implements IUmxm {
    constructor(
        public id?: number,
        public userId?: number,
        public interests?: IInterest[],
        public activities?: IActivity[],
        public celebs?: ICeleb[]
    ) {}
}
