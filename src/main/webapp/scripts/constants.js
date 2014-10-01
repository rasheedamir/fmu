'use strict';

/* Constants */
angular.module('fmuClientApp').constant('RESTURL', {
    eavrop: '/eavrop/:eavropId',
    eavropDocuments: '/eavrop/:eavropId/documents',
    eavropRequestedDocuments: '/eavrop/:eavropId/requested-documents',
    eavropNotes: '/eavrop/:eavropId/notes',
});
