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

fmuApp.constant('RestUrl', {
    eavrop: "http://localhost:8080/app/rest/eavrop"
});
