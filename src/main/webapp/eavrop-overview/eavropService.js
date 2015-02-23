(function() {
    'use strict';

angular.module('fmu.eavrop')
    .factory('eavropService', eavropService);

/*@ngInject*/
function eavropService($q, $http, Dataservice, gettext) {
    var eavropConstants = {
        dateFormat: 'yyyy-MM-dd',
        sortKeyMap: {
            // since pagination is done in backend hibernate sort is based on eavrops field values mapping
            // is needed when going from DTO key to actual eavrop fieldkey

            arendeId: 'arendeId',
            utredningType: 'utredningType',
            status: 'eavropState'
        },
        statusMapping: {
            UNASSIGNED: gettext('Eavrop-status/Förfrågan om utredning har inkommit'),
            ASSIGNED: gettext('Eavrop-status/Förfrågan tilldelas, inväntar acceptans'),
            ACCEPTED: gettext('Eavrop-status/Förfrågan accepterade'),
            ON_HOLD: gettext('Eavrop-status/Inväntar beslut från beställare'),
            SENT: gettext('Eavrop-status/Inväntar acceptans'),
            APPROVED: gettext('Eavrop-status/Utredningen godkänts av beställare'),
            CLOSED: gettext('Eavrop-status/Utredningen är avslutad'),
            ONGOING: gettext('Eavrop-status/Utredningen är påbörjad')
        },
        isCompletedMapping: {
            true: gettext('Eavrop-tabell-komplett-status/Ja'),
            false: gettext('Eavrop-tabell-komplett-status/Nej')
        },

        isCompensationApprovedMapping: {
            null: gettext('Eavrop-tabell-kompensation-godkänd-status/inväntar'),
            true: gettext('Eavrop-tabell-kompensation-godkänd-status/Ja'),
            false: gettext('Eavrop-tabell-kompensation-godkänd-status/Nej')
        },
        eavropStatus: {
            notAccepted: 'NOT_ACCEPTED',
            accepted: 'ACCEPTED',
            completed: 'COMPLETED'
        }
    };

    var service = {
        getEavropConstants: eavropConstants,
    };

    return service;
}
eavropService.$inject = ['$q', '$http', 'Dataservice', 'gettext'];
})();