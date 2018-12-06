/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeappTestModule } from '../../../test.module';
import { SampleComponent } from 'app/entities/sample/sample.component';
import { SampleService } from 'app/entities/sample/sample.service';
import { Sample } from 'app/shared/model/sample.model';

describe('Component Tests', () => {
    describe('Sample Management Component', () => {
        let comp: SampleComponent;
        let fixture: ComponentFixture<SampleComponent>;
        let service: SampleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [SampleComponent],
                providers: []
            })
                .overrideTemplate(SampleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SampleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Sample(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.samples[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
