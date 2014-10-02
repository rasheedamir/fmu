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
            acceptedDate: '2014-02-02',
            documentsDeliveryDate: '2014-02-02',
            investigationAcceptedDate: '2014-02-02',
            certificateSentDate: '2014-02-02',
            investigationNoDays: 7,
            noDeviations: 5,
            type: 'TMU',
            interpreter: 'italienska',
            investigator: {
                org: 'Stockholms läns landsting',
                unit: 'Danderyds sjukhus',
            },
            orderer:{
                lfc: 'Nordväst, Sundbyberg',
                name: 'Per Handläggarsson',
                phone: '070-112233',
                email: 'namn@epost.se',
                org: 'Försäkringskassan'
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
                {name: 'Sassam', regDate: '2014-01-01', regBy: {name: 'Dora Doktoren', unit: 'Danderyds sjukhus'}}
            ],
            reqDocuments: [
                {name: 'Intyg Y', reqDate: '2014-01-01', comment: 'En kommentar', reqBy: {name: 'Dora Doktoren', unit: 'Danderyds sjukhus'}, reqTo: {name: 'Per handläggarsson', unit: 'Försäkringskassan'}}
            ],
            notes: [
                {type: 'Länk', content: 'http://www.lank.com', createdBy: {name: 'Per handläggarsson', role: 'Handläggare', org: 'Försäkringskassan'}, createdDate: '2014-01-01'},
                {type: 'Länk', content: 'http://www.lank.com', createdBy: {name: 'Per handläggarsson', role: 'Handläggare', org: 'Försäkringskassan'}, createdDate: '2014-01-01'}
            ]
        },
    ];

    function getEavropById(id){
        for (var i=0; i < eavrops.length; ++i) {
            if(eavrops[i].eavropId === id){
                return eavrops[i];
            }
        }
    }
    var EAVROP_PATTERN = /\/eavrop\/(\d+)/;
    var EAVROP_DOCUMENTS_PATTERN = /^\/eavrop\/(\d+)\/documents$/;
    var EAVROP_REQ_DOCUMENTS_PATTERN = /^\/eavrop\/(\d+)\/requested-documents$/;
    var EAVROP_NOTES_PATTERN = /^\/eavrop\/(\d+)\/notes/;


    $httpBackend.whenPOST(EAVROP_REQ_DOCUMENTS_PATTERN).respond(function(method, url, data){
        var id = EAVROP_PATTERN.exec(url)[1];
        getEavropById(parseInt(id)).reqDocuments.push(angular.fromJson(data));
        return [200, {}, {}];
    });
    $httpBackend.whenPOST(EAVROP_DOCUMENTS_PATTERN).respond(function(method, url, data){
        var id = EAVROP_PATTERN.exec(url)[1];
        getEavropById(parseInt(id)).documents.push(angular.fromJson(data));
        return [200, {}, {}];
    });
    $httpBackend.whenGET(EAVROP_DOCUMENTS_PATTERN).respond(function(method, url){
        var id = EAVROP_PATTERN.exec(url)[1];
        return [200, getEavropById(parseInt(id)).documents, {}];
    });
    $httpBackend.whenGET(EAVROP_REQ_DOCUMENTS_PATTERN).respond(function(method, url){
        var id = EAVROP_PATTERN.exec(url)[1];
        return [200, getEavropById(parseInt(id)).reqDocuments, {}];
    });
    $httpBackend.whenGET(EAVROP_NOTES_PATTERN).respond(function(method, url){
        var id = EAVROP_PATTERN.exec(url)[1];
        return [200, getEavropById(parseInt(id)).notes, {}];
    });

    $httpBackend.whenGET(EAVROP_PATTERN).respond(function(method, url){
        var id = EAVROP_PATTERN.exec(url)[1];
        return [200, getEavropById(parseInt(id)), {}];
    });
    $httpBackend.whenGET('/eavrop').respond(eavrops);
});

//Everything else should pass through to server
angular.module('fmuClientAppDev').run(function($httpBackend){
    $httpBackend.whenGET(/.*/).passThrough();
    $httpBackend.whenPOST(/.*/).passThrough();
    $httpBackend.whenDELETE(/.*/).passThrough();
    $httpBackend.whenPUT(/.*/).passThrough();
});


angular.module('fmuClientAppDev').run(function(AuthService){
    AuthService.userInfo.roles.push('ROLE_SAMORDNARE');
    AuthService.userInfo.roles.push('ROLE_UTREDARE');
});
