import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISampleUser } from 'app/shared/model/sample-user.model';

type EntityResponseType = HttpResponse<ISampleUser>;
type EntityArrayResponseType = HttpResponse<ISampleUser[]>;

@Injectable({ providedIn: 'root' })
export class SampleUserService {
    private resourceUrl = SERVER_API_URL + 'api/sample-users';

    constructor(private http: HttpClient) {}

    create(sampleUser: ISampleUser): Observable<EntityResponseType> {
        return this.http.post<ISampleUser>(this.resourceUrl, sampleUser, { observe: 'response' });
    }

    update(sampleUser: ISampleUser): Observable<EntityResponseType> {
        return this.http.put<ISampleUser>(this.resourceUrl, sampleUser, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISampleUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISampleUser[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
