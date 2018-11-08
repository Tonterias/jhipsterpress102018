import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUmxm } from 'app/shared/model/umxm.model';
import { UmxmService } from './umxm.service';
import { IUser, UserService } from 'app/core';
import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from 'app/entities/interest';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';
import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from 'app/entities/celeb';

@Component({
    selector: 'jhi-umxm-update',
    templateUrl: './umxm-update.component.html'
})
export class UmxmUpdateComponent implements OnInit {
    umxm: IUmxm;
    isSaving: boolean;

    users: IUser[];

    interests: IInterest[];

    activities: IActivity[];

    celebs: ICeleb[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private umxmService: UmxmService,
        private userService: UserService,
        private interestService: InterestService,
        private activityService: ActivityService,
        private celebService: CelebService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ umxm }) => {
            this.umxm = umxm;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.interestService.query().subscribe(
            (res: HttpResponse<IInterest[]>) => {
                this.interests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.activityService.query().subscribe(
            (res: HttpResponse<IActivity[]>) => {
                this.activities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.celebService.query().subscribe(
            (res: HttpResponse<ICeleb[]>) => {
                this.celebs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.umxm.id !== undefined) {
            this.subscribeToSaveResponse(this.umxmService.update(this.umxm));
        } else {
            this.subscribeToSaveResponse(this.umxmService.create(this.umxm));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUmxm>>) {
        result.subscribe((res: HttpResponse<IUmxm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackInterestById(index: number, item: IInterest) {
        return item.id;
    }

    trackActivityById(index: number, item: IActivity) {
        return item.id;
    }

    trackCelebById(index: number, item: ICeleb) {
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
}
