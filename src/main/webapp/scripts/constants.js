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
            ON_HOLD: 'Inväntar beslut från beställare',
            SENT: 'Inväntar acceptans',
            APPROVED: 'Utredningen godkänts av beställare',
            CLOSED: 'Utredningen är avslutad',
            ONGOING: 'Utredningen är påbörjad'
        },
        isCompletedMapping: {
            true: 'Ja',
            false: 'Nej'
        },

        isCompensationApprovedMapping: {
            null : 'inväntar',
            true : 'Ja',
            false : 'Nej'
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
            EXAMINATION: 'Besök',
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
            INTERPRETER_BOOKED: 'Bokat',
            INTERPRETATION_PERFORMED: 'Tolkning genomförd',
            INTERPRETER_CANCELED: 'Tolk avbokad',
            INTERPRETER_NOT_PRESENT: 'Tolk uteblev',
            INTERPRETER_PRESENT_BUT_NOT_USED: 'Tolk anlänt, men tolkning inte använd'
        },

        editableEvents: {
            examination: 'EXAMINATION',
            briefing: 'BREIFING_WITH_CITIZEN',
            internalWork: 'INTERNAL_WORK'
        },

        eventsRequireConfirmation: {
            CANCELLED_GT_48_H: 'Besök avbokat >48h',
            CANCELLED_NOT_PRESENT: 'Patient uteblev',
            CANCELLED_LT_96_H: 'Besök avbokat <96h',
            CANCELLED_GT_96_H: 'Besök avbokat >96h'
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
    .constant('EAVROP_COMPENSATION', {
        statusMapping: {
            CANCELLED_NOT_PRESENT: 'Patient uteblev',
            CANCELLED_BY_CAREGIVER: 'Besök avbokat av utförare',
            CANCELLED_LT_48_H: 'Besök avbokat <48h',
            CANCELLED_GT_48_H: 'Besök avbokat >48h',
            CANCELLED_LT_96_H: 'Besök avbokat <96h',
            CANCELLED_GT_96_H: 'Besök avbokat >96h',
            INTERPRETER_NOT_PRESENT: 'Tolk uteblev',
            INTYG_COMPLEMENT_RESPONSE_DEVIATION: 'Antal dagar för komplettering har överskridits',
            EAVROP_ASSIGNMENT_ACCEPT_DEVIATION: 'Antal dagar för acceptans av förfrågan om utredning har överskridits',
            EAVROP_ASSESSMENT_LENGHT_DEVIATION: 'Antal dagar för utredning har överskridits',
            UNKNOWN: 'Ops detta ska inte hända'
        },
        jaNejMapping: {
            true: 'Ja',
            false: 'Nej'
        }
    })
    .constant('EAVROP_NOTES', {
        cannotAdd: 'Anteckningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda',
        cannotRemove: 'Fel uppstod vid bortagning av anteckning, detta kan beror på att du inte har rätt behörighet att genomföra denna förfrågan'
    });
