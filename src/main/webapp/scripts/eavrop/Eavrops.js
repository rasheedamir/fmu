
angular.module('fmuClientApp').factory('Eavrops', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavrop, {eavropId: '@eavropId'});
}]);


angular.module('fmuClientApp').factory('EavropDocuments', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropDocuments, {eavropId: '@eavropId'});
}]);


angular.module('fmuClientApp').factory('EavropRequestedDocuments', ['$resource', 'RESTURL', function($resource, RESTURL){
    return $resource(RESTURL.eavropRequestedDocuments, {eavropId: '@eavropId'});
}]);
