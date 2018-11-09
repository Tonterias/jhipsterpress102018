import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICeleb } from 'app/shared/model/celeb.model';
import { CelebService } from './celeb.service';

import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../community/community.service';
import { IUmxm } from 'app/shared/model/umxm.model';
import { UmxmService } from '../umxm/umxm.service';

import { Principal } from 'app/core';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-celeb',
    templateUrl: './celeb.component.html'
})
export class CelebComponent implements OnInit, OnDestroy {
    currentAccount: any;
    celebs: ICeleb[];
    communities: ICommunity[];
    umxms: IUmxm[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    owner: any;
    isAdmin: boolean;
    arrayAux = [];
    arrayIds = [];

    constructor(
        private celebService: CelebService,
        private communityService: CommunityService,
        private umxmService: UmxmService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.celebService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.celebService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICeleb[]>) => this.paginateCelebs(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/celeb'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/celeb',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/celeb',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.owner = account.id;
            this.principal.hasAnyAuthority(['ROLE_ADMIN']).then(result => {
                this.isAdmin = result;
            });
        });
        this.registerChangeInCelebs();
    }

    myCelebs() {
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.communityService.query(query).subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
                console.log('CONSOLOG: M:myActivities & O: this.communities : ', this.communities);
                this.communitiesCelebs();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private communitiesCelebs() {
        const query = {};
        if (this.communities != null) {
            const arrayCommmunities = [];
            this.communities.forEach(community => {
                arrayCommmunities.push(community.id);
            });
            query['communityId.in'] = arrayCommmunities;
        }
        this.celebService.query(query).subscribe(
            (res: HttpResponse<ICeleb[]>) => {
                this.celebs = res.body;
                console.log('CONSOLOG: M:communitiesActivities & O: this.celebs : ', this.celebs);
                this.myUserUmxm();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private myUserUmxm() {
        const query = {};
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.umxmService.query(query).subscribe(
            (res: HttpResponse<IUmxm[]>) => {
                this.umxms = res.body;
                console.log('CONSOLOG: M:myUserUmxm & O: this.umxms : ', this.umxms);
                this.myUmxmCelebs();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private myUmxmCelebs() {
        const query = {};
        if (this.umxms != null) {
            const arrayUmxms = [];
            this.umxms.forEach(umxms => {
                arrayUmxms.push(umxms.id);
            });
            query['umxmId.in'] = arrayUmxms;
        }
        this.celebService.query(query).subscribe(
            (res: HttpResponse<ICeleb[]>) => {
                //                        this.activities = this.activities.concat(res.body);
                this.celebs = this.filterActivities(this.celebs.concat(res.body));
                console.log('CONSOLOG: M:myUmxmActivities & O: this.celebs : ', this.celebs);
                this.paginateCelebs(this.celebs, res.headers);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private filterActivities(activities) {
        this.arrayAux = [];
        this.arrayIds = [];
        activities.map(x => {
            if (this.arrayIds.length >= 1 && this.arrayIds.includes(x.id) === false) {
                this.arrayAux.push(x);
                this.arrayIds.push(x.id);
            } else if (this.arrayIds.length === 0) {
                this.arrayAux.push(x);
                this.arrayIds.push(x.id);
            }
        });
        console.log('CONSOLOG: M:filterActivities & O: filterInterests', this.arrayIds, this.arrayAux);
        return this.arrayAux;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICeleb) {
        return item.id;
    }

    registerChangeInCelebs() {
        this.eventSubscriber = this.eventManager.subscribe('celebListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateCelebs(data: ICeleb[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.celebs = data;
        console.log('CONSOLOG: M:paginateActivities & O: this.celebs : ', this.celebs);
        console.log('CONSOLOG: M:paginateActivities & O: this.owner : ', this.owner);
        console.log('CONSOLOG: M:paginateActivities & O: this.isAdmin : ', this.isAdmin);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
