'use strict';

/* Constants */
angular.module('fmuClientApp')
    .constant('RESTURL', {
        eavrop: '/eavrop/:eavropId',
        eavropDocuments: '/eavrop/:eavropId/documents',
        eavropRequestedDocuments: '/eavrop/:eavropId/requested-documents',
        eavropNotes: '/eavrop/:eavropId/notes',
        eavropAllEvents: 'app/rest/eavrop/:eavropId/all-events',
        eavropOrder: 'app/rest/eavrop/:eavropId/order'
    })
    .constant('EAVROP_TABLE', {
        dateFormat: 'yyyy-MM-dd',
        sortKeyMap: {
            // since pagination is done in backend hibernate sort is based on eavrops field values mapping
            // is needed when going from DTO key to actual eavrop fieldkey

            arendeId: 'arendeId',
            utredningType: 'utredningType',
            bestallareEnhet: 'bestallaradministrator',
            bestallareOrganisation : 'bestallaradministrator',
            creationTime : 'documentsSentFromBestallareDateTime',
            patientCity : 'invanare',
            mottagarenOrganisation : 'currentAssignment',
            status : 'eavropState',
            antalDagarEfterForfragan : null,
            color : null,
            avikelser: null
        },
        statusMapping: {
            UNASSIGNED: 'Förfrågan om utredning har inkommit',
            ASSIGNED: 'Förfrågan tilldelas, inväntar acceptans',
            ACCEPTED: 'Förfrågan accepterade',
            ON_HOLD: 'Utredningen väntar',
            SENT: 'Sent ???',
            APPROVED: 'Utredningen godkänts av beställare',
            CLOSED: 'Utredningen är avslutad'
        },
        isCompletedMapping: {
            true: 'Ja',
            false: 'Nej'
        }
    })
    .constant('EAVROP_STATUS', {
        notAccepted: 'NOT_ACCEPTED',
        accepted: 'ACCEPTED',
        completed: 'COMPLETED',
    });
