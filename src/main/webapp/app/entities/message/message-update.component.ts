import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { IUser, UserService } from 'app/core';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community';
import { IFollow } from 'app/shared/model/follow.model';
import { FollowService } from '../follow/follow.service';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from '../blockuser/blockuser.service';

import { Principal } from 'app/core';

@Component({
    selector: 'jhi-message-update',
    templateUrl: './message-update.component.html'
})
export class MessageUpdateComponent implements OnInit {
    private _message: IMessage;
    isSaving: boolean;

    users: IUser[];

    communities: ICommunity[];
    creationDate: string;

    follows: IFollow[];
    //    loggedProfile: IProfile[];
    loggedUser: IUser[];
    blockusers: IBlockuser[];

    currentAccount: any;
    isBlocked: boolean;
    //    loggedProfileId: number;
    loggedUserId: number;

    routeData: any;
    nameParamFollows: any;
    valueParamFollows: number;
    blockedByUser: string;

    alerts: any[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private messageService: MessageService,
        private userService: UserService,
        private communityService: CommunityService,
        //        private profileService: ProfileService,
        private followService: FollowService,
        private blockuserService: BlockuserService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ message }) => {
            this.message = message;
            this.creationDate = this.message.creationDate != null ? this.message.creationDate.format(DATE_TIME_FORMAT) : null;
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            //            console.log('CONSOLOG: M:ngOnInit & O: this.currentAccount : ',  this.currentAccount);
            //            console.log('CONSOLOG: M:ngOnInit & O: this.owner : ',  this.owner);
            this.myMessagesCommunities();
            this.currentLoggedUser();
        });
        //        this.userService.query().subscribe(
        //            (res: HttpResponse<IUser[]>) => {
        //                this.users = res.body;
        //            },
        //            (res: HttpErrorResponse) => this.onError(res.message)
        //        );
        //        this.communityService.query().subscribe(
        //            (res: HttpResponse<ICommunity[]>) => {
        //                this.communities = res.body;
        //            },
        //            (res: HttpErrorResponse) => this.onError(res.message)
        //        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.message.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
        if (this.message.id !== undefined) {
            this.subscribeToSaveResponse(this.messageService.update(this.message));
        } else {
            //            this.subscribeToSaveResponse(this.messageService.create(this.message));
            if (this.message.communityId !== undefined) {
                const query = {};
                query['cfollowingId.equals'] = this.message.communityId;
                this.followService.query(query).subscribe(
                    (res: HttpResponse<IFollow[]>) => {
                        this.follows = res.body;
                        console.log('CONSOLOG: M:save & O: this.follows : ', this.follows);
                        this.follows.forEach(follow => {
                            if (follow.followedId !== null) {
                                this.message.userId = follow.followedId;
                                console.log('CONSOLOG: M:save & O: this.message : ', this.message);
                                this.subscribeToSaveResponse(this.messageService.create(this.message));
                            }
                        });
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            } else {
                if (this.message.userId !== undefined) {
                    if (this.isBlocked === false) {
                        console.log('CONSOLOG: M:save & O: this.isBlockUser.length : NO-BLOCKED ', this.isBlockUser.length);
                        this.subscribeToSaveResponse(this.messageService.create(this.message));
                        //                    } else {
                        ////                        this.valueParamFollows = null;
                        ////                        this.jhiAlertService.error('BLOCKED BY USER', {type: 'warning', msg: 'BLOCKED BY USER'});
                        ////                        this.jhiAlertService.addAlert({type: 'warning', msg: 'BLOCKED BY USER', timeout: 10000}, []);
                        ////                        this.jhiAlertService.error(msg: 'BLOCKED BY USER');
                        //                        this.blockedByUser = 'BLOCKED BY USER';
                        //                        console.log('CONSOLOG: M:save & O: this.blockedByUser : ', this.blockedByUser);
                        ////                        this.onBlockedUserError(this.blockedByUser);
                    }
                }
            }
        }
    }

    myMessagesCommunities() {
        console.log('CONSOLOG: M:myMessagesCommunities & O: this.currentAccount : ', this.currentAccount);
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.communityService.query(query).subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private currentLoggedUser() {
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.userService.query(query).subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.loggedUser = res.body;
                this.loggedUser.forEach(user => {
                    this.loggedUserId = user.id;
                });
                this.isBlockUser().subscribe(
                    (res3: HttpResponse<IBlockuser[]>) => {
                        this.blockusers = res3.body;
                        console.log('CONSOLOG: M:currentLoggedProfile & O:  this.blockusers : ', this.blockusers);
                        if (this.blockusers.length > 0) {
                            this.isBlocked = true;
                            this.valueParamFollows = null;
                            this.onWarning('BLOCKED BY USER');
                            console.log('CONSOLOG: M:currentLoggedProfile & O:  this.isBlocked : ', this.isBlocked);
                            return this.blockusers[0];
                        }
                    },
                    (res3: HttpErrorResponse) => this.onError(res3.message)
                );
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private isBlockUser() {
        this.isBlocked = false;
        const query = {};
        if (this.currentAccount.id != null) {
            query['blockeduserId.in'] = this.loggedUserId;
            query['blockinguserId.in'] = Number(this.valueParamFollows);
        }
        console.log('CONSOLOG: M:isBlockUser & O: query : ', query);
        return this.blockuserService.query(query);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>) {
        result.subscribe((res: HttpResponse<IMessage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        console.log('CONSOLOG: M:onError & O:  errorMessage : ', errorMessage);
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private onWarning(errorMessage: string) {
        console.log('CONSOLOG: M:onWarning & O:  errorMessage : ', errorMessage);
        //        this.jhiAlertService.addAlert({type: 'warning', msg: errorMessage, timeout: 10000}, null, null);
        //        this.jhiAlertService.addAlert({type: 'warning', msg: errorMessage, timeout: 10000}, []);
        //        this.jhiAlertService.warning('TEST', {type: 'warning', msg: errorMessage});
        //        this.jhiAlertService.warning(errorMessage, null, null);
        //        this.jhiAlertService.addAlert(this.jhiAlertService.warning(errorMessage, null, null));
        // estas no dan errores
        //        this.jhiAlertService.addAlert({type: 'warning', msg: errorMessage, timeout: 5000}, []);
        //        this.jhiAlertService.addAlert({type: 'warning', msg: errorMessage, timeout: 5000}, null);
        this.alerts = [];
        this.jhiAlertService.error(errorMessage, null, null);
        console.log('CONSOLOG: M:onWarning & O:  this.alerts : ', this.alerts);
        this.alerts.push(
            this.jhiAlertService.addAlert(
                {
                    type: 'info',
                    msg: errorMessage,
                    timeout: 5000,
                    toast: false,
                    scoped: true
                },
                this.alerts
            )
        );
        console.log('CONSOLOG: M:onWarning & O:  this.alerts2 : ', this.alerts);
    }
    //
    //    private onBlockedUserError(blockedByUser: string) {
    //        console.log('CONSOLOG: M:onBlockedUserError & O: this.blockedByUser : ', blockedByUser);
    //        this.jhiAlertService.info(blockedByUser, null, null);
    //    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackCommunityById(index: number, item: ICommunity) {
        return item.id;
    }

    get message() {
        return this._message;
    }

    set message(message: IMessage) {
        this._message = message;
        this.creationDate = moment(message.creationDate).format(DATE_TIME_FORMAT);
    }
}
