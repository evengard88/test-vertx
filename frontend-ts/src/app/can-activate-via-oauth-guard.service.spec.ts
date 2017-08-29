import { TestBed, inject } from '@angular/core/testing';

import { CanActivateViaOAuthGuardService } from './can-activate-via-oauth-guard.service';

describe('CanActivateViaOAuthGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CanActivateViaOAuthGuardService]
    });
  });

  it('should be created', inject([CanActivateViaOAuthGuardService], (service: CanActivateViaOAuthGuardService) => {
    expect(service).toBeTruthy();
  }));
});
