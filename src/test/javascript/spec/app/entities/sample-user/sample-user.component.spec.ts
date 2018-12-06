/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WeappTestModule } from '../../../test.module';
import { SampleUserComponent } from 'app/entities/sample-user/sample-user.component';
import { SampleUserService } from 'app/entities/sample-user/sample-user.service';
import { SampleUser } from 'app/shared/model/sample-user.model';

describe('Component Tests', () => {
    describe('SampleUser Management Component', () => {
        let comp: SampleUserComponent;
        let fixture: ComponentFixture<SampleUserComponent>;
        let service: SampleUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [SampleUserComponent],
                providers: []
            })
                .overrideTemplate(SampleUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SampleUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SampleUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SampleUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sampleUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
