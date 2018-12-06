/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeappTestModule } from '../../../test.module';
import { SampleUserDetailComponent } from 'app/entities/sample-user/sample-user-detail.component';
import { SampleUser } from 'app/shared/model/sample-user.model';

describe('Component Tests', () => {
    describe('SampleUser Management Detail Component', () => {
        let comp: SampleUserDetailComponent;
        let fixture: ComponentFixture<SampleUserDetailComponent>;
        const route = ({ data: of({ sampleUser: new SampleUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [SampleUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SampleUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SampleUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sampleUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
