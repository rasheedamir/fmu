'use strict';

describe('Services Tests ', function () {
    var eavropservice, urlBuilder, eavropStatus, $httpBackend,
        response = [{'testId': 'test'}];

    beforeEach(module('fmuClientApp'));
    beforeEach(inject(function (EavropService, RestUrlBuilderService, EAVROP_STATUS, _$httpBackend_) {
        eavropservice = EavropService;
        urlBuilder = RestUrlBuilderService;
        eavropStatus = EAVROP_STATUS;
        $httpBackend = _$httpBackend_;
    }));

    it('should initialize the service', function () {
        expect(eavropservice).toBeDefined();
        expect(urlBuilder).toBeDefined();
        expect(eavropStatus).toBeDefined();
        expect($httpBackend).toBeDefined();
    });
});