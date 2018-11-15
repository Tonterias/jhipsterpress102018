import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IUprofile } from 'app/shared/model/uprofile.model';
import { UprofileService } from 'app/entities/uprofile';
import { FollowService } from '../follow/follow.service';
import { IFollow } from 'app/shared/model/follow.model';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from '../blockuser/blockuser.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from '../notification/notification.service';
import { IUser, UserService } from 'app/core';
import { IUmxm } from 'app/shared/model/umxm.model';
import { UmxmService } from '../umxm/umxm.service';
import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from '../interest/interest.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from '../activity/activity.service';
import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from '../celeb/celeb.service';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';

@Component({
    selector: 'jhi-uprofile-detail',
    templateUrl: './uprofile-detail.component.html'
})
export class UprofileDetailComponent implements OnInit {
    uprofile: IUprofile;
    uprofiles: IUprofile[];
    loggedProfile: IUprofile[];

    follows: IFollow[];
    private _follow: IFollow;

    blockusers: IBlockuser[];
    private _blockuser: IBlockuser;

    umxm: IUmxm;

    interests: IInterest[];
    activities: IActivity[];
    celebs: ICeleb[];

    consultedUser: IUser;
    consultedUserId: number;
    private _notification: INotification;
    notificationDate: string;
    notificationReason: any;

    currentAccount: any;
    isFollowing: boolean;
    isBlocked: boolean;
    loggedProfileId: number;
    creationDate: string;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private principal: Principal,
        private uprofileService: UprofileService,
        private umxmService: UmxmService,
        private interestService: InterestService,
        private activityService: ActivityService,
        private celebService: CelebService,
        private userService: UserService,
        private followService: FollowService,
        private blockuserService: BlockuserService,
        private notificationService: NotificationService,
        private jhiAlertService: JhiAlertService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ uprofile }) => {
            this.uprofile = uprofile;
            this.consultedUserId = uprofile.userId;
            console.log('CONSOLOG: M:ngOnInit & O: this.uprofile : ', this.uprofile);
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.currentLoggedProfile();
        });
        this.fillProfile();
        //        this.consultUmxm();
        this.isSaving = false;
        this.follow = new Object();
        this.blockuser = new Object();
        //        this.umxmInterests();
        //        this.umxmActivities();
        //        this.umxmCelebs();
    }

    private fillProfile() {
        console.log('CONSOLOG: M:fillProfile!!!!!!!!!!!!!!!!!!!!!!!!!! en fillProfile1');
        this.consultProfile().subscribe(
            (res: HttpResponse<IUser>) => {
                this.consultedUser = res.body;
                console.log('CONSOLOG: M:ngOnInit & O: this.consultedUser : ', this.consultedUser);
                console.log('CONSOLOG: M:fillProfile!!!!!!!!!!!!!!!!!!!!!!!!!! en fillProfile2');
                this.consultUmxm().subscribe(
                    (res2: HttpResponse<IUmxm>) => {
                        this.umxm = res2.body;
                        console.log('CONSOLOG: M:fillProfile & O: this.umxm : ', this.umxm);
                        this.umxmInterests();
                        this.umxmActivities();
                        this.umxmCelebs();
                    },
                    (res2: HttpErrorResponse) => this.onError(res2.message)
                );
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private consultProfile() {
        console.log('CONSOLOG: M:consultProfile!!!!!!!!!!!!!!!!!!!!!!!!!! en consultedUserId', this.consultedUserId);
        return this.userService.findById(this.consultedUserId);
    }

    private consultUmxm() {
        console.log('CONSOLOG: M:consultUmxm!!!!!!!!!!!!!!!!!!!!!!!!!! en consultUmxm', this.consultedUser.id);
        return this.umxmService.find(this.consultedUser.id);
    }

    private umxmInterests() {
        const query2 = {};
        query2['umxmId.equals'] = this.umxm.id;
        return this.interestService.query(query2).subscribe(
            (res: HttpResponse<IInterest[]>) => {
                this.interests = res.body;
                console.log('CONSOLOG: M:umxmInterests & O: this.interests : ', this.interests);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private umxmActivities() {
        const query3 = {};
        query3['umxmId.equals'] = this.umxm.id;
        return this.activityService.query(query3).subscribe(
            (res: HttpResponse<IActivity[]>) => {
                this.activities = res.body;
                console.log('CONSOLOG: M:umxmActivities & O: this.activities : ', this.activities);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private umxmCelebs() {
        const query4 = {};
        query4['umxmId.equals'] = this.umxm.id;
        return this.celebService.query(query4).subscribe(
            (res: HttpResponse<ICeleb[]>) => {
                this.celebs = res.body;
                console.log('CONSOLOG: M:umxmCelebs & O: this.celebs : ', this.celebs);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private currentLoggedProfile() {
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.uprofileService.query(query).subscribe(
            (res: HttpResponse<IUprofile[]>) => {
                this.loggedProfile = res.body;
                this.loggedProfile.forEach(profile => {
                    this.loggedProfileId = profile.id;
                });
                this.isFollower().subscribe(
                    (res2: HttpResponse<IFollow[]>) => {
                        this.follows = res2.body;
                        if (this.follows.length > 0) {
                            this.isFollowing = true;
                            // return this.follows[0];
                        }
                    },
                    (res2: HttpErrorResponse) => this.onError(res2.message)
                );
                this.isBlockUser().subscribe(
                    (res3: HttpResponse<IBlockuser[]>) => {
                        this.blockusers = res3.body;
                        if (this.blockusers.length > 0) {
                            this.isBlocked = true;
                            return this.blockusers[0];
                        }
                    },
                    (res3: HttpErrorResponse) => this.onError(res3.message)
                );
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private isFollower() {
        this.isFollowing = false;
        const query2 = {};
        if (this.currentAccount.id != null) {
            query2['followedId.in'] = this.loggedProfileId;
            query2['followingId.in'] = this.uprofile.id;
        }
        return this.followService.query(query2);
        /*.subscribe(
            ( res: HttpResponse<IFollow[]> ) => {
                this.follows = res.body;
                if ( this.follows.length > 0) {
                    this.isFollowing = true;
                    return this.follows[0];
                }
            },
            ( res: HttpErrorResponse ) => this.onError( res.message )
        );*/
    }

    following() {
        this.isSaving = true;
        this.follow.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        this.follow.followingId = this.uprofile.id;
        this.follow.followedId = this.loggedProfileId;
        if (this.isFollowing === false) {
            console.log('CONSOLOG: M:following & O: this.follow : ', this.follow);
            this.subscribeToSaveResponse(this.followService.create(this.follow));
            this.notificationReason = 'FOLLOWING';
            this.createNotification(this.notificationReason);
            this.isFollowing = true;
            this.reload();
        }
    }

    unFollowing() {
        if (this.isFollowing === true) {
            this.isFollower().subscribe(
                (res: HttpResponse<IFollow[]>) => {
                    this.follows = res.body;
                    if (this.follows.length > 0) {
                        this.isFollowing = true;
                        // return this.follows[0];
                        console.log('CONSOLOG: M:unFollowing & O: this.follows[0].id : ', this.follows[0].id);
                        this.followService.delete(this.follows[0].id).subscribe(response => {
                            this.notificationReason = 'UNFOLLOWING';
                            this.createNotification(this.notificationReason);
                        });
                        this.reload();
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    private createNotification(notificationReason) {
        this.notification = new Object();
        console.log('CONSOLOG: M:createNotification & O: this.notification : ', this.notification);
        console.log('CONSOLOG: M:createNotification & O: this.consultedUserId : ', this.consultedUserId);
        this.isSaving = true;
        this.notification.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        this.notification.notificationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        this.notification.notificationReason = notificationReason;
        //        this.notification.notificationText = notificationReason + ': ' + this.profile.lastName + ' ' + profile.lastName;
        this.notification.notificationText = notificationReason;
        this.notification.isDelivered = false;
        this.notification.userId = this.consultedUserId;
        if (this.notification.id !== undefined) {
            this.subscribeToSaveResponse2(this.notificationService.update(this.notification));
        } else {
            console.log('CONSOLOG: M:createNotification & O: this.notification: ', this.notification);
            this.subscribeToSaveResponse2(this.notificationService.create(this.notification));
        }
    }
    // BOTONES DE BLOCK Y UNBLOCK USER que más tarde pasaremos a los mensajes, pero ahora se quedan en el PROFILE

    private isBlockUser() {
        this.isBlocked = false;
        const query = {};
        if (this.currentAccount.id != null) {
            query['blockeduserId.in'] = this.loggedProfileId;
            query['blockinguserId.in'] = this.uprofile.id;
        }
        return this.blockuserService.query(query);
        //        .subscribe(
        //            ( res: HttpResponse<IBlockuser[]> ) => {
        //                this.blockusers = res.body;
        //                if ( this.blockusers.length > 0) {
        //                    this.isBlocked = true;
        //                    return this.blockusers[0];
        //                }
        //            },
        //            ( res: HttpErrorResponse ) => this.onError( res.message )
        //        );
    }

    blocking() {
        this.isSaving = true;
        this.blockuser.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        this.blockuser.blockeduserId = this.loggedProfileId;
        this.blockuser.blockinguserId = this.uprofile.id;
        if (this.isBlocked === false) {
            this.subscribeToSaveResponse(this.blockuserService.create(this.blockuser));
            this.isBlocked = true;
            this.reload();
        }
    }

    unBlocking() {
        if (this.isBlocked === true) {
            this.isBlockUser().subscribe(
                (res4: HttpResponse<IBlockuser[]>) => {
                    this.blockusers = res4.body;
                    if (this.blockusers.length > 0) {
                        this.isBlocked = true;
                        console.log('CONSOLOG: M:unBlocking & O2: this.blockusers[0].id : ', this.blockusers[0].id);
                        this.blockuserService.delete(this.blockusers[0].id).subscribe(response => {});
                        this.reload();
                    }
                },
                (res4: HttpErrorResponse) => this.onError(res4.message)
            );
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
        result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponse2(result: Observable<HttpResponse<INotification>>) {
        result.subscribe((res: HttpResponse<INotification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    reload() {
        window.location.reload();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    get follow() {
        return this._follow;
    }

    set follow(follow: IFollow) {
        this._follow = follow;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
    }

    get blockuser() {
        return this._blockuser;
    }

    set blockuser(blockuser: IBlockuser) {
        this._blockuser = blockuser;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
    }

    get notification() {
        return this._notification;
    }

    set notification(notification: INotification) {
        this._notification = notification;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
        this.notificationDate = moment().format(DATE_TIME_FORMAT);
        this.notificationReason = '';
    }
}
