'use strict';
describe("Authentication service test", function() {
    var auth_service, backend, authConstants;
    beforeEach(module('fmu.core'));
    beforeEach(inject(function(AuthService, $httpBackend, AUTH) {
        auth_service = AuthService;
        backend = $httpBackend;
        authConstants = AUTH;
    }));

    it("should initialize authservice", function() {
        expect(auth_service).toBeDefined();
        expect(backend).toBeDefined();
        expect(authConstants).toBeDefined();
    });

    it("should call backend to get userinfo with correct url", function() {
        auth_service.getUserInfo();
        expect(getCalledURl(backend)).toBe(authConstants.userInfo);
    });

    it("should call backend to change role with correct url", function() {
        auth_service.changeRole();
        expect(getCalledURl(backend)).toBe(authConstants.changeRole);
    });
});