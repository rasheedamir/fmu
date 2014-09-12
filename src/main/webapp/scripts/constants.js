'use strict';

/* Constants */

fmuApp.constant('USER_ROLES', {
        all: '*',
        admin: 'ROLE_ADMIN',
        user: 'ROLE_USER'
    });

// Define for each locale the associated flag
// It will be used by the library "http://www.famfamfam.com/lab/icons/flags/"
// to display the flag
fmuApp.constant('FLAGS', {
        en: 'gb',
        sv: "sv"
    });

fmuApp.constant('RESTURL', {
    eavrop: "/app/rest/eavrop"
});

fmuApp.constant('EAVROPHEADERS', {
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
