/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterpressTestModule } from '../../../test.module';
import { UmxmDetailComponent } from 'app/entities/umxm/umxm-detail.component';
import { Umxm } from 'app/shared/model/umxm.model';

describe('Component Tests', () => {
    describe('Umxm Management Detail Component', () => {
        let comp: UmxmDetailComponent;
        let fixture: ComponentFixture<UmxmDetailComponent>;
        const route = ({ data: of({ umxm: new Umxm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterpressTestModule],
                declarations: [UmxmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UmxmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UmxmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.umxm).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
