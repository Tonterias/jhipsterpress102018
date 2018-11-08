/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterpressTestModule } from '../../../test.module';
import { UmxmUpdateComponent } from 'app/entities/umxm/umxm-update.component';
import { UmxmService } from 'app/entities/umxm/umxm.service';
import { Umxm } from 'app/shared/model/umxm.model';

describe('Component Tests', () => {
    describe('Umxm Management Update Component', () => {
        let comp: UmxmUpdateComponent;
        let fixture: ComponentFixture<UmxmUpdateComponent>;
        let service: UmxmService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterpressTestModule],
                declarations: [UmxmUpdateComponent]
            })
                .overrideTemplate(UmxmUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UmxmUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UmxmService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Umxm(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.umxm = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Umxm();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.umxm = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
