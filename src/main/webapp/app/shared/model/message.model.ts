import { Moment } from 'moment';

export interface IMessage {
    id?: number;
    creationDate?: Moment;
    messageText?: string;
    isDelivered?: boolean;
    userId?: number;
    communityId?: number;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public messageText?: string,
        public isDelivered?: boolean,
        public userId?: number,
        public communityId?: number
    ) {
        this.isDelivered = this.isDelivered || false;
    }
}
