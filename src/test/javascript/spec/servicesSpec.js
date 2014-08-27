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

    ddescribe('EavropTableService', function(){
        var eavropTableservice, httpBackend;

        beforeEach(inject(function(_EavropTableService_, $httpBackend){
            eavropTableservice = _EavropTableService_;
            httpBackend = $httpBackend;
        }));

        it("should return all eavrops", function(){
            httpBackend.whenGET("http://fmu.se/eavrops").respond([
                {"ärende-id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                {"ärende-id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                {"ärende-id":"11221213", "typ":"TMU", "datum":"2014/10/12"}
            ]);
            eavropTableservice.getEavrops().then(function(result){
                expect(result).toEqual(
                    [{"ärende-id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                        {"ärende-id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                        {"ärende-id":"11221213", "typ":"TMU", "datum":"2014/10/12"}]);
            });
        });

        it("should return correct number of rows", function(){
            httpBackend.whenGET("http://fmu.se/eavrops").respond([
                {"ärende-id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                {"ärende-id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                {"ärende-id":"11221213", "typ":"TMU", "datum":"2014/10/12"}
            ]);
            eavropTableservice.getEavrops().then(function(result){
                expect(result.length).toEqual(3);
            });
        })

        it("should have non-null fields on each row", function(){
            httpBackend.whenGET("http://fmu.se/eavrops").respond([
                {"ärende-id":"12345", "typ":"TMU", "datum":"2014/11/12"},
                {"ärende-id":"678910", "typ":"SLU", "datum":"2014/12/12"},
                {"ärende-id":"11221213", "typ":"TMU", "datum":"2014/10/12"}
            ]);
            eavropTableservice.getEavrops().then(function(result){
                angular.forEach(result, function(value, key){
                    expect(value["datum"]).toBeTruthy;
                    expect(value["ärende-id"]).toBeTruthy;
                    expect(value["typ"]).toBeTruthy;
                })
            });
        })
    });
});


