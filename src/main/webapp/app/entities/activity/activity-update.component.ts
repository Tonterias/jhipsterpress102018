import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community';
import { IUmxm } from 'app/shared/model/umxm.model';
import { UmxmService } from 'app/entities/umxm';

import { Principal } from 'app/core';

@Component({
    selector: 'jhi-activity-update',
    templateUrl: './activity-update.component.html'
})
export class ActivityUpdateComponent implements OnInit {
    private _activity: IActivity;
    isSaving: boolean;

    communities: ICommunity[];

    umxms: IUmxm[];

    currentAccount: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private activityService: ActivityService,
        private communityService: CommunityService,
        private umxmService: UmxmService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activity }) => {
            this.activity = activity;
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.myCommunitiesActivities(this.currentAccount);
            this.myUserActivities(this.currentAccount);
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.activity.id !== undefined) {
            this.subscribeToSaveResponse(this.activityService.update(this.activity));
        } else {
            this.subscribeToSaveResponse(this.activityService.create(this.activity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>) {
        result.subscribe((res: HttpResponse<IActivity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private myUserActivities(currentAccount) {
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.umxmService.query(query).subscribe(
            (res: HttpResponse<IUmxm[]>) => {
                this.umxms = res.body;
                console.log('CONSOLOG: M:myProfiles & O: res.body : ', res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private myCommunitiesActivities(currentAccount) {
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.communityService.query(query).subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
                console.log('CONSOLOG: M:myCommunities & O: res.body : ', res.body);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        console.log('CONSOLOG: M:myCommunities & O: this.currentAccount.id : ', this.currentAccount.id);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCommunityById(index: number, item: ICommunity) {
        return item.id;
    }

    trackUmxmById(index: number, item: IUmxm) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    get activity() {
        return this._activity;
    }

    set activity(activity: IActivity) {
        this._activity = activity;
    }
}
