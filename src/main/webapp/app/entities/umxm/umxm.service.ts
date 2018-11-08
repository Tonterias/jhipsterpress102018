import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUmxm } from 'app/shared/model/umxm.model';

type EntityResponseType = HttpResponse<IUmxm>;
type EntityArrayResponseType = HttpResponse<IUmxm[]>;

@Injectable({ providedIn: 'root' })
export class UmxmService {
    public resourceUrl = SERVER_API_URL + 'api/umxms';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/umxms';

    constructor(private http: HttpClient) {}

    create(umxm: IUmxm): Observable<EntityResponseType> {
        return this.http.post<IUmxm>(this.resourceUrl, umxm, { observe: 'response' });
    }

    update(umxm: IUmxm): Observable<EntityResponseType> {
        return this.http.put<IUmxm>(this.resourceUrl, umxm, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUmxm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUmxm[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUmxm[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
