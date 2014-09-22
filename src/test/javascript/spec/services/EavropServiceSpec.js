'use strict';

describe('Services Tests ', function () {

    beforeEach(module('fmuClientApp'));

    describe('EavropService', function(){
        var eavropservice, httpBackend;

        beforeEach(inject(function(_EavropService_, $httpBackend, _RESTURL_){
            eavropservice = _EavropService_;
            httpBackend = $httpBackend;
            // Mock http response
            httpBackend.whenGET(_RESTURL_.eavrop).respond([
                {'id':'12345', 'typ':'TMU', 'datum':'2014/11/12'},
                {'id':'678910', 'typ':'SLU', 'datum':'2014/12/12'},
                {'id':'11221213', 'typ':'TMU', 'datum':'2014/10/12'}
            ]);
        }));

        it('should return all eavrops', function(){
            eavropservice.getEavrops().then(function(result){
                expect(result).toEqual(
                    [{'id':'12345', 'typ':'TMU', 'datum':'2014/11/12'},
                        {'id':'678910', 'typ':'SLU', 'datum':'2014/12/12'},
                        {'id':'11221213', 'typ':'TMU', 'datum':'2014/10/12'}]);
            });
        });

        it('should return correct number of rows', function(){
            eavropservice.getEavrops().then(function(result){
                expect(result.length).toEqual(3);
            });
        });

        it('should have non-null fields on each row', function(){
            eavropservice.getEavrops().then(function(result){
                angular.forEach(result, function(value){
                    expect(value.datum).toBeTruthy();
                    expect(value.id).toBeTruthy();
                    expect(value.typ).toBeTruthy();
                });
            });
        });
    });
});