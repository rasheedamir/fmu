'use strict';

describe('RestUrlBuilderServiceTest', function () {
    var urlBuilder, statusConstant;
    beforeEach(module('fmuClientApp'));
    beforeEach(inject(function (RestUrlBuilderService, EAVROP_STATUS) {
        urlBuilder = RestUrlBuilderService;
        statusConstant = EAVROP_STATUS;
    }));


    it('should initialize the service', function(){
        expect(urlBuilder).toBeDefined();
        expect(statusConstant).toBeDefined();
    });
    
    it('should create corrent eavrop rest URL', function(){
        var url = urlBuilder.buildOverViewRestUrl(1,2,statusConstant.notAccepted,0,10,'arendeId', 'ASC');
        expect(url).toBe('/app/rest/eavrop/fromdate/1/todate/2/status/NOT_ACCEPTED/page/0/pagesize/10/sortkey/arendeId/sortorder/ASC');
    });
});