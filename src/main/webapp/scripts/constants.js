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
    arendeID: 'Ärende ID',
    typ: 'Typ',
    enhet: 'Enhet/Avdelning',
    skickadDatum: 'Förfrågan skickad datum',
    patientOrt: 'Patientens bostadsort',
    bestallareOrganisation: 'Organisation',
    mottagenOrganisation: 'Organisation',
    utredare: 'Utredare',
    status: 'Status',
    antalPasserandeDagar: 'Antal dagar efter förfrågan om utredning'
});