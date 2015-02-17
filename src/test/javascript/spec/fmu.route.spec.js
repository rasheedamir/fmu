describe('fmu navigation', function() {
    var state, httpBackend, RESTURL;
    beforeEach(function() {
        module('fmu');
        inject(function($rootScope, $location, $state, $httpBackend, _RESTURL_) {
            state = $state;
            httpBackend = $httpBackend;
            RESTURL = _RESTURL_;
            routeHelper.setUp($rootScope, $location);
        });
    });

    it("should define dependencies", function() {
        expect(state).toBeDefined();
        expect(httpBackend).toBeDefined();
    });

    it("Should have correct default page title", function() {
    	routeHelper.goTo('/');
    	expect(routeHelper.rootScope.title).toEqual(state.current.title);
    });

    describe('Eavrop', function() {
        describe("Order", function() {
            it("should go to eavrop order page", function() {
                var eavropId = '1245253';
                var url = '/eavrop/' + eavropId + '/order/contents';
                httpBackend.expectGET(RESTURL.eavrop.replace(':eavropId', eavropId)).respond(200, {});
                httpBackend.expectGET(RESTURL.eavropPatient.replace(':eavropId', eavropId)).respond(200, {});
                httpBackend.expectGET(RESTURL.eavropOrder.replace(':eavropId', eavropId)).respond(200, {});

                routeHelper.goTo(url);
                
                httpBackend.flush();
                expect(state.current.name).toEqual('eavrop.order.contents');
                expect(routeHelper.rootScope.title).toEqual('Eavrop-content-title/Content');
            });
        });
    });
});