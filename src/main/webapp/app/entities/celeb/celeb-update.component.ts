import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from './celeb.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community';
import { IUmxm } from 'app/shared/model/umxm.model';
import { UmxmService } from 'app/entities/umxm';

@Component({
    selector: 'jhi-celeb-update',
    templateUrl: './celeb-update.component.html'
})
export class CelebUpdateComponent implements OnInit {
    celeb: ICeleb;
    isSaving: boolean;

    communities: ICommunity[];

    umxms: IUmxm[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private celebService: CelebService,
        private communityService: CommunityService,
        private umxmService: UmxmService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ celeb }) => {
            this.celeb = celeb;
        });
        this.communityService.query().subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.umxmService.query().subscribe(
            (res: HttpResponse<IUmxm[]>) => {
                this.umxms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.celeb.id !== undefined) {
            this.subscribeToSaveResponse(this.celebService.update(this.celeb));
        } else {
            this.subscribeToSaveResponse(this.celebService.create(this.celeb));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICeleb>>) {
        result.subscribe((res: HttpResponse<ICeleb>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
