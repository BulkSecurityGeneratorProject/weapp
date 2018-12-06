import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParams } from 'app/shared/model/params.model';

type EntityResponseType = HttpResponse<IParams>;
type EntityArrayResponseType = HttpResponse<IParams[]>;

@Injectable({ providedIn: 'root' })
export class ParamsService {
    private resourceUrl = SERVER_API_URL + 'api/params';

    constructor(private http: HttpClient) {}

    create(params: IParams): Observable<EntityResponseType> {
        return this.http.post<IParams>(this.resourceUrl, params, { observe: 'response' });
    }

    update(params: IParams): Observable<EntityResponseType> {
        return this.http.put<IParams>(this.resourceUrl, params, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParams>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParams[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
