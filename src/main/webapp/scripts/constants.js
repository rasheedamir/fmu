'use strict';

/* Constants */
angular.module('fmuClientApp').constant('RESTURL', {
    eavrop: '/app/rest/eavrop'
});

angular.module('fmuClientApp').constant('EAVROPHEADERS', {
    arendeId: 'Ärende ID',
    utredningType: 'Typ',
    enhet: 'Enhet/Avdelning',
    creationTime: 'Förfrågan skickad datum',
    patientCity: 'Patientens bostadsort',
    bestallareOrganisation: 'Organisation',
    mottagarenOrganisation: 'Organisation',
    utredare: 'Utredare',
    status: 'Status',
    antalDagarEfterForfragan: 'Antal dagar efter förfrågan om utredning'
});
