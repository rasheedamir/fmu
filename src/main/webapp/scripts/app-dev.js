'use strict';

angular.module('fmuClientAppDev', ['fmuClientApp', 'ngMockE2E']);

angular.module('fmuClientAppDev').run(function($httpBackend){
    var phones = [{name: 'phone1'}, {name: 'phone2'}];

    // returns the current list of phones
    $httpBackend.whenGET('/phones').respond(phones);
});


angular.module('fmuClientAppDev').run(function($httpBackend){
    var eavrops = [
        {
            eavropId: 1,
            arendeId: 123,
            inquiryDate: '2014-01-02',
            type: 'TMU',
            interpreter: 'italienska',
            orderer:{
                lfc: 'Nordväst, Sundbyberg',
                name: 'Per Handläggarsson',
                phone: '070-112233',
                email: 'namn@epost.se',
            },
            patient:{
                initials: 'VW',
                gender: 'Kvinna',
                dobYear: '1962',
                residence: 'Karlshamn',
                details:{
                    socSecNo: 'ÅÅÅÅMMDD-xxxx',
                    name: 'Patient patientsson',
                    phone: '070-112233',
                    prevInvestigatedAt: 'Blommans vårdcentral',
                    sickLeaveClinic: 'Bli frisk kliniken',
                    sickLeaveDoctor: 'Doktor frisk',
                    specialNeeds: ''
                }
            },
            documents: [
            {
                name: 'Sassam',
                regBy: {
                    name: 'Dora Doktoren',
                    org: 'Danderyds sjukhus'
                },
                regDate: '2014-04-16'
            }
            ],
            reqAmendments: [
            {
                name: 'Intyg Y',
                reqFrom: {
                    name: 'Dora Doktoren',
                    org: 'Danderyds sjukhus'
                },
                reqTo: {
                    name: 'Per handläggarsson',
                    org: 'Försäkringskassan'
                },
                reqDate: '2014-04-16'
            }
            ]
        },
    ];
    function putEavrop(id, eavrop){
        for (var i=0; i < eavrops.length; ++i) {
            if(eavrops[i].eavropId === id){
                eavrops.splice(i, 1, eavrop);
                return eavrop;
            }
        }
    }

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

    $httpBackend.whenPUT(GET_PATTERN).respond(function(method, url, data){
        var id = GET_PATTERN.exec(url)[1];
        return [200, putEavrop(parseInt(id), angular.fromJson(data)), {}];
    });
});

//Everything else should pass through to server
angular.module('fmuClientAppDev').run(function($httpBackend){
    $httpBackend.whenGET(/.*/).passThrough();
    $httpBackend.whenPOST(/.*/).passThrough();
    $httpBackend.whenDELETE(/.*/).passThrough();
    $httpBackend.whenPUT(/.*/).passThrough();
});


angular.module('fmuClientAppDev').run(function(AuthService){
    AuthService.userInfo.roles.push("ROLE_SAMORDNARE");
    AuthService.userInfo.roles.push("ROLE_UTREDARE");
});
