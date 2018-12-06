/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeappTestModule } from '../../../test.module';
import { ParamsComponent } from 'app/entities/params/params.component';
import { ParamsService } from 'app/entities/params/params.service';
import { Params } from 'app/shared/model/params.model';

describe('Component Tests', () => {
    describe('Params Management Component', () => {
        let comp: ParamsComponent;
        let fixture: ComponentFixture<ParamsComponent>;
        let service: ParamsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [ParamsComponent],
                providers: []
            })
                .overrideTemplate(ParamsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParamsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParamsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Params(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.params[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
