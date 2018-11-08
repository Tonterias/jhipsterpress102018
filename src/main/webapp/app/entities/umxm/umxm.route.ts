import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Umxm } from 'app/shared/model/umxm.model';
import { UmxmService } from './umxm.service';
import { UmxmComponent } from './umxm.component';
import { UmxmDetailComponent } from './umxm-detail.component';
import { UmxmUpdateComponent } from './umxm-update.component';
import { UmxmDeletePopupComponent } from './umxm-delete-dialog.component';
import { IUmxm } from 'app/shared/model/umxm.model';

@Injectable({ providedIn: 'root' })
export class UmxmResolve implements Resolve<IUmxm> {
    constructor(private service: UmxmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Umxm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Umxm>) => response.ok),
                map((umxm: HttpResponse<Umxm>) => umxm.body)
            );
        }
        return of(new Umxm());
    }
}

export const umxmRoute: Routes = [
    {
        path: 'umxm',
        component: UmxmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterpressApp.umxm.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'umxm/:id/view',
        component: UmxmDetailComponent,
        resolve: {
            umxm: UmxmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.umxm.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'umxm/new',
        component: UmxmUpdateComponent,
        resolve: {
            umxm: UmxmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.umxm.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'umxm/:id/edit',
        component: UmxmUpdateComponent,
        resolve: {
            umxm: UmxmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.umxm.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const umxmPopupRoute: Routes = [
    {
        path: 'umxm/:id/delete',
        component: UmxmDeletePopupComponent,
        resolve: {
            umxm: UmxmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.umxm.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
