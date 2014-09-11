'use strict';

describe('Services Tests ', function () {

    beforeEach(module('fmuApp'));

    describe('AuthenticationSharedService', function () {
        var serviceTested,
            httpBackend,
            authServiceSpied;

        beforeEach(inject(function($httpBackend, AuthenticationSharedService, authService) {
            serviceTested = AuthenticationSharedService;
            httpBackend = $httpBackend;
            authServiceSpied = authService;
            //Request on app init
            httpBackend.expectGET('i18n/en.json').respond(200, '');
        }));
        //make sure no expectations were missed in your tests.
        //(e.g. expectGET or expectPOST)
        afterEach(function() {
            httpBackend.verifyNoOutstandingExpectation();
            httpBackend.verifyNoOutstandingRequest();
        });

        it('should call backend on logout then call authService.loginCancelled', function(){
            //GIVEN
            //set up some data for the http call to return and test later.
            var returnData = { result: 'ok' };
            //expectGET to make sure this is called once.
            httpBackend.expectGET('app/logout').respond(returnData);        

            //Set spy
            spyOn(authServiceSpied, 'loginCancelled');

             //WHEN
            serviceTested.logout();
            //flush the backend to "execute" the request to do the expectedGET assertion.
            httpBackend.flush();

            //THEN
            expect(authServiceSpied.loginCancelled).toHaveBeenCalled();
        });

    });

    describe('EavropService', function(){
        var eavropservice, httpBackend;

        beforeEach(inject(function(_EavropService_, $httpBackend, _RESTURL_){
            eavropservice = _EavropService_;
            httpBackend = $httpBackend;
            // Mock http response
            httpBackend.whenGET(_RESTURL_.eavrop).respond([
                {"id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                {"id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                {"id":"11221213", "typ":"TMU", "datum":"2014/10/12"}
            ]);
        }));

        it("should return all eavrops", function(){
            eavropservice.getEavrops().then(function(result){
                expect(result).toEqual(
                    [{"id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                        {"id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                        {"id":"11221213", "typ":"TMU", "datum":"2014/10/12"}]);
            });
        });

        it("should return correct number of rows", function(){
            eavropservice.getEavrops().then(function(result){
                expect(result.length).toEqual(3);
            });
        })

        it("should have non-null fields on each row", function(){
            eavropservice.getEavrops().then(function(result){
                angular.forEach(result, function(value, key){
                    expect(value["datum"]).toBeTruthy;
                    expect(value["id"]).toBeTruthy;
                    expect(value["typ"]).toBeTruthy;
                })
            });
        })
    });
});


