/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { VtopicService } from 'app/entities/vtopic/vtopic.service';
import { IVtopic, Vtopic } from 'app/shared/model/vtopic.model';

describe('Service Tests', () => {
    describe('Vtopic Service', () => {
        let injector: TestBed;
        let service: VtopicService;
        let httpMock: HttpTestingController;
        let elemDefault: IVtopic;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(VtopicService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Vtopic(0, currentDate, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        creationDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Vtopic', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        creationDate: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        creationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Vtopic(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Vtopic', async () => {
                const returnedFromService = Object.assign(
                    {
                        creationDate: currentDate.format(DATE_TIME_FORMAT),
                        vtopictitle: 'BBBBBB',
                        vtopicdesc: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        creationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Vtopic', async () => {
                const returnedFromService = Object.assign(
                    {
                        creationDate: currentDate.format(DATE_TIME_FORMAT),
                        vtopictitle: 'BBBBBB',
                        vtopicdesc: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        creationDate: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Vtopic', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
