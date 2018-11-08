import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterpressSharedModule } from 'app/shared';
import { JhipsterpressAdminModule } from 'app/admin/admin.module';
import {
    UmxmComponent,
    UmxmDetailComponent,
    UmxmUpdateComponent,
    UmxmDeletePopupComponent,
    UmxmDeleteDialogComponent,
    umxmRoute,
    umxmPopupRoute
} from './';

const ENTITY_STATES = [...umxmRoute, ...umxmPopupRoute];

@NgModule({
    imports: [JhipsterpressSharedModule, JhipsterpressAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [UmxmComponent, UmxmDetailComponent, UmxmUpdateComponent, UmxmDeleteDialogComponent, UmxmDeletePopupComponent],
    entryComponents: [UmxmComponent, UmxmUpdateComponent, UmxmDeleteDialogComponent, UmxmDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterpressUmxmModule {}
