'use strict';

/* Constants */
angular.module('fmuClientApp')
    .constant('RESTURL', {
        eavrop: 'app/rest/eavrop/:eavropId',
        eavropDocuments: 'app/rest/eavrop/:eavropId/received-documents',
        eavropRequestedDocuments: 'app/rest/eavrop/:eavropId/requested-documents',
        eavropNotes: 'app/rest/eavrop/:eavropId/notes',
        eavropAllEvents: 'app/rest/eavrop/:eavropId/all-events',
        eavropOrder: 'app/rest/eavrop/:eavropId/order',
        eavropPatient: 'app/rest/eavrop/:eavropId/patient',
        eavropVardgivarenheter: 'app/rest/eavrop/:eavropId/vardgivarenheter',
        eavropAssignment: 'app/rest/eavrop/:eavropId/assign',
        eavropAccept: 'app/rest/eavrop/:eavropId/accept',
        eavropReject: 'app/rest/eavrop/:eavropId/reject',
        userInfo: 'app/rest/user',
        changeRole: 'app/rest/user/changerole'
    });