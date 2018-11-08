import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUmxm } from 'app/shared/model/umxm.model';

@Component({
    selector: 'jhi-umxm-detail',
    templateUrl: './umxm-detail.component.html'
})
export class UmxmDetailComponent implements OnInit {
    umxm: IUmxm;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ umxm }) => {
            this.umxm = umxm;
        });
    }

    previousState() {
        window.history.back();
    }
}
