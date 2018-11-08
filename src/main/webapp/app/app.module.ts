import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { Ng2Webstorage } from 'ngx-webstorage';

import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { JhipsterpressSharedModule } from 'app/shared';
import { JhipsterpressCoreModule } from 'app/core';
import { JhipsterpressAppRoutingModule } from './app-routing.module';
import { JhipsterpressHomeModule } from './home/home.module';
import { JhipsterpressAccountModule } from './account/account.module';
import { JhipsterpressEntityModule } from './entities/entity.module';
import * as moment from 'moment';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent, NavbarComponent, FooterComponent, PageRibbonComponent, ActiveMenuDirective, ErrorComponent } from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        JhipsterpressAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),
        JhipsterpressSharedModule,
        JhipsterpressCoreModule,
        JhipsterpressHomeModule,
        JhipsterpressAccountModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        JhipsterpressEntityModule
    ],
    declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true
        }
    ],
    bootstrap: [JhiMainComponent]
})
export class JhipsterpressAppModule {
    constructor(private dpConfig: NgbDatepickerConfig) {
        this.dpConfig.minDate = { year: moment().year() - 100, month: 1, day: 1 };
    }
}
