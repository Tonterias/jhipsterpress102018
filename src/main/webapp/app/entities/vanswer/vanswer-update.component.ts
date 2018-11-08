import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IVanswer } from 'app/shared/model/vanswer.model';
import { VanswerService } from './vanswer.service';
import { IUser, UserService } from 'app/core';
import { IVquestion } from 'app/shared/model/vquestion.model';
import { VquestionService } from 'app/entities/vquestion';

@Component({
    selector: 'jhi-vanswer-update',
    templateUrl: './vanswer-update.component.html'
})
export class VanswerUpdateComponent implements OnInit {
    vanswer: IVanswer;
    isSaving: boolean;

    users: IUser[];

    vquestions: IVquestion[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private vanswerService: VanswerService,
        private userService: UserService,
        private vquestionService: VquestionService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ vanswer }) => {
            this.vanswer = vanswer;
            this.creationDate = this.vanswer.creationDate != null ? this.vanswer.creationDate.format(DATE_TIME_FORMAT) : null;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.vquestionService.query().subscribe(
            (res: HttpResponse<IVquestion[]>) => {
                this.vquestions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.vanswer.creationDate = this.creationDate != null ? moment(this.creationDate, DATE_TIME_FORMAT) : null;
        if (this.vanswer.id !== undefined) {
            this.subscribeToSaveResponse(this.vanswerService.update(this.vanswer));
        } else {
            this.subscribeToSaveResponse(this.vanswerService.create(this.vanswer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVanswer>>) {
        result.subscribe((res: HttpResponse<IVanswer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVquestionById(index: number, item: IVquestion) {
        return item.id;
    }
}
