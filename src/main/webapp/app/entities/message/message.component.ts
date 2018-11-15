import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../community/community.service';
import { IUser, UserService } from 'app/core';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from './message.service';

import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-message',
    templateUrl: './message.component.html'
})
export class MessageComponent implements OnInit, OnDestroy {
    currentAccount: any;
    messages: IMessage[];
    communities: ICommunity[];
    users: IUser[];
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
    //    paramMessageProfileId: any;
    paramMessageUserId: any;
    owner: any;
    isAdmin: boolean;
    arrayAux = [];
    arrayIds = [];

    constructor(
        private messageService: MessageService,
        private communityService: CommunityService,
        private userService: UserService,
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
        this.activatedRoute.queryParams.subscribe(params => {
            this.paramMessageUserId = params.userIdEquals;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        console.log('CONSOLOG: M:loadAll & O: this.paramMessageUserId.id : ', this.paramMessageUserId);
        const query = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        if (this.paramMessageUserId != null) {
            query['userId.equals'] = this.paramMessageUserId;
        }
        this.messageService
            .query(query)
            .subscribe(
                (res: HttpResponse<IMessage[]>) => this.paginateMessages(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        if (this.currentSearch) {
            this.messageService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IMessage[]>) => this.paginateMessages(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/message'], {
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
            '/message',
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
            '/message',
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
            //            this.myMessagesCommunities();
        });
        this.registerChangeInMessages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMessage) {
        return item.id;
    }

    registerChangeInMessages() {
        this.eventSubscriber = this.eventManager.subscribe('messageListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    myMessagesCommunities() {
        console.log('CONSOLOG: M:myMessagesCommunities & O: this.currentAccount.id : ', this.currentAccount.id);
        const query = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.communityService.query(query).subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
                console.log('CONSOLOG: M:myMessagesCommunities & O: this.communities : ', this.communities);
                this.communitiesMessages();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        console.log('CONSOLOG: M:myMessagesCommunities & VOOOOOOY myUserMessages');
        this.myUserMessages();
    }

    private myUserMessages() {
        const query = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        if (this.currentAccount.id != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.messageService.query(query).subscribe(
            (res: HttpResponse<IMessage[]>) => {
                this.messages = res.body;
                console.log('CONSOLOG: M:myUserMessages & O: this.messages : ', this.messages);
                //                    this.myMessagesProfiles(); // ????????????????????????????????????????????????????????????????????
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private communitiesMessages() {
        const query = {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        };
        if (this.communities != null) {
            const arrayCommmunities = [];
            this.communities.forEach(community => {
                arrayCommmunities.push(community.id);
            });
            query['communityId.in'] = arrayCommmunities;
        }
        this.messageService.query(query).subscribe(
            (res: HttpResponse<IMessage[]>) => {
                console.log('CONSOLOG: M:profilesMessages & O: this.messages1 Users messages: ', this.messages);
                console.log('CONSOLOG: M:profilesMessages & O: this.messages2 Comm messages: ', res.body);
                this.messages = this.filterMessages(this.messages.concat(res.body));
                this.paginateMessages(this.messages, res.headers);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private filterMessages(messages) {
        this.arrayAux = [];
        this.arrayIds = [];
        messages.map(x => {
            if (this.arrayIds.length >= 1 && this.arrayIds.includes(x.id) === false) {
                this.arrayAux.push(x);
                this.arrayIds.push(x.id);
            } else if (this.arrayIds.length === 0) {
                this.arrayAux.push(x);
                this.arrayIds.push(x.id);
            }
        });
        console.log('CONSOLOG: M:filterMessages & O: this.messages : ', this.arrayIds, this.arrayAux);
        return this.arrayAux;
    }

    private paginateMessages(data: IMessage[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.messages = data;
        console.log('CONSOLOG: M:paginateFollows & O: this.messages : ', this.messages);
        console.log('CONSOLOG: M:paginateFollows & O: this.owner : ', this.owner);
        console.log('CONSOLOG: M:paginateFollows & O: this.isAdmin : ', this.isAdmin);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
