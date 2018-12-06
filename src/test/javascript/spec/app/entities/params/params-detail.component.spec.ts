/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WeappTestModule } from '../../../test.module';
import { ParamsDetailComponent } from 'app/entities/params/params-detail.component';
import { Params } from 'app/shared/model/params.model';

describe('Component Tests', () => {
    describe('Params Management Detail Component', () => {
        let comp: ParamsDetailComponent;
        let fixture: ComponentFixture<ParamsDetailComponent>;
        const route = ({ data: of({ params: new Params(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [WeappTestModule],
                declarations: [ParamsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParamsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParamsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.params).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
