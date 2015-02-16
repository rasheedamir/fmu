describe('fmu navigation', function() {
    var rootScope, httpBackend, location, state;
    beforeEach(function() {
        module('fmu');
        inject(function($rootScope, $httpBackend, $location, $state) {
            rootScope = $rootScope;
            httpBackend = $httpBackend;
            location = $location;
            state = $state;
        });
    });

    var goTo = function(url) {
        location.url(url);
        rootScope.$digest();
    };

    it("should define dependencies", function() {
        expect(rootScope).toBeDefined();
        expect(httpBackend).toBeDefined();
        expect(location).toBeDefined();
        expect(state).toBeDefined();
    });

    it("Should have correct default page title", function() {
    	goTo('/');
    	expect(rootScope.title).toEqual(state.current.title);
    });
});