import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Uprofile } from 'app/shared/model/uprofile.model';
import { UprofileService } from './uprofile.service';
import { UprofileComponent } from './uprofile.component';
import { UprofileDetailComponent } from './uprofile-detail.component';
import { UprofileUpdateComponent } from './uprofile-update.component';
import { UprofileDeletePopupComponent } from './uprofile-delete-dialog.component';
import { IUprofile } from 'app/shared/model/uprofile.model';

@Injectable({ providedIn: 'root' })
export class UprofileResolve implements Resolve<IUprofile> {
    constructor(private service: UprofileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Uprofile> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Uprofile>) => response.ok),
                map((uprofile: HttpResponse<Uprofile>) => uprofile.body)
            );
        }
        return of(new Uprofile());
    }
}

export const uprofileRoute: Routes = [
    {
        path: 'uprofile',
        component: UprofileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterpressApp.uprofile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'uprofile/:id/view',
        component: UprofileDetailComponent,
        resolve: {
            uprofile: UprofileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.uprofile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'uprofile/new',
        component: UprofileUpdateComponent,
        resolve: {
            uprofile: UprofileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.uprofile.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'uprofile/:id/edit',
        component: UprofileUpdateComponent,
        resolve: {
            uprofile: UprofileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.uprofile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const uprofilePopupRoute: Routes = [
    {
        path: 'uprofile/:id/delete',
        component: UprofileDeletePopupComponent,
        resolve: {
            uprofile: UprofileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterpressApp.uprofile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
