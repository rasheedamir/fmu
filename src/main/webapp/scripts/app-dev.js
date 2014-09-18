'use strict';

angular.module('fmuClientAppDev', ['fmuClientApp', 'ngMockE2E']);

angular.module('fmuClientAppDev').run(function($httpBackend){
    var phones = [{name: 'phone1'}, {name: 'phone2'}];

    // returns the current list of phones
    $httpBackend.whenGET('/phones').respond(phones);
});


angular.module('fmuClientAppDev').run(function($httpBackend){
    var eavrops = [
        { eavropId: 1, arendeId: 123 },
        { eavropId: 2, arendeId: 1234 },
        { eavropId: 3, arendeId: 12345 },
    ];

    function getEavropById(id){
        for (var i=0; i < eavrops.length; ++i) {
            if(eavrops[i].eavropId === id){
                return eavrops[i];
            }
        }
    }
    var GET_PATTERN = /\/eavrop\/(\d+)/;

    $httpBackend.whenGET('/eavrop').respond(eavrops);
    $httpBackend.whenGET(GET_PATTERN).respond(function(method, url){
        var id = GET_PATTERN.exec(url)[1];
        return [200, getEavropById(parseInt(id)), {}];
    });
});

//Everything else should pass through to server
angular.module('fmuClientAppDev').run(function($httpBackend){
    $httpBackend.whenGET(/.*/).passThrough();
$httpBackend.whenPOST(/.*/).passThrough();
$httpBackend.whenDELETE(/.*/).passThrough();
$httpBackend.whenPUT(/.*/).passThrough();
});


