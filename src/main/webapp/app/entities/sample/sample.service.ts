import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISample } from 'app/shared/model/sample.model';

type EntityResponseType = HttpResponse<ISample>;
type EntityArrayResponseType = HttpResponse<ISample[]>;

@Injectable({ providedIn: 'root' })
export class SampleService {
    private resourceUrl = SERVER_API_URL + 'api/samples';

    constructor(private http: HttpClient) {}

    create(sample: ISample): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sample);
        return this.http
            .post<ISample>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(sample: ISample): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sample);
        return this.http
            .put<ISample>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISample>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISample[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(sample: ISample): ISample {
        const copy: ISample = Object.assign({}, sample, {
            sampleDate: sample.sampleDate != null && sample.sampleDate.isValid() ? sample.sampleDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.sampleDate = res.body.sampleDate != null ? moment(res.body.sampleDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((sample: ISample) => {
            sample.sampleDate = sample.sampleDate != null ? moment(sample.sampleDate) : null;
        });
        return res;
    }
}
