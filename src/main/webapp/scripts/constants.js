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
        userInfo: 'app/rest/user',
        changeRole: 'app/rest/user/changerole'
    })
    .constant('EAVROP_TABLE', {
        dateFormat: 'yyyy-MM-dd',
        sortKeyMap: {
            // since pagination is done in backend hibernate sort is based on eavrops field values mapping
            // is needed when going from DTO key to actual eavrop fieldkey

            arendeId: 'arendeId',
            utredningType: 'utredningType',
            status: 'eavropState'
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
    .constant('UTREDNING', {
        dateFormat: 'yyyy-MM-dd',
        statusMapping: {
            INTYG_APPROVED: 'Intyg godkänt',
            INTYG_COMPLEMENT_REQUEST: 'Intyg kompleteras',
            INTYG_SIGNED: 'Intyg signeras',
            EAVROP_APPROVED: 'Utredningen godkänts',
            EAVROP_COMPENSATION_APPROVED: 'Utredningens godkänts för utbetalning',
            UNKNOWN: 'Okänt handelse',
            EXAMINATION: 'Examination',
            BREIFING_WITH_CITIZEN: 'Möte med patient',
            INTERNAL_WORK: 'Internt arbete'
        },

        handelseMapping: {
            BOOKED: 'Bokat',
            PERFORMED: 'Genomfört',
            CANCELLED_NOT_PRESENT: 'Patient uteblev',
            CANCELLED_BY_CARE_GIVER: 'Besök avbokat av utförare',
            CANCELLED_LT_48_H: 'Besök avbokat <48h',
            CANCELLED_GT_48_H: 'Besök avbokat >48h',
            CANCELLED_LT_96_H: 'Besök avbokat <96h',
            CANCELLED_GT_96_H: 'Besök avbokat >96h'
        },

        tolkMapping: {
            INTERPPRETER_BOOKED: 'Bokat',
            INTERPRETATION_PERFORMED: 'Tolkning genomförd',
            INTERPPRETER_CANCELED: 'Tolk avbokad',
            INTERPPRETER_NOT_PRESENT: 'Tolk uteblev',
            INTERPPRETER_PRESENT_BUT_NOT_USED: 'Tolk anlänt, men tolkning inte använd'
        },

        editableEvents: {
            examination: 'EXAMINATION',
            briefing: 'BREIFING_WITH_CITIZEN',
            internalWork: 'INTERNAL_WORK'
        },

        errors: {
            cannotCreateBooking: 'Bookningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda'
        }
    })
    .constant('EAVROP_STATUS', {
        notAccepted: 'NOT_ACCEPTED',
        accepted: 'ACCEPTED',
        completed: 'COMPLETED'
    })
    .constant('EAVROP_NOTES', {
        cannotAdd: 'Anteckningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda',
        cannotRemove: 'Fel uppstod vid bortagning av anteckning, detta kan beror på att du inte har rätt behörighet att genomföra denna förfrågan'
    });
