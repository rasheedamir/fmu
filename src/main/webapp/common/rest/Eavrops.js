'use strict';
angular.module('fmuClientApp').factory('Eavrops', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavrop, {
            eavropId: '@eavropId'
        });
    }
]);
angular.module('fmuClientApp').factory('EavropDocuments', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropDocuments, {
            eavropId: '@eavropId'
        });
    }
]);
angular.module('fmuClientApp').factory('EavropRequestedDocuments', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropRequestedDocuments, {
            eavropId: '@eavropId'
        });
    }
]);

angular.module('fmuClientApp').factory('EavropNotes', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropNotes, {
            eavropId: '@eavropId'
        });
    }
]);

angular.module('fmuClientApp').factory('EavropAllEvents', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropAllEvents, {
            eavropId: '@eavropId'
        });
    }
]);

angular.module('fmuClientApp').factory('EavropOrder', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropOrder, {
            eavropId: '@eavropId'
        });
    }
]);

angular.module('fmuClientApp').factory('EavropPatient', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropPatient, {
            eavropId: '@eavropId'
        });
    }
]);

angular.module('fmuClientApp').factory('EavropVardgivarenheter', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropVardgivarenheter, {
            eavropId: '@eavropId'
        });
    }
]);

angular.module('fmuClientApp').factory('EavropAssignment', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropAssignment, {
            eavropId: '@eavropId'
        }, {
            'assign': {
                method: 'PUT'
            }
        });
    }
]);
angular.module('fmuClientApp').factory('EavropAccept', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropAccept, {
            eavropId: '@eavropId'
        }, {
            'accept': {
                method: 'PUT'
            }
        });
    }
]);
angular.module('fmuClientApp').factory('EavropReject', ['$resource', 'RESTURL',
    function($resource, RESTURL) {
        return $resource(RESTURL.eavropReject, {
            eavropId: '@eavropId'
        }, {
            'reject': {
                method: 'PUT'
            }
        });
    }
]);