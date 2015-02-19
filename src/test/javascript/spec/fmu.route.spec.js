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
});