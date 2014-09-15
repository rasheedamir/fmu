'use strict';

angular.module('fmuClientAppDev', ['fmuClientApp', 'ngMockE2E']);

angular.module('fmuClientAppDev').run(function($httpBackend){
    var phones = [{name: 'phone1'}, {name: 'phone2'}];

    // returns the current list of phones
    $httpBackend.whenGET('/phones').respond(phones);
});

//Everything else should pass through to server
angular.module('fmuClientAppDev').run(function($httpBackend){
    $httpBackend.whenGET(/.*/).passThrough();
    $httpBackend.whenPOST(/.*/).passThrough();
    $httpBackend.whenDELETE(/.*/).passThrough();
    $httpBackend.whenPUT(/.*/).passThrough();
});


