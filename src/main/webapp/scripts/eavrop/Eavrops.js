'use strict';
angular.module('fmuClientApp').factory('Eavrops', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavrop, {eavropId: '@eavropId'});
}]);


angular.module('fmuClientApp').factory('EavropDocuments', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropDocuments, {eavropId: '@eavropId'});
}]);


angular.module('fmuClientApp').factory('EavropRequestedDocuments', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropRequestedDocuments, {eavropId: '@eavropId'});
}]);

angular.module('fmuClientApp').factory('EavropNotes', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropNotes, {eavropId: '@eavropId'});
}]);

angular.module('fmuClientApp').factory('EavropAllEvents', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropAllEvents, {eavropId: '@eavropId'});
}]);

angular.module('fmuClientApp').factory('EavropOrder', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropOrder, {eavropId: '@eavropId'});
}]);
